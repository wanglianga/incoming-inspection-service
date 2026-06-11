package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.dto.PurchaseArrivalDTO;
import com.factory.inspection.dto.SamplingDTO;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.Material;
import com.factory.inspection.entity.PurchaseOrder;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.InspectionBatchRepository;
import com.factory.inspection.vo.InspectionBatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InspectionBatchService {

    private final InspectionBatchRepository inspectionBatchRepository;
    private final PurchaseOrderService purchaseOrderService;
    private final SupplierService supplierService;
    private final MaterialService materialService;

    private final AtomicInteger batchCounter = new AtomicInteger(0);

    @Autowired
    public InspectionBatchService(InspectionBatchRepository inspectionBatchRepository,
                                  PurchaseOrderService purchaseOrderService,
                                  SupplierService supplierService,
                                  MaterialService materialService) {
        this.inspectionBatchRepository = inspectionBatchRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.supplierService = supplierService;
        this.materialService = materialService;
    }

    @Transactional
    public InspectionBatchVO handlePurchaseArrival(PurchaseArrivalDTO dto) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getByOrderNoInternal(dto.getOrderNo());
        Supplier supplier = supplierService.getByCodeInternal(dto.getSupplierCode());
        Material material = materialService.getByCodeInternal(dto.getMaterialCode());

        InspectionBatch batch = new InspectionBatch();
        batch.setBatchNo(generateBatchNo());
        batch.setPurchaseOrder(purchaseOrder);
        batch.setSupplier(supplier);
        batch.setMaterial(material);
        batch.setBatchCode(dto.getBatchCode());
        batch.setArrivedQuantity(dto.getArrivedQuantity());
        batch.setUrgencyLevel(dto.getUrgencyLevel());
        batch.setInspector(dto.getInspector());
        batch.setRemark(dto.getRemark());
        batch.setStatus(InspectionStatus.PENDING);

        return VoConverter.toInspectionBatchVO(inspectionBatchRepository.save(batch));
    }

    @Transactional
    public InspectionBatchVO performSampling(SamplingDTO dto) {
        InspectionBatch batch = getByBatchNoInternal(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.PENDING) {
            throw new BusinessException("当前批次状态不允许抽样，当前状态: " + batch.getStatus());
        }

        if (dto.getSampleQuantity() > batch.getArrivedQuantity()) {
            throw new BusinessException("抽样数量不能大于到货数量");
        }

        batch.setSampleQuantity(dto.getSampleQuantity());
        batch.setSamplingScheme(dto.getSamplingScheme());
        batch.setStatus(InspectionStatus.SAMPLING);

        return VoConverter.toInspectionBatchVO(inspectionBatchRepository.save(batch));
    }

    @Transactional
    public InspectionBatchVO startInspection(String batchNo) {
        InspectionBatch batch = getByBatchNoInternal(batchNo);
        if (batch.getStatus() != InspectionStatus.SAMPLING) {
            throw new BusinessException("当前批次状态不允许开始检验，当前状态: " + batch.getStatus());
        }
        batch.setStatus(InspectionStatus.INSPECTING);
        return VoConverter.toInspectionBatchVO(inspectionBatchRepository.save(batch));
    }

    public InspectionBatchVO getById(Long id) {
        InspectionBatch batch = inspectionBatchRepository.findById(id)
                .orElseThrow(() -> new BusinessException("检验批次不存在"));
        initializeLazyAssociations(batch);
        return VoConverter.toInspectionBatchVO(batch);
    }

    public InspectionBatchVO getByBatchNo(String batchNo) {
        InspectionBatch batch = inspectionBatchRepository.findByBatchNo(batchNo)
                .orElseThrow(() -> new BusinessException("检验批次不存在: " + batchNo));
        initializeLazyAssociations(batch);
        return VoConverter.toInspectionBatchVO(batch);
    }

    public List<InspectionBatchVO> list() {
        List<InspectionBatch> batches = inspectionBatchRepository.findAll();
        for (InspectionBatch batch : batches) {
            initializeLazyAssociations(batch);
        }
        return VoConverter.toInspectionBatchVOList(batches);
    }

    public List<InspectionBatchVO> listByStatus(InspectionStatus status) {
        List<InspectionBatch> batches = inspectionBatchRepository.findByStatus(status);
        for (InspectionBatch batch : batches) {
            initializeLazyAssociations(batch);
        }
        return VoConverter.toInspectionBatchVOList(batches);
    }

    @Transactional
    public InspectionBatchVO updateStatus(String batchNo, InspectionStatus status) {
        InspectionBatch batch = getByBatchNoInternal(batchNo);
        batch.setStatus(status);
        return VoConverter.toInspectionBatchVO(inspectionBatchRepository.save(batch));
    }

    InspectionBatch getByBatchNoInternal(String batchNo) {
        return inspectionBatchRepository.findByBatchNo(batchNo)
                .orElseThrow(() -> new BusinessException("检验批次不存在: " + batchNo));
    }

    private void initializeLazyAssociations(InspectionBatch batch) {
        if (batch.getPurchaseOrder() != null) {
            batch.getPurchaseOrder().getOrderNo();
        }
        if (batch.getSupplier() != null) {
            batch.getSupplier().getSupplierName();
        }
        if (batch.getMaterial() != null) {
            batch.getMaterial().getMaterialName();
        }
    }

    private String generateBatchNo() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int seq = batchCounter.incrementAndGet() % 1000;
        return "IB" + datePart + String.format("%03d", seq);
    }
}

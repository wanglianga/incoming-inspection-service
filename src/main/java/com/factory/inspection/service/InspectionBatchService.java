package com.factory.inspection.service;

import com.factory.inspection.dto.PurchaseArrivalDTO;
import com.factory.inspection.dto.SamplingDTO;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.Material;
import com.factory.inspection.entity.PurchaseOrder;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.InspectionBatchRepository;
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
    public InspectionBatch handlePurchaseArrival(PurchaseArrivalDTO dto) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getByOrderNo(dto.getOrderNo());
        Supplier supplier = supplierService.getByCode(dto.getSupplierCode());
        Material material = materialService.getByCode(dto.getMaterialCode());

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

        return inspectionBatchRepository.save(batch);
    }

    @Transactional
    public InspectionBatch performSampling(SamplingDTO dto) {
        InspectionBatch batch = getByBatchNo(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.PENDING) {
            throw new BusinessException("当前批次状态不允许抽样，当前状态: " + batch.getStatus());
        }

        if (dto.getSampleQuantity() > batch.getArrivedQuantity()) {
            throw new BusinessException("抽样数量不能大于到货数量");
        }

        batch.setSampleQuantity(dto.getSampleQuantity());
        batch.setSamplingScheme(dto.getSamplingScheme());
        batch.setStatus(InspectionStatus.SAMPLING);

        return inspectionBatchRepository.save(batch);
    }

    @Transactional
    public InspectionBatch startInspection(String batchNo) {
        InspectionBatch batch = getByBatchNo(batchNo);
        if (batch.getStatus() != InspectionStatus.SAMPLING) {
            throw new BusinessException("当前批次状态不允许开始检验，当前状态: " + batch.getStatus());
        }
        batch.setStatus(InspectionStatus.INSPECTING);
        return inspectionBatchRepository.save(batch);
    }

    public InspectionBatch getById(Long id) {
        return inspectionBatchRepository.findById(id)
                .orElseThrow(() -> new BusinessException("检验批次不存在"));
    }

    public InspectionBatch getByBatchNo(String batchNo) {
        return inspectionBatchRepository.findByBatchNo(batchNo)
                .orElseThrow(() -> new BusinessException("检验批次不存在: " + batchNo));
    }

    public List<InspectionBatch> list() {
        return inspectionBatchRepository.findAll();
    }

    public List<InspectionBatch> listByStatus(InspectionStatus status) {
        return inspectionBatchRepository.findByStatus(status);
    }

    @Transactional
    public InspectionBatch updateStatus(String batchNo, InspectionStatus status) {
        InspectionBatch batch = getByBatchNo(batchNo);
        batch.setStatus(status);
        return inspectionBatchRepository.save(batch);
    }

    private String generateBatchNo() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int seq = batchCounter.incrementAndGet() % 1000;
        return "IB" + datePart + String.format("%03d", seq);
    }
}

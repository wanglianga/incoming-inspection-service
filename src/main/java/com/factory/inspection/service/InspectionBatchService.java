package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.dto.PurchaseArrivalDTO;
import com.factory.inspection.dto.SamplingDTO;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.Material;
import com.factory.inspection.entity.PurchaseOrder;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.enums.MaterialRiskLevel;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.InspectionBatchRepository;
import com.factory.inspection.vo.InspectionBatchVO;
import com.factory.inspection.vo.SamplingCalculationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InspectionBatchService {

    private static final double BASE_SAMPLING_RATIO = 0.10;
    private static final double KEY_PART_EXTRA_RATIO = 0.20;
    private static final double IMPORTANT_EXTRA_RATIO = 0.10;
    private static final double HIGH_DEFECT_SUPPLIER_EXTRA_RATIO = 0.15;
    private static final double HIGH_DEFECT_THRESHOLD = 5.0;
    private static final double NEW_MATERIAL_EXTRA_RATIO = 0.15;

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

    public SamplingCalculationVO calculateSampling(String batchNo) {
        InspectionBatch batch = getByBatchNoInternal(batchNo);
        initializeLazyAssociations(batch);

        Material material = batch.getMaterial();
        Supplier supplier = batch.getSupplier();

        List<String> reasons = new ArrayList<>();
        double ratio = BASE_SAMPLING_RATIO;
        reasons.add("基础抽样比例: " + (BASE_SAMPLING_RATIO * 100) + "%");

        if (material != null && material.getRiskLevel() != null) {
            if (material.getRiskLevel() == MaterialRiskLevel.KEY_PART) {
                ratio += KEY_PART_EXTRA_RATIO;
                reasons.add("关键件物料，额外增加抽样比例: " + (KEY_PART_EXTRA_RATIO * 100) + "%");
            } else if (material.getRiskLevel() == MaterialRiskLevel.IMPORTANT) {
                ratio += IMPORTANT_EXTRA_RATIO;
                reasons.add("重要件物料，额外增加抽样比例: " + (IMPORTANT_EXTRA_RATIO * 100) + "%");
            }
        }

        if (supplier != null && supplier.getHistoricalDefectRate() != null
                && supplier.getHistoricalDefectRate() > HIGH_DEFECT_THRESHOLD) {
            ratio += HIGH_DEFECT_SUPPLIER_EXTRA_RATIO;
            reasons.add("供应商历史不良率(" + supplier.getHistoricalDefectRate() + "%)超过阈值(" + HIGH_DEFECT_THRESHOLD + "%)，额外增加抽样比例: "
                    + (HIGH_DEFECT_SUPPLIER_EXTRA_RATIO * 100) + "%");
        }

        if (material != null && Boolean.TRUE.equals(material.getIsNewMaterial())) {
            ratio += NEW_MATERIAL_EXTRA_RATIO;
            reasons.add("新物料批次，额外增加抽样比例: " + (NEW_MATERIAL_EXTRA_RATIO * 100) + "%");
        }

        if (ratio > 1.0) {
            ratio = 1.0;
        }

        int sampleQuantity = (int) Math.ceil(batch.getArrivedQuantity() * ratio);
        String scheme = buildSchemeName(material, supplier, ratio);

        SamplingCalculationVO vo = new SamplingCalculationVO();
        vo.setBatchNo(batchNo);
        vo.setArrivedQuantity(batch.getArrivedQuantity());
        vo.setSampleQuantity(sampleQuantity);
        vo.setSamplingScheme(scheme);
        vo.setReasons(reasons);
        vo.setSamplingRatio(ratio);
        return vo;
    }

    @Transactional
    public InspectionBatchVO performSampling(SamplingDTO dto) {
        InspectionBatch batch = getByBatchNoInternal(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.PENDING) {
            throw new BusinessException("当前批次状态不允许抽样，当前状态: " + batch.getStatus());
        }

        initializeLazyAssociations(batch);

        int sampleQuantity;
        String samplingScheme;
        String samplingReasons;

        if (dto.getSampleQuantity() != null && dto.getSampleQuantity() > 0) {
            if (dto.getSampleQuantity() > batch.getArrivedQuantity()) {
                throw new BusinessException("抽样数量不能大于到货数量");
            }
            sampleQuantity = dto.getSampleQuantity();
            samplingScheme = dto.getSamplingScheme() != null ? dto.getSamplingScheme() : "手动指定";
            samplingReasons = "手动指定抽样数量: " + sampleQuantity;
        } else {
            SamplingCalculationVO calculation = calculateSampling(dto.getBatchNo());
            sampleQuantity = calculation.getSampleQuantity();
            samplingScheme = calculation.getSamplingScheme();
            samplingReasons = String.join("; ", calculation.getReasons());
        }

        batch.setSampleQuantity(sampleQuantity);
        batch.setSamplingScheme(samplingScheme);
        batch.setSamplingReasons(samplingReasons);
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

    private String buildSchemeName(Material material, Supplier supplier, double ratio) {
        StringBuilder sb = new StringBuilder("风险驱动抽样");
        if (ratio <= BASE_SAMPLING_RATIO) {
            sb.append("(常规)");
        } else if (ratio <= BASE_SAMPLING_RATIO + IMPORTANT_EXTRA_RATIO) {
            sb.append("(加严)");
        } else if (ratio <= BASE_SAMPLING_RATIO + IMPORTANT_EXTRA_RATIO + HIGH_DEFECT_SUPPLIER_EXTRA_RATIO) {
            sb.append("(特严)");
        } else {
            sb.append("(全检)");
        }
        return sb.toString();
    }
}

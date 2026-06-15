package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.dto.StorageRecordDTO;
import com.factory.inspection.entity.ConcessionAcceptance;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.StorageRecord;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.ConcessionAcceptanceRepository;
import com.factory.inspection.repository.StorageRecordRepository;
import com.factory.inspection.vo.StorageRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StorageService {

    private final StorageRecordRepository storageRecordRepository;
    private final InspectionBatchService inspectionBatchService;
    private final MaterialService materialService;
    private final ConcessionAcceptanceRepository concessionAcceptanceRepository;

    private final AtomicInteger storageCounter = new AtomicInteger(0);

    @Autowired
    public StorageService(StorageRecordRepository storageRecordRepository,
                          InspectionBatchService inspectionBatchService,
                          MaterialService materialService,
                          ConcessionAcceptanceRepository concessionAcceptanceRepository) {
        this.storageRecordRepository = storageRecordRepository;
        this.inspectionBatchService = inspectionBatchService;
        this.materialService = materialService;
        this.concessionAcceptanceRepository = concessionAcceptanceRepository;
    }

    @Transactional
    public StorageRecordVO createStorage(StorageRecordDTO dto) {
        InspectionBatch batch = inspectionBatchService.getByBatchNoInternal(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.QUALIFIED
                && batch.getStatus() != InspectionStatus.CONCESSION_APPROVED) {
            throw new BusinessException("当前批次状态不允许入库，当前状态: " + batch.getStatus());
        }

        if (dto.getStoredQuantity() > batch.getArrivedQuantity()) {
            throw new BusinessException("入库数量不能大于到货数量");
        }

        String warehouseLocation = dto.getWarehouseLocation();
        if (warehouseLocation == null || warehouseLocation.isEmpty()) {
            warehouseLocation = batch.getMaterial().getWarehouseLocation();
        }

        StorageRecord storageRecord = new StorageRecord();
        storageRecord.setStorageNo(generateStorageNo());
        storageRecord.setBatch(batch);
        storageRecord.setMaterial(batch.getMaterial());
        storageRecord.setStoredQuantity(dto.getStoredQuantity());
        storageRecord.setWarehouseLocation(warehouseLocation);
        storageRecord.setReceiver(dto.getReceiver());
        storageRecord.setRemark(dto.getRemark());

        if (batch.getStatus() == InspectionStatus.CONCESSION_APPROVED) {
            ConcessionAcceptance concession = resolveConcessionForBatch(batch, dto.getConcessionId());
            storageRecord.setConcessionId(concession.getId());
            storageRecord.setIsConcessionRestricted(true);

            if (concession.getApplicableWorkOrders() != null && !concession.getApplicableWorkOrders().isEmpty()) {
                storageRecord.setApplicableWorkOrder(concession.getApplicableWorkOrders());
            } else if (dto.getApplicableWorkOrder() != null && !dto.getApplicableWorkOrder().isEmpty()) {
                storageRecord.setApplicableWorkOrder(dto.getApplicableWorkOrder());
            } else {
                throw new BusinessException("让步接收物料入库必须指定可用工单范围");
            }
        } else {
            storageRecord.setConcessionId(null);
            storageRecord.setIsConcessionRestricted(false);
            storageRecord.setApplicableWorkOrder(null);
        }

        inspectionBatchService.updateStatus(dto.getBatchNo(), InspectionStatus.STORED);

        StorageRecord saved = storageRecordRepository.save(storageRecord);
        initializeLazyAssociations(saved);
        return VoConverter.toStorageRecordVO(saved);
    }

    public boolean checkWorkOrderAccess(Long storageRecordId, String workOrderNo) {
        StorageRecord record = storageRecordRepository.findById(storageRecordId)
                .orElseThrow(() -> new BusinessException("入库记录不存在"));

        if (!Boolean.TRUE.equals(record.getIsConcessionRestricted())) {
            return true;
        }

        if (record.getApplicableWorkOrder() == null || record.getApplicableWorkOrder().isEmpty()) {
            return false;
        }

        if (workOrderNo == null || workOrderNo.isEmpty()) {
            return false;
        }

        List<String> allowedOrders = Arrays.asList(record.getApplicableWorkOrder().split(","));
        return allowedOrders.stream()
                .map(String::trim)
                .anyMatch(order -> order.equals(workOrderNo));
    }

    public StorageRecordVO getById(Long id) {
        StorageRecord storageRecord = storageRecordRepository.findById(id)
                .orElseThrow(() -> new BusinessException("入库记录不存在"));
        initializeLazyAssociations(storageRecord);
        return VoConverter.toStorageRecordVO(storageRecord);
    }

    public StorageRecordVO getByStorageNo(String storageNo) {
        StorageRecord storageRecord = storageRecordRepository.findByStorageNo(storageNo)
                .orElseThrow(() -> new BusinessException("入库记录不存在: " + storageNo));
        initializeLazyAssociations(storageRecord);
        return VoConverter.toStorageRecordVO(storageRecord);
    }

    public List<StorageRecordVO> list() {
        List<StorageRecord> list = storageRecordRepository.findAll();
        for (StorageRecord record : list) {
            initializeLazyAssociations(record);
        }
        return VoConverter.toStorageRecordVOList(list);
    }

    public List<StorageRecordVO> getByBatchId(Long batchId) {
        List<StorageRecord> list = storageRecordRepository.findByBatchId(batchId);
        for (StorageRecord record : list) {
            initializeLazyAssociations(record);
        }
        return VoConverter.toStorageRecordVOList(list);
    }

    private ConcessionAcceptance resolveConcessionForBatch(InspectionBatch batch, Long requestedConcessionId) {
        if (requestedConcessionId != null) {
            ConcessionAcceptance concession = concessionAcceptanceRepository.findById(requestedConcessionId)
                    .orElseThrow(() -> new BusinessException("让步接收记录不存在: " + requestedConcessionId));
            if (!concession.getBatch().getId().equals(batch.getId())) {
                throw new BusinessException("让步接收记录与当前批次不匹配");
            }
            if (!"APPROVED".equals(concession.getApproveStatus())) {
                throw new BusinessException("让步接收申请未审批通过，不能入库");
            }
            return concession;
        }

        ConcessionAcceptance concession = concessionAcceptanceRepository
                .findByBatchIdAndApproveStatus(batch.getId(), "APPROVED")
                .orElseThrow(() -> new BusinessException("未找到该批次已审批通过的让步接收记录，请指定让步接收ID"));
        return concession;
    }

    private void initializeLazyAssociations(StorageRecord record) {
        if (record.getBatch() != null) {
            record.getBatch().getBatchNo();
        }
        if (record.getMaterial() != null) {
            record.getMaterial().getMaterialName();
        }
    }

    private String generateStorageNo() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int seq = storageCounter.incrementAndGet() % 1000;
        return "ST" + datePart + String.format("%03d", seq);
    }
}

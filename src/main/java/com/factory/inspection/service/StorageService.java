package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.dto.StorageRecordDTO;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.StorageRecord;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.StorageRecordRepository;
import com.factory.inspection.vo.StorageRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StorageService {

    private final StorageRecordRepository storageRecordRepository;
    private final InspectionBatchService inspectionBatchService;
    private final MaterialService materialService;

    private final AtomicInteger storageCounter = new AtomicInteger(0);

    @Autowired
    public StorageService(StorageRecordRepository storageRecordRepository,
                          InspectionBatchService inspectionBatchService,
                          MaterialService materialService) {
        this.storageRecordRepository = storageRecordRepository;
        this.inspectionBatchService = inspectionBatchService;
        this.materialService = materialService;
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

        inspectionBatchService.updateStatus(dto.getBatchNo(), InspectionStatus.STORED);

        StorageRecord saved = storageRecordRepository.save(storageRecord);
        initializeLazyAssociations(saved);
        return VoConverter.toStorageRecordVO(saved);
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

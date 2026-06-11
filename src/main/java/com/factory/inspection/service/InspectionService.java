package com.factory.inspection.service;

import com.factory.inspection.dto.DefectRecordDTO;
import com.factory.inspection.dto.InspectionRecordDTO;
import com.factory.inspection.entity.DefectRecord;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.InspectionRecord;
import com.factory.inspection.enums.DefectLevel;
import com.factory.inspection.enums.InspectionResult;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.enums.UnqualifiedType;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.DefectRecordRepository;
import com.factory.inspection.repository.InspectionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InspectionService {

    private final InspectionRecordRepository inspectionRecordRepository;
    private final DefectRecordRepository defectRecordRepository;
    private final InspectionBatchService inspectionBatchService;

    @Autowired
    public InspectionService(InspectionRecordRepository inspectionRecordRepository,
                             DefectRecordRepository defectRecordRepository,
                             InspectionBatchService inspectionBatchService) {
        this.inspectionRecordRepository = inspectionRecordRepository;
        this.defectRecordRepository = defectRecordRepository;
        this.inspectionBatchService = inspectionBatchService;
    }

    @Transactional
    public InspectionRecord addInspectionRecord(InspectionRecordDTO dto) {
        InspectionBatch batch = inspectionBatchService.getByBatchNo(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.INSPECTING && batch.getStatus() != InspectionStatus.SAMPLING) {
            throw new BusinessException("当前批次状态不允许录入检验记录，当前状态: " + batch.getStatus());
        }

        if (batch.getStatus() == InspectionStatus.SAMPLING) {
            inspectionBatchService.startInspection(dto.getBatchNo());
        }

        InspectionRecord record = new InspectionRecord();
        record.setBatch(batch);
        record.setInspectionType(dto.getInspectionType());
        record.setItemName(dto.getItemName());
        record.setMeasuredValue(dto.getMeasuredValue());
        record.setStandardValue(dto.getStandardValue());
        record.setResult(dto.getResult());
        record.setRemark(dto.getRemark());
        record.setInspector(dto.getInspector());

        return inspectionRecordRepository.save(record);
    }

    @Transactional
    public DefectRecord addDefectRecord(DefectRecordDTO dto) {
        InspectionBatch batch = inspectionBatchService.getByBatchNo(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.INSPECTING) {
            throw new BusinessException("当前批次状态不允许录入缺陷记录，当前状态: " + batch.getStatus());
        }

        DefectRecord record = new DefectRecord();
        record.setBatch(batch);
        record.setUnqualifiedType(dto.getUnqualifiedType());
        record.setDefectLevel(dto.getDefectLevel());
        record.setDefectQuantity(dto.getDefectQuantity());
        record.setDescription(dto.getDescription());
        record.setRecorder(dto.getRecorder());

        return defectRecordRepository.save(record);
    }

    @Transactional
    public InspectionBatch judgeInspectionResult(String batchNo) {
        InspectionBatch batch = inspectionBatchService.getByBatchNo(batchNo);

        if (batch.getStatus() != InspectionStatus.INSPECTING) {
            throw new BusinessException("当前批次状态不允许判定，当前状态: " + batch.getStatus());
        }

        List<InspectionRecord> records = inspectionRecordRepository.findByBatchId(batch.getId());
        if (records.isEmpty()) {
            throw new BusinessException("未找到检验记录，无法进行判定");
        }

        boolean hasFailure = records.stream()
                .anyMatch(r -> r.getResult() == InspectionResult.FAIL);

        List<DefectRecord> defects = defectRecordRepository.findByBatchId(batch.getId());
        boolean hasCriticalDefect = defects.stream()
                .anyMatch(d -> d.getDefectLevel() == DefectLevel.CRITICAL);
        boolean hasMajorDefect = defects.stream()
                .anyMatch(d -> d.getDefectLevel() == DefectLevel.MAJOR);

        boolean hasQuantityShortage = defects.stream()
                .anyMatch(d -> d.getUnqualifiedType() == UnqualifiedType.QUANTITY_SHORTAGE);
        boolean hasCertificateMissing = defects.stream()
                .anyMatch(d -> d.getUnqualifiedType() == UnqualifiedType.CERTIFICATE_MISSING);

        if (!hasFailure && defects.isEmpty()) {
            return inspectionBatchService.updateStatus(batchNo, InspectionStatus.QUALIFIED);
        } else if (hasCriticalDefect || hasMajorDefect || hasQuantityShortage || hasCertificateMissing) {
            return inspectionBatchService.updateStatus(batchNo, InspectionStatus.UNQUALIFIED);
        } else {
            return inspectionBatchService.updateStatus(batchNo, InspectionStatus.UNQUALIFIED);
        }
    }

    public List<InspectionRecord> getRecordsByBatchId(Long batchId) {
        return inspectionRecordRepository.findByBatchId(batchId);
    }

    public List<DefectRecord> getDefectsByBatchId(Long batchId) {
        return defectRecordRepository.findByBatchId(batchId);
    }
}

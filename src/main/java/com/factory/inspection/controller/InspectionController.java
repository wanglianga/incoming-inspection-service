package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.dto.DefectRecordDTO;
import com.factory.inspection.dto.InspectionRecordDTO;
import com.factory.inspection.entity.DefectRecord;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.InspectionRecord;
import com.factory.inspection.service.InspectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    private final InspectionService inspectionService;

    @Autowired
    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping("/record")
    public Result<InspectionRecord> addInspectionRecord(@Valid @RequestBody InspectionRecordDTO dto) {
        return Result.success(inspectionService.addInspectionRecord(dto));
    }

    @PostMapping("/defect")
    public Result<DefectRecord> addDefectRecord(@Valid @RequestBody DefectRecordDTO dto) {
        return Result.success(inspectionService.addDefectRecord(dto));
    }

    @PostMapping("/judge/{batchNo}")
    public Result<InspectionBatch> judgeInspectionResult(@PathVariable String batchNo) {
        return Result.success(inspectionService.judgeInspectionResult(batchNo));
    }

    @GetMapping("/records/batch/{batchId}")
    public Result<List<InspectionRecord>> getRecordsByBatchId(@PathVariable Long batchId) {
        return Result.success(inspectionService.getRecordsByBatchId(batchId));
    }

    @GetMapping("/defects/batch/{batchId}")
    public Result<List<DefectRecord>> getDefectsByBatchId(@PathVariable Long batchId) {
        return Result.success(inspectionService.getDefectsByBatchId(batchId));
    }
}

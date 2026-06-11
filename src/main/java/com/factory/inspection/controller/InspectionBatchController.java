package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.dto.PurchaseArrivalDTO;
import com.factory.inspection.dto.SamplingDTO;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.service.InspectionBatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspection-batches")
public class InspectionBatchController {

    private final InspectionBatchService inspectionBatchService;

    @Autowired
    public InspectionBatchController(InspectionBatchService inspectionBatchService) {
        this.inspectionBatchService = inspectionBatchService;
    }

    @PostMapping("/arrival")
    public Result<InspectionBatch> handlePurchaseArrival(@Valid @RequestBody PurchaseArrivalDTO dto) {
        return Result.success(inspectionBatchService.handlePurchaseArrival(dto));
    }

    @PostMapping("/sampling")
    public Result<InspectionBatch> performSampling(@Valid @RequestBody SamplingDTO dto) {
        return Result.success(inspectionBatchService.performSampling(dto));
    }

    @PostMapping("/{batchNo}/start")
    public Result<InspectionBatch> startInspection(@PathVariable String batchNo) {
        return Result.success(inspectionBatchService.startInspection(batchNo));
    }

    @GetMapping("/{id}")
    public Result<InspectionBatch> getById(@PathVariable Long id) {
        return Result.success(inspectionBatchService.getById(id));
    }

    @GetMapping("/batchNo/{batchNo}")
    public Result<InspectionBatch> getByBatchNo(@PathVariable String batchNo) {
        return Result.success(inspectionBatchService.getByBatchNo(batchNo));
    }

    @GetMapping
    public Result<List<InspectionBatch>> list() {
        return Result.success(inspectionBatchService.list());
    }

    @GetMapping("/status/{status}")
    public Result<List<InspectionBatch>> listByStatus(@PathVariable InspectionStatus status) {
        return Result.success(inspectionBatchService.listByStatus(status));
    }
}

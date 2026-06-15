package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.dto.PurchaseArrivalDTO;
import com.factory.inspection.dto.SamplingDTO;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.service.InspectionBatchService;
import com.factory.inspection.vo.InspectionBatchVO;
import com.factory.inspection.vo.SamplingCalculationVO;
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
    public Result<InspectionBatchVO> handlePurchaseArrival(@Valid @RequestBody PurchaseArrivalDTO dto) {
        return Result.success(inspectionBatchService.handlePurchaseArrival(dto));
    }

    @PostMapping("/sampling")
    public Result<InspectionBatchVO> performSampling(@Valid @RequestBody SamplingDTO dto) {
        return Result.success(inspectionBatchService.performSampling(dto));
    }

    @GetMapping("/{batchNo}/calculate-sampling")
    public Result<SamplingCalculationVO> calculateSampling(@PathVariable String batchNo) {
        return Result.success(inspectionBatchService.calculateSampling(batchNo));
    }

    @PostMapping("/{batchNo}/start")
    public Result<InspectionBatchVO> startInspection(@PathVariable String batchNo) {
        return Result.success(inspectionBatchService.startInspection(batchNo));
    }

    @GetMapping("/{id}")
    public Result<InspectionBatchVO> getById(@PathVariable Long id) {
        return Result.success(inspectionBatchService.getById(id));
    }

    @GetMapping("/batchNo/{batchNo}")
    public Result<InspectionBatchVO> getByBatchNo(@PathVariable String batchNo) {
        return Result.success(inspectionBatchService.getByBatchNo(batchNo));
    }

    @GetMapping
    public Result<List<InspectionBatchVO>> list() {
        return Result.success(inspectionBatchService.list());
    }

    @GetMapping("/status/{status}")
    public Result<List<InspectionBatchVO>> listByStatus(@PathVariable InspectionStatus status) {
        return Result.success(inspectionBatchService.listByStatus(status));
    }
}

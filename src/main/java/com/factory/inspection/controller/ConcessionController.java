package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.dto.ConcessionAcceptanceDTO;
import com.factory.inspection.dto.ConcessionApproveDTO;
import com.factory.inspection.entity.ConcessionAcceptance;
import com.factory.inspection.service.ConcessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concessions")
public class ConcessionController {

    private final ConcessionService concessionService;

    @Autowired
    public ConcessionController(ConcessionService concessionService) {
        this.concessionService = concessionService;
    }

    @PostMapping("/apply")
    public Result<ConcessionAcceptance> applyConcession(@Valid @RequestBody ConcessionAcceptanceDTO dto) {
        return Result.success(concessionService.applyConcession(dto));
    }

    @PostMapping("/approve")
    public Result<ConcessionAcceptance> approveConcession(@Valid @RequestBody ConcessionApproveDTO dto) {
        return Result.success(concessionService.approveConcession(dto));
    }

    @GetMapping("/{id}")
    public Result<ConcessionAcceptance> getById(@PathVariable Long id) {
        return Result.success(concessionService.getById(id));
    }

    @GetMapping
    public Result<List<ConcessionAcceptance>> list() {
        return Result.success(concessionService.list());
    }

    @GetMapping("/batch/{batchId}")
    public Result<List<ConcessionAcceptance>> getByBatchId(@PathVariable Long batchId) {
        return Result.success(concessionService.getByBatchId(batchId));
    }
}

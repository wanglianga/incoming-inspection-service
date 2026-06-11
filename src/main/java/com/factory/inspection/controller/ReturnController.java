package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.dto.ReturnRecordDTO;
import com.factory.inspection.dto.SupplierConfirmReturnDTO;
import com.factory.inspection.service.ReturnService;
import com.factory.inspection.vo.ReturnRecordVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    private final ReturnService returnService;

    @Autowired
    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PostMapping
    public Result<ReturnRecordVO> createReturn(@Valid @RequestBody ReturnRecordDTO dto) {
        return Result.success(returnService.createReturn(dto));
    }

    @PostMapping("/supplier-confirm")
    public Result<ReturnRecordVO> supplierConfirm(@Valid @RequestBody SupplierConfirmReturnDTO dto) {
        return Result.success(returnService.supplierConfirm(dto));
    }

    @GetMapping("/{id}")
    public Result<ReturnRecordVO> getById(@PathVariable Long id) {
        return Result.success(returnService.getById(id));
    }

    @GetMapping("/returnNo/{returnNo}")
    public Result<ReturnRecordVO> getByReturnNo(@PathVariable String returnNo) {
        return Result.success(returnService.getByReturnNo(returnNo));
    }

    @GetMapping
    public Result<List<ReturnRecordVO>> list() {
        return Result.success(returnService.list());
    }

    @GetMapping("/batch/{batchId}")
    public Result<List<ReturnRecordVO>> getByBatchId(@PathVariable Long batchId) {
        return Result.success(returnService.getByBatchId(batchId));
    }
}

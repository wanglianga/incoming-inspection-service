package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.dto.StorageRecordDTO;
import com.factory.inspection.service.StorageService;
import com.factory.inspection.vo.StorageRecordVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storages")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public Result<StorageRecordVO> createStorage(@Valid @RequestBody StorageRecordDTO dto) {
        return Result.success(storageService.createStorage(dto));
    }

    @GetMapping("/{id}")
    public Result<StorageRecordVO> getById(@PathVariable Long id) {
        return Result.success(storageService.getById(id));
    }

    @GetMapping("/storageNo/{storageNo}")
    public Result<StorageRecordVO> getByStorageNo(@PathVariable String storageNo) {
        return Result.success(storageService.getByStorageNo(storageNo));
    }

    @GetMapping
    public Result<List<StorageRecordVO>> list() {
        return Result.success(storageService.list());
    }

    @GetMapping("/batch/{batchId}")
    public Result<List<StorageRecordVO>> getByBatchId(@PathVariable Long batchId) {
        return Result.success(storageService.getByBatchId(batchId));
    }
}

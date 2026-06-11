package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.service.SupplierService;
import com.factory.inspection.vo.SupplierVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public Result<SupplierVO> create(@Valid @RequestBody Supplier supplier) {
        return Result.success(supplierService.create(supplier));
    }

    @GetMapping("/{id}")
    public Result<SupplierVO> getById(@PathVariable Long id) {
        return Result.success(supplierService.getById(id));
    }

    @GetMapping("/code/{code}")
    public Result<SupplierVO> getByCode(@PathVariable String code) {
        return Result.success(supplierService.getByCode(code));
    }

    @GetMapping
    public Result<List<SupplierVO>> list() {
        return Result.success(supplierService.list());
    }

    @PutMapping("/{id}")
    public Result<SupplierVO> update(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
        return Result.success(supplierService.update(id, supplier));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return Result.success();
    }
}

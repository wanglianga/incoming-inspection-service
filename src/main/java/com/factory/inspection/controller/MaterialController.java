package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.entity.Material;
import com.factory.inspection.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public Result<Material> create(@Valid @RequestBody Material material) {
        return Result.success(materialService.create(material));
    }

    @GetMapping("/{id}")
    public Result<Material> getById(@PathVariable Long id) {
        return Result.success(materialService.getById(id));
    }

    @GetMapping("/code/{code}")
    public Result<Material> getByCode(@PathVariable String code) {
        return Result.success(materialService.getByCode(code));
    }

    @GetMapping
    public Result<List<Material>> list() {
        return Result.success(materialService.list());
    }

    @PutMapping("/{id}")
    public Result<Material> update(@PathVariable Long id, @Valid @RequestBody Material material) {
        return Result.success(materialService.update(id, material));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        materialService.delete(id);
        return Result.success();
    }
}

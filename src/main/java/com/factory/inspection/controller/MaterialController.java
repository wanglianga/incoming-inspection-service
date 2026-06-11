package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.entity.Material;
import com.factory.inspection.service.MaterialService;
import com.factory.inspection.vo.MaterialVO;
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
    public Result<MaterialVO> create(@Valid @RequestBody Material material) {
        return Result.success(materialService.create(material));
    }

    @GetMapping("/{id}")
    public Result<MaterialVO> getById(@PathVariable Long id) {
        return Result.success(materialService.getById(id));
    }

    @GetMapping("/code/{code}")
    public Result<MaterialVO> getByCode(@PathVariable String code) {
        return Result.success(materialService.getByCode(code));
    }

    @GetMapping
    public Result<List<MaterialVO>> list() {
        return Result.success(materialService.list());
    }

    @PutMapping("/{id}")
    public Result<MaterialVO> update(@PathVariable Long id, @Valid @RequestBody Material material) {
        return Result.success(materialService.update(id, material));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        materialService.delete(id);
        return Result.success();
    }
}

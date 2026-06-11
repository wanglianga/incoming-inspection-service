package com.factory.inspection.service;

import com.factory.inspection.entity.Material;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Transactional
    public Material create(Material material) {
        if (materialRepository.findByMaterialCode(material.getMaterialCode()).isPresent()) {
            throw new BusinessException("物料编码已存在");
        }
        return materialRepository.save(material);
    }

    public Material getById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new BusinessException("物料不存在"));
    }

    public Material getByCode(String materialCode) {
        return materialRepository.findByMaterialCode(materialCode)
                .orElseThrow(() -> new BusinessException("物料不存在: " + materialCode));
    }

    public List<Material> list() {
        return materialRepository.findAll();
    }

    @Transactional
    public Material update(Long id, Material material) {
        Material existing = getById(id);
        existing.setMaterialName(material.getMaterialName());
        existing.setSpecification(material.getSpecification());
        existing.setUnit(material.getUnit());
        existing.setWarehouseLocation(material.getWarehouseLocation());
        return materialRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }
}

package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.entity.Material;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.MaterialRepository;
import com.factory.inspection.vo.MaterialVO;
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
    public MaterialVO create(Material material) {
        if (materialRepository.findByMaterialCode(material.getMaterialCode()).isPresent()) {
            throw new BusinessException("物料编码已存在");
        }
        return VoConverter.toMaterialVO(materialRepository.save(material));
    }

    public MaterialVO getById(Long id) {
        return VoConverter.toMaterialVO(materialRepository.findById(id)
                .orElseThrow(() -> new BusinessException("物料不存在")));
    }

    public MaterialVO getByCode(String materialCode) {
        return VoConverter.toMaterialVO(materialRepository.findByMaterialCode(materialCode)
                .orElseThrow(() -> new BusinessException("物料不存在: " + materialCode)));
    }

    public List<MaterialVO> list() {
        return VoConverter.toMaterialVOList(materialRepository.findAll());
    }

    @Transactional
    public MaterialVO update(Long id, Material material) {
        Material existing = materialRepository.findById(id)
                .orElseThrow(() -> new BusinessException("物料不存在"));
        existing.setMaterialName(material.getMaterialName());
        existing.setSpecification(material.getSpecification());
        existing.setUnit(material.getUnit());
        existing.setWarehouseLocation(material.getWarehouseLocation());
        existing.setRiskLevel(material.getRiskLevel());
        existing.setIsNewMaterial(material.getIsNewMaterial());
        return VoConverter.toMaterialVO(materialRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }

    public Material getByCodeInternal(String materialCode) {
        return materialRepository.findByMaterialCode(materialCode)
                .orElseThrow(() -> new BusinessException("物料不存在: " + materialCode));
    }
}

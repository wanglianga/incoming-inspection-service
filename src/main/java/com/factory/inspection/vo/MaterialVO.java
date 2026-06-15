package com.factory.inspection.vo;

import com.factory.inspection.enums.MaterialRiskLevel;

import java.time.LocalDateTime;

public class MaterialVO {

    private Long id;
    private String materialCode;
    private String materialName;
    private String specification;
    private String unit;
    private String warehouseLocation;
    private MaterialRiskLevel riskLevel;
    private Boolean isNewMaterial;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public MaterialRiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(MaterialRiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Boolean getIsNewMaterial() {
        return isNewMaterial;
    }

    public void setIsNewMaterial(Boolean isNewMaterial) {
        this.isNewMaterial = isNewMaterial;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

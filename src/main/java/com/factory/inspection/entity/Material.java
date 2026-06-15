package com.factory.inspection.entity;

import com.factory.inspection.enums.MaterialRiskLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String materialCode;

    @Column(nullable = false, length = 200)
    private String materialName;

    @Column(length = 100)
    private String specification;

    @Column(length = 50)
    private String unit;

    @Column(length = 50)
    private String warehouseLocation;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private MaterialRiskLevel riskLevel;

    @Column
    private Boolean isNewMaterial;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
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

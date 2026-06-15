package com.factory.inspection.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "storage_record")
public class StorageRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String storageNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InspectionBatch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer storedQuantity;

    @Column(length = 50)
    private String warehouseLocation;

    @Column(length = 50)
    private String receiver;

    @Column(length = 500)
    private String remark;

    @Column
    private Long concessionId;

    @Column
    private Boolean isConcessionRestricted;

    @Column(length = 1000)
    private String applicableWorkOrder;

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

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public InspectionBatch getBatch() {
        return batch;
    }

    public void setBatch(InspectionBatch batch) {
        this.batch = batch;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getStoredQuantity() {
        return storedQuantity;
    }

    public void setStoredQuantity(Integer storedQuantity) {
        this.storedQuantity = storedQuantity;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getConcessionId() {
        return concessionId;
    }

    public void setConcessionId(Long concessionId) {
        this.concessionId = concessionId;
    }

    public Boolean getIsConcessionRestricted() {
        return isConcessionRestricted;
    }

    public void setIsConcessionRestricted(Boolean isConcessionRestricted) {
        this.isConcessionRestricted = isConcessionRestricted;
    }

    public String getApplicableWorkOrder() {
        return applicableWorkOrder;
    }

    public void setApplicableWorkOrder(String applicableWorkOrder) {
        this.applicableWorkOrder = applicableWorkOrder;
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

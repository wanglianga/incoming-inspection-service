package com.factory.inspection.vo;

import java.time.LocalDateTime;

public class StorageRecordVO {

    private Long id;
    private String storageNo;
    private String batchNo;
    private MaterialVO material;
    private Integer storedQuantity;
    private String warehouseLocation;
    private String receiver;
    private String remark;
    private Long concessionId;
    private Boolean isConcessionRestricted;
    private String applicableWorkOrder;
    private LocalDateTime createTime;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public MaterialVO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialVO material) {
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

package com.factory.inspection.vo;

import java.time.LocalDateTime;

public class ReturnRecordVO {

    private Long id;
    private String returnNo;
    private String batchNo;
    private SupplierVO supplier;
    private Integer returnQuantity;
    private String returnReason;
    private Boolean supplierConfirmed;
    private Boolean supplierRejected;
    private String supplierRejectReason;
    private String handler;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public SupplierVO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierVO supplier) {
        this.supplier = supplier;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public Boolean getSupplierConfirmed() {
        return supplierConfirmed;
    }

    public void setSupplierConfirmed(Boolean supplierConfirmed) {
        this.supplierConfirmed = supplierConfirmed;
    }

    public Boolean getSupplierRejected() {
        return supplierRejected;
    }

    public void setSupplierRejected(Boolean supplierRejected) {
        this.supplierRejected = supplierRejected;
    }

    public String getSupplierRejectReason() {
        return supplierRejectReason;
    }

    public void setSupplierRejectReason(String supplierRejectReason) {
        this.supplierRejectReason = supplierRejectReason;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

package com.factory.inspection.vo;

import java.time.LocalDateTime;

public class PurchaseOrderVO {

    private Long id;
    private String orderNo;
    private SupplierVO supplier;
    private MaterialVO material;
    private Integer orderQuantity;
    private String remark;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public SupplierVO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierVO supplier) {
        this.supplier = supplier;
    }

    public MaterialVO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialVO material) {
        this.material = material;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

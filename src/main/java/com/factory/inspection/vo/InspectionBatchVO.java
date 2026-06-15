package com.factory.inspection.vo;

import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.enums.UrgencyLevel;

import java.time.LocalDateTime;

public class InspectionBatchVO {

    private Long id;
    private String batchNo;
    private SimplePurchaseOrderVO purchaseOrder;
    private SupplierVO supplier;
    private MaterialVO material;
    private String batchCode;
    private Integer arrivedQuantity;
    private Integer sampleQuantity;
    private String samplingScheme;
    private String samplingReasons;
    private UrgencyLevel urgencyLevel;
    private InspectionStatus status;
    private String inspector;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public SimplePurchaseOrderVO getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(SimplePurchaseOrderVO purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Integer getArrivedQuantity() {
        return arrivedQuantity;
    }

    public void setArrivedQuantity(Integer arrivedQuantity) {
        this.arrivedQuantity = arrivedQuantity;
    }

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public String getSamplingScheme() {
        return samplingScheme;
    }

    public void setSamplingScheme(String samplingScheme) {
        this.samplingScheme = samplingScheme;
    }

    public String getSamplingReasons() {
        return samplingReasons;
    }

    public void setSamplingReasons(String samplingReasons) {
        this.samplingReasons = samplingReasons;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public InspectionStatus getStatus() {
        return status;
    }

    public void setStatus(InspectionStatus status) {
        this.status = status;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

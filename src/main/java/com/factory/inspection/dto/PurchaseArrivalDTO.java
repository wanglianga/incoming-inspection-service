package com.factory.inspection.dto;

import com.factory.inspection.enums.UrgencyLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PurchaseArrivalDTO {
    @NotBlank(message = "采购单号不能为空")
    private String orderNo;

    @NotBlank(message = "供应商编码不能为空")
    private String supplierCode;

    @NotBlank(message = "物料编码不能为空")
    private String materialCode;

    @NotBlank(message = "批次号不能为空")
    private String batchCode;

    @NotNull(message = "到货数量不能为空")
    @Positive(message = "到货数量必须大于0")
    private Integer arrivedQuantity;

    private UrgencyLevel urgencyLevel;

    private String remark;

    private String inspector;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }
}

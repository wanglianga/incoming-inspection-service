package com.factory.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StorageRecordDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    @NotNull(message = "入库数量不能为空")
    @Positive(message = "入库数量必须大于0")
    private Integer storedQuantity;

    private String warehouseLocation;

    private String receiver;

    private String remark;

    private Long concessionId;

    private String applicableWorkOrder;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getApplicableWorkOrder() {
        return applicableWorkOrder;
    }

    public void setApplicableWorkOrder(String applicableWorkOrder) {
        this.applicableWorkOrder = applicableWorkOrder;
    }
}

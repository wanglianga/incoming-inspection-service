package com.factory.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReturnRecordDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    @NotNull(message = "退货数量不能为空")
    @Positive(message = "退货数量必须大于0")
    private Integer returnQuantity;

    @NotBlank(message = "退货原因不能为空")
    private String returnReason;

    private String handler;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}

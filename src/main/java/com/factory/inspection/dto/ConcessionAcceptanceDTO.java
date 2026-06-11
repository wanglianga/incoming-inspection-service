package com.factory.inspection.dto;

import com.factory.inspection.enums.UrgencyLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ConcessionAcceptanceDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    @NotBlank(message = "让步原因不能为空")
    private String concessionReason;

    private UrgencyLevel urgencyLevel;

    @NotNull(message = "接收数量不能为空")
    @Positive(message = "接收数量必须大于0")
    private Integer acceptedQuantity;

    private String conditions;

    private String applicant;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getConcessionReason() {
        return concessionReason;
    }

    public void setConcessionReason(String concessionReason) {
        this.concessionReason = concessionReason;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Integer getAcceptedQuantity() {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(Integer acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
}

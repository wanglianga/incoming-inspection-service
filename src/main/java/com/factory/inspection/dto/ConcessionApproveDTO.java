package com.factory.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConcessionApproveDTO {
    @NotNull(message = "让步接收ID不能为空")
    private Long concessionId;

    @NotNull(message = "审批结果不能为空")
    private Boolean approved;

    private String approver;

    private String approveRemark;

    public Long getConcessionId() {
        return concessionId;
    }

    public void setConcessionId(Long concessionId) {
        this.concessionId = concessionId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }
}

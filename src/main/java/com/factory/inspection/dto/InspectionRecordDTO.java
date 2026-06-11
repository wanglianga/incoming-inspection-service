package com.factory.inspection.dto;

import com.factory.inspection.enums.InspectionResult;
import com.factory.inspection.enums.InspectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InspectionRecordDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    @NotNull(message = "检验类型不能为空")
    private InspectionType inspectionType;

    @NotBlank(message = "检验项目名称不能为空")
    private String itemName;

    private String measuredValue;

    private String standardValue;

    @NotNull(message = "检验结果不能为空")
    private InspectionResult result;

    private String remark;

    private String inspector;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public InspectionResult getResult() {
        return result;
    }

    public void setResult(InspectionResult result) {
        this.result = result;
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

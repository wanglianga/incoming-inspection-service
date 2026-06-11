package com.factory.inspection.dto;

import com.factory.inspection.enums.DefectLevel;
import com.factory.inspection.enums.UnqualifiedType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DefectRecordDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    @NotNull(message = "不合格类型不能为空")
    private UnqualifiedType unqualifiedType;

    private DefectLevel defectLevel;

    private Integer defectQuantity;

    @NotBlank(message = "缺陷描述不能为空")
    private String description;

    private String recorder;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public UnqualifiedType getUnqualifiedType() {
        return unqualifiedType;
    }

    public void setUnqualifiedType(UnqualifiedType unqualifiedType) {
        this.unqualifiedType = unqualifiedType;
    }

    public DefectLevel getDefectLevel() {
        return defectLevel;
    }

    public void setDefectLevel(DefectLevel defectLevel) {
        this.defectLevel = defectLevel;
    }

    public Integer getDefectQuantity() {
        return defectQuantity;
    }

    public void setDefectQuantity(Integer defectQuantity) {
        this.defectQuantity = defectQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
}

package com.factory.inspection.vo;

import com.factory.inspection.enums.DefectLevel;
import com.factory.inspection.enums.UnqualifiedType;

import java.time.LocalDateTime;

public class DefectRecordVO {

    private Long id;
    private String batchNo;
    private UnqualifiedType unqualifiedType;
    private DefectLevel defectLevel;
    private Integer defectQuantity;
    private String description;
    private String recorder;
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

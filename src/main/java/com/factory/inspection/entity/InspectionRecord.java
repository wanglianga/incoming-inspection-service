package com.factory.inspection.entity;

import com.factory.inspection.enums.InspectionResult;
import com.factory.inspection.enums.InspectionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspection_record")
public class InspectionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InspectionBatch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InspectionType inspectionType;

    @Column(length = 200)
    private String itemName;

    @Column(length = 500)
    private String measuredValue;

    @Column(length = 500)
    private String standardValue;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private InspectionResult result;

    @Column(length = 500)
    private String remark;

    @Column(length = 50)
    private String inspector;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InspectionBatch getBatch() {
        return batch;
    }

    public void setBatch(InspectionBatch batch) {
        this.batch = batch;
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

package com.factory.inspection.entity;

import com.factory.inspection.enums.DefectLevel;
import com.factory.inspection.enums.UnqualifiedType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "defect_record")
public class DefectRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InspectionBatch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UnqualifiedType unqualifiedType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private DefectLevel defectLevel;

    @Column
    private Integer defectQuantity;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String recorder;

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

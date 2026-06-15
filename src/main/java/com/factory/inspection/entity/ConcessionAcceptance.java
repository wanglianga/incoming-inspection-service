package com.factory.inspection.entity;

import com.factory.inspection.enums.UrgencyLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "concession_acceptance")
public class ConcessionAcceptance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InspectionBatch batch;

    @Column(nullable = false, length = 1000)
    private String concessionReason;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private UrgencyLevel urgencyLevel;

    @Column
    private Integer acceptedQuantity;

    @Column(length = 500)
    private String conditions;

    @Column(length = 50)
    private String applicant;

    @Column(length = 50)
    private String approver;

    @Column(length = 20)
    private String approveStatus;

    @Column(length = 500)
    private String approveRemark;

    @Column(length = 1000)
    private String applicableWorkOrders;

    @Column(length = 1000)
    private String riskDescription;

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

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public String getApplicableWorkOrders() {
        return applicableWorkOrders;
    }

    public void setApplicableWorkOrders(String applicableWorkOrders) {
        this.applicableWorkOrders = applicableWorkOrders;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
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

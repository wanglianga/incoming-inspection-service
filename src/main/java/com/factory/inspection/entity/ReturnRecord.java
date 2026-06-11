package com.factory.inspection.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "return_record")
public class ReturnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String returnNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InspectionBatch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private Integer returnQuantity;

    @Column(length = 1000)
    private String returnReason;

    @Column
    private Boolean supplierConfirmed;

    @Column
    private Boolean supplierRejected;

    @Column(length = 500)
    private String supplierRejectReason;

    @Column(length = 50)
    private String handler;

    @Column(length = 50)
    private String status;

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

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public InspectionBatch getBatch() {
        return batch;
    }

    public void setBatch(InspectionBatch batch) {
        this.batch = batch;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public Boolean getSupplierConfirmed() {
        return supplierConfirmed;
    }

    public void setSupplierConfirmed(Boolean supplierConfirmed) {
        this.supplierConfirmed = supplierConfirmed;
    }

    public Boolean getSupplierRejected() {
        return supplierRejected;
    }

    public void setSupplierRejected(Boolean supplierRejected) {
        this.supplierRejected = supplierRejected;
    }

    public String getSupplierRejectReason() {
        return supplierRejectReason;
    }

    public void setSupplierRejectReason(String supplierRejectReason) {
        this.supplierRejectReason = supplierRejectReason;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

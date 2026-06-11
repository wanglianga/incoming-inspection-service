package com.factory.inspection.repository;

import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.enums.InspectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InspectionBatchRepository extends JpaRepository<InspectionBatch, Long> {
    Optional<InspectionBatch> findByBatchNo(String batchNo);
    List<InspectionBatch> findByStatus(InspectionStatus status);
    List<InspectionBatch> findByPurchaseOrderId(Long purchaseOrderId);
}

package com.factory.inspection.repository;

import com.factory.inspection.entity.ConcessionAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConcessionAcceptanceRepository extends JpaRepository<ConcessionAcceptance, Long> {
    List<ConcessionAcceptance> findByBatchId(Long batchId);
    Optional<ConcessionAcceptance> findByBatchIdAndApproveStatus(Long batchId, String approveStatus);
}

package com.factory.inspection.repository;

import com.factory.inspection.entity.ReturnRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReturnRecordRepository extends JpaRepository<ReturnRecord, Long> {
    Optional<ReturnRecord> findByReturnNo(String returnNo);
    List<ReturnRecord> findByBatchId(Long batchId);
}

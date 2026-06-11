package com.factory.inspection.repository;

import com.factory.inspection.entity.DefectRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefectRecordRepository extends JpaRepository<DefectRecord, Long> {
    List<DefectRecord> findByBatchId(Long batchId);
}

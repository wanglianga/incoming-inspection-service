package com.factory.inspection.repository;

import com.factory.inspection.entity.StorageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRecordRepository extends JpaRepository<StorageRecord, Long> {
    Optional<StorageRecord> findByStorageNo(String storageNo);
    List<StorageRecord> findByBatchId(Long batchId);
}

package com.factory.inspection.repository;

import com.factory.inspection.entity.InspectionItem;
import com.factory.inspection.enums.InspectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionItemRepository extends JpaRepository<InspectionItem, Long> {
    List<InspectionItem> findByInspectionType(InspectionType inspectionType);
}

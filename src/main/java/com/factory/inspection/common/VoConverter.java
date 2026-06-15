package com.factory.inspection.common;

import com.factory.inspection.entity.*;
import com.factory.inspection.vo.*;

import java.util.ArrayList;
import java.util.List;

public class VoConverter {

    public static SupplierVO toSupplierVO(Supplier entity) {
        if (entity == null) {
            return null;
        }
        SupplierVO vo = new SupplierVO();
        vo.setId(entity.getId());
        vo.setSupplierCode(entity.getSupplierCode());
        vo.setSupplierName(entity.getSupplierName());
        vo.setContactPerson(entity.getContactPerson());
        vo.setContactPhone(entity.getContactPhone());
        vo.setAddress(entity.getAddress());
        vo.setHistoricalDefectRate(entity.getHistoricalDefectRate());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<SupplierVO> toSupplierVOList(List<Supplier> list) {
        if (list == null) {
            return null;
        }
        List<SupplierVO> result = new ArrayList<>();
        for (Supplier entity : list) {
            result.add(toSupplierVO(entity));
        }
        return result;
    }

    public static MaterialVO toMaterialVO(Material entity) {
        if (entity == null) {
            return null;
        }
        MaterialVO vo = new MaterialVO();
        vo.setId(entity.getId());
        vo.setMaterialCode(entity.getMaterialCode());
        vo.setMaterialName(entity.getMaterialName());
        vo.setSpecification(entity.getSpecification());
        vo.setUnit(entity.getUnit());
        vo.setWarehouseLocation(entity.getWarehouseLocation());
        vo.setRiskLevel(entity.getRiskLevel());
        vo.setIsNewMaterial(entity.getIsNewMaterial());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<MaterialVO> toMaterialVOList(List<Material> list) {
        if (list == null) {
            return null;
        }
        List<MaterialVO> result = new ArrayList<>();
        for (Material entity : list) {
            result.add(toMaterialVO(entity));
        }
        return result;
    }

    public static SimplePurchaseOrderVO toSimplePurchaseOrderVO(PurchaseOrder entity) {
        if (entity == null) {
            return null;
        }
        SimplePurchaseOrderVO vo = new SimplePurchaseOrderVO();
        vo.setId(entity.getId());
        vo.setOrderNo(entity.getOrderNo());
        vo.setOrderQuantity(entity.getOrderQuantity());
        vo.setStatus(entity.getStatus());
        return vo;
    }

    public static List<SimplePurchaseOrderVO> toSimplePurchaseOrderVOList(List<PurchaseOrder> list) {
        if (list == null) {
            return null;
        }
        List<SimplePurchaseOrderVO> result = new ArrayList<>();
        for (PurchaseOrder entity : list) {
            result.add(toSimplePurchaseOrderVO(entity));
        }
        return result;
    }

    public static PurchaseOrderVO toPurchaseOrderVO(PurchaseOrder entity) {
        if (entity == null) {
            return null;
        }
        PurchaseOrderVO vo = new PurchaseOrderVO();
        vo.setId(entity.getId());
        vo.setOrderNo(entity.getOrderNo());
        vo.setSupplier(toSupplierVO(entity.getSupplier()));
        vo.setMaterial(toMaterialVO(entity.getMaterial()));
        vo.setOrderQuantity(entity.getOrderQuantity());
        vo.setRemark(entity.getRemark());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<PurchaseOrderVO> toPurchaseOrderVOList(List<PurchaseOrder> list) {
        if (list == null) {
            return null;
        }
        List<PurchaseOrderVO> result = new ArrayList<>();
        for (PurchaseOrder entity : list) {
            result.add(toPurchaseOrderVO(entity));
        }
        return result;
    }

    public static InspectionBatchVO toInspectionBatchVO(InspectionBatch entity) {
        if (entity == null) {
            return null;
        }
        InspectionBatchVO vo = new InspectionBatchVO();
        vo.setId(entity.getId());
        vo.setBatchNo(entity.getBatchNo());
        vo.setPurchaseOrder(toSimplePurchaseOrderVO(entity.getPurchaseOrder()));
        vo.setSupplier(toSupplierVO(entity.getSupplier()));
        vo.setMaterial(toMaterialVO(entity.getMaterial()));
        vo.setBatchCode(entity.getBatchCode());
        vo.setArrivedQuantity(entity.getArrivedQuantity());
        vo.setSampleQuantity(entity.getSampleQuantity());
        vo.setSamplingScheme(entity.getSamplingScheme());
        vo.setSamplingReasons(entity.getSamplingReasons());
        vo.setUrgencyLevel(entity.getUrgencyLevel());
        vo.setStatus(entity.getStatus());
        vo.setInspector(entity.getInspector());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<InspectionBatchVO> toInspectionBatchVOList(List<InspectionBatch> list) {
        if (list == null) {
            return null;
        }
        List<InspectionBatchVO> result = new ArrayList<>();
        for (InspectionBatch entity : list) {
            result.add(toInspectionBatchVO(entity));
        }
        return result;
    }

    public static InspectionRecordVO toInspectionRecordVO(InspectionRecord entity) {
        if (entity == null) {
            return null;
        }
        InspectionRecordVO vo = new InspectionRecordVO();
        vo.setId(entity.getId());
        if (entity.getBatch() != null) {
            vo.setBatchNo(entity.getBatch().getBatchNo());
        }
        vo.setInspectionType(entity.getInspectionType());
        vo.setItemName(entity.getItemName());
        vo.setMeasuredValue(entity.getMeasuredValue());
        vo.setStandardValue(entity.getStandardValue());
        vo.setResult(entity.getResult());
        vo.setRemark(entity.getRemark());
        vo.setInspector(entity.getInspector());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<InspectionRecordVO> toInspectionRecordVOList(List<InspectionRecord> list) {
        if (list == null) {
            return null;
        }
        List<InspectionRecordVO> result = new ArrayList<>();
        for (InspectionRecord entity : list) {
            result.add(toInspectionRecordVO(entity));
        }
        return result;
    }

    public static DefectRecordVO toDefectRecordVO(DefectRecord entity) {
        if (entity == null) {
            return null;
        }
        DefectRecordVO vo = new DefectRecordVO();
        vo.setId(entity.getId());
        if (entity.getBatch() != null) {
            vo.setBatchNo(entity.getBatch().getBatchNo());
        }
        vo.setUnqualifiedType(entity.getUnqualifiedType());
        vo.setDefectLevel(entity.getDefectLevel());
        vo.setDefectQuantity(entity.getDefectQuantity());
        vo.setDescription(entity.getDescription());
        vo.setRecorder(entity.getRecorder());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<DefectRecordVO> toDefectRecordVOList(List<DefectRecord> list) {
        if (list == null) {
            return null;
        }
        List<DefectRecordVO> result = new ArrayList<>();
        for (DefectRecord entity : list) {
            result.add(toDefectRecordVO(entity));
        }
        return result;
    }

    public static ConcessionAcceptanceVO toConcessionAcceptanceVO(ConcessionAcceptance entity) {
        if (entity == null) {
            return null;
        }
        ConcessionAcceptanceVO vo = new ConcessionAcceptanceVO();
        vo.setId(entity.getId());
        if (entity.getBatch() != null) {
            vo.setBatchNo(entity.getBatch().getBatchNo());
        }
        vo.setConcessionReason(entity.getConcessionReason());
        vo.setUrgencyLevel(entity.getUrgencyLevel());
        vo.setAcceptedQuantity(entity.getAcceptedQuantity());
        vo.setConditions(entity.getConditions());
        vo.setApplicant(entity.getApplicant());
        vo.setApprover(entity.getApprover());
        vo.setApproveStatus(entity.getApproveStatus());
        vo.setApproveRemark(entity.getApproveRemark());
        vo.setApplicableWorkOrders(entity.getApplicableWorkOrders());
        vo.setRiskDescription(entity.getRiskDescription());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<ConcessionAcceptanceVO> toConcessionAcceptanceVOList(List<ConcessionAcceptance> list) {
        if (list == null) {
            return null;
        }
        List<ConcessionAcceptanceVO> result = new ArrayList<>();
        for (ConcessionAcceptance entity : list) {
            result.add(toConcessionAcceptanceVO(entity));
        }
        return result;
    }

    public static ReturnRecordVO toReturnRecordVO(ReturnRecord entity) {
        if (entity == null) {
            return null;
        }
        ReturnRecordVO vo = new ReturnRecordVO();
        vo.setId(entity.getId());
        vo.setReturnNo(entity.getReturnNo());
        if (entity.getBatch() != null) {
            vo.setBatchNo(entity.getBatch().getBatchNo());
        }
        vo.setSupplier(toSupplierVO(entity.getSupplier()));
        vo.setReturnQuantity(entity.getReturnQuantity());
        vo.setReturnReason(entity.getReturnReason());
        vo.setSupplierConfirmed(entity.getSupplierConfirmed());
        vo.setSupplierRejected(entity.getSupplierRejected());
        vo.setSupplierRejectReason(entity.getSupplierRejectReason());
        vo.setHandler(entity.getHandler());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<ReturnRecordVO> toReturnRecordVOList(List<ReturnRecord> list) {
        if (list == null) {
            return null;
        }
        List<ReturnRecordVO> result = new ArrayList<>();
        for (ReturnRecord entity : list) {
            result.add(toReturnRecordVO(entity));
        }
        return result;
    }

    public static StorageRecordVO toStorageRecordVO(StorageRecord entity) {
        if (entity == null) {
            return null;
        }
        StorageRecordVO vo = new StorageRecordVO();
        vo.setId(entity.getId());
        vo.setStorageNo(entity.getStorageNo());
        if (entity.getBatch() != null) {
            vo.setBatchNo(entity.getBatch().getBatchNo());
        }
        vo.setMaterial(toMaterialVO(entity.getMaterial()));
        vo.setStoredQuantity(entity.getStoredQuantity());
        vo.setWarehouseLocation(entity.getWarehouseLocation());
        vo.setReceiver(entity.getReceiver());
        vo.setRemark(entity.getRemark());
        vo.setConcessionId(entity.getConcessionId());
        vo.setIsConcessionRestricted(entity.getIsConcessionRestricted());
        vo.setApplicableWorkOrder(entity.getApplicableWorkOrder());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<StorageRecordVO> toStorageRecordVOList(List<StorageRecord> list) {
        if (list == null) {
            return null;
        }
        List<StorageRecordVO> result = new ArrayList<>();
        for (StorageRecord entity : list) {
            result.add(toStorageRecordVO(entity));
        }
        return result;
    }
}

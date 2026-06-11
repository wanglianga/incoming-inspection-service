package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.entity.Material;
import com.factory.inspection.entity.PurchaseOrder;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.PurchaseOrderRepository;
import com.factory.inspection.vo.PurchaseOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierService supplierService;
    private final MaterialService materialService;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                SupplierService supplierService,
                                MaterialService materialService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierService = supplierService;
        this.materialService = materialService;
    }

    @Transactional
    public PurchaseOrderVO create(PurchaseOrder order) {
        if (purchaseOrderRepository.findByOrderNo(order.getOrderNo()).isPresent()) {
            throw new BusinessException("采购单号已存在");
        }
        Supplier supplier = supplierService.getByCodeInternal(order.getSupplier().getSupplierCode());
        Material material = materialService.getByCodeInternal(order.getMaterial().getMaterialCode());
        order.setSupplier(supplier);
        order.setMaterial(material);
        if (order.getStatus() == null) {
            order.setStatus("CREATED");
        }
        return VoConverter.toPurchaseOrderVO(purchaseOrderRepository.save(order));
    }

    public PurchaseOrderVO getById(Long id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("采购单不存在"));
        if (order.getSupplier() != null) {
            order.getSupplier().getSupplierName();
        }
        if (order.getMaterial() != null) {
            order.getMaterial().getMaterialName();
        }
        return VoConverter.toPurchaseOrderVO(order);
    }

    public PurchaseOrderVO getByOrderNo(String orderNo) {
        PurchaseOrder order = purchaseOrderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("采购单不存在: " + orderNo));
        if (order.getSupplier() != null) {
            order.getSupplier().getSupplierName();
        }
        if (order.getMaterial() != null) {
            order.getMaterial().getMaterialName();
        }
        return VoConverter.toPurchaseOrderVO(order);
    }

    public List<PurchaseOrderVO> list() {
        List<PurchaseOrder> orders = purchaseOrderRepository.findAll();
        for (PurchaseOrder order : orders) {
            if (order.getSupplier() != null) {
                order.getSupplier().getSupplierName();
            }
            if (order.getMaterial() != null) {
                order.getMaterial().getMaterialName();
            }
        }
        return VoConverter.toPurchaseOrderVOList(orders);
    }

    @Transactional
    public PurchaseOrderVO update(Long id, PurchaseOrder order) {
        PurchaseOrder existing = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("采购单不存在"));
        existing.setOrderQuantity(order.getOrderQuantity());
        existing.setRemark(order.getRemark());
        existing.setStatus(order.getStatus());
        return VoConverter.toPurchaseOrderVO(purchaseOrderRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    PurchaseOrder getByOrderNoInternal(String orderNo) {
        return purchaseOrderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("采购单不存在: " + orderNo));
    }
}

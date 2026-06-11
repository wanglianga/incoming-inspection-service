package com.factory.inspection.service;

import com.factory.inspection.entity.Material;
import com.factory.inspection.entity.PurchaseOrder;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.PurchaseOrderRepository;
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
    public PurchaseOrder create(PurchaseOrder order) {
        if (purchaseOrderRepository.findByOrderNo(order.getOrderNo()).isPresent()) {
            throw new BusinessException("采购单号已存在");
        }
        Supplier supplier = supplierService.getByCode(order.getSupplier().getSupplierCode());
        Material material = materialService.getByCode(order.getMaterial().getMaterialCode());
        order.setSupplier(supplier);
        order.setMaterial(material);
        if (order.getStatus() == null) {
            order.setStatus("CREATED");
        }
        return purchaseOrderRepository.save(order);
    }

    public PurchaseOrder getById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("采购单不存在"));
    }

    public PurchaseOrder getByOrderNo(String orderNo) {
        return purchaseOrderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("采购单不存在: " + orderNo));
    }

    public List<PurchaseOrder> list() {
        return purchaseOrderRepository.findAll();
    }

    @Transactional
    public PurchaseOrder update(Long id, PurchaseOrder order) {
        PurchaseOrder existing = getById(id);
        existing.setOrderQuantity(order.getOrderQuantity());
        existing.setRemark(order.getRemark());
        existing.setStatus(order.getStatus());
        return purchaseOrderRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}

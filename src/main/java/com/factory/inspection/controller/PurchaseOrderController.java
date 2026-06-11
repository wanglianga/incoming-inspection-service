package com.factory.inspection.controller;

import com.factory.inspection.common.Result;
import com.factory.inspection.entity.PurchaseOrder;
import com.factory.inspection.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public Result<PurchaseOrder> create(@Valid @RequestBody PurchaseOrder order) {
        return Result.success(purchaseOrderService.create(order));
    }

    @GetMapping("/{id}")
    public Result<PurchaseOrder> getById(@PathVariable Long id) {
        return Result.success(purchaseOrderService.getById(id));
    }

    @GetMapping("/orderNo/{orderNo}")
    public Result<PurchaseOrder> getByOrderNo(@PathVariable String orderNo) {
        return Result.success(purchaseOrderService.getByOrderNo(orderNo));
    }

    @GetMapping
    public Result<List<PurchaseOrder>> list() {
        return Result.success(purchaseOrderService.list());
    }

    @PutMapping("/{id}")
    public Result<PurchaseOrder> update(@PathVariable Long id, @Valid @RequestBody PurchaseOrder order) {
        return Result.success(purchaseOrderService.update(id, order));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        purchaseOrderService.delete(id);
        return Result.success();
    }
}

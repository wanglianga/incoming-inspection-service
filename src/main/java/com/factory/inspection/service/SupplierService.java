package com.factory.inspection.service;

import com.factory.inspection.entity.Supplier;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public Supplier create(Supplier supplier) {
        if (supplierRepository.findBySupplierCode(supplier.getSupplierCode()).isPresent()) {
            throw new BusinessException("供应商编码已存在");
        }
        return supplierRepository.save(supplier);
    }

    public Supplier getById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("供应商不存在"));
    }

    public Supplier getByCode(String supplierCode) {
        return supplierRepository.findBySupplierCode(supplierCode)
                .orElseThrow(() -> new BusinessException("供应商不存在: " + supplierCode));
    }

    public List<Supplier> list() {
        return supplierRepository.findAll();
    }

    @Transactional
    public Supplier update(Long id, Supplier supplier) {
        Supplier existing = getById(id);
        existing.setSupplierName(supplier.getSupplierName());
        existing.setContactPerson(supplier.getContactPerson());
        existing.setContactPhone(supplier.getContactPhone());
        existing.setAddress(supplier.getAddress());
        return supplierRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }
}

package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.entity.Supplier;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.SupplierRepository;
import com.factory.inspection.vo.SupplierVO;
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
    public SupplierVO create(Supplier supplier) {
        if (supplierRepository.findBySupplierCode(supplier.getSupplierCode()).isPresent()) {
            throw new BusinessException("供应商编码已存在");
        }
        return VoConverter.toSupplierVO(supplierRepository.save(supplier));
    }

    public SupplierVO getById(Long id) {
        return VoConverter.toSupplierVO(supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("供应商不存在")));
    }

    public SupplierVO getByCode(String supplierCode) {
        return VoConverter.toSupplierVO(supplierRepository.findBySupplierCode(supplierCode)
                .orElseThrow(() -> new BusinessException("供应商不存在: " + supplierCode)));
    }

    public List<SupplierVO> list() {
        return VoConverter.toSupplierVOList(supplierRepository.findAll());
    }

    @Transactional
    public SupplierVO update(Long id, Supplier supplier) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("供应商不存在"));
        existing.setSupplierName(supplier.getSupplierName());
        existing.setContactPerson(supplier.getContactPerson());
        existing.setContactPhone(supplier.getContactPhone());
        existing.setAddress(supplier.getAddress());
        return VoConverter.toSupplierVO(supplierRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    public Supplier getByCodeInternal(String supplierCode) {
        return supplierRepository.findBySupplierCode(supplierCode)
                .orElseThrow(() -> new BusinessException("供应商不存在: " + supplierCode));
    }
}

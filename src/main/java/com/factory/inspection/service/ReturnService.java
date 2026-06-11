package com.factory.inspection.service;

import com.factory.inspection.dto.ReturnRecordDTO;
import com.factory.inspection.dto.SupplierConfirmReturnDTO;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.entity.ReturnRecord;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.enums.UnqualifiedType;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.ReturnRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReturnService {

    private final ReturnRecordRepository returnRecordRepository;
    private final InspectionBatchService inspectionBatchService;

    private final AtomicInteger returnCounter = new AtomicInteger(0);

    @Autowired
    public ReturnService(ReturnRecordRepository returnRecordRepository,
                         InspectionBatchService inspectionBatchService) {
        this.returnRecordRepository = returnRecordRepository;
        this.inspectionBatchService = inspectionBatchService;
    }

    @Transactional
    public ReturnRecord createReturn(ReturnRecordDTO dto) {
        InspectionBatch batch = inspectionBatchService.getByBatchNo(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.UNQUALIFIED
                && batch.getStatus() != InspectionStatus.CONCESSION_REJECTED) {
            throw new BusinessException("当前批次状态不允许创建退货单，当前状态: " + batch.getStatus());
        }

        if (dto.getReturnQuantity() > batch.getArrivedQuantity()) {
            throw new BusinessException("退货数量不能大于到货数量");
        }

        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setReturnNo(generateReturnNo());
        returnRecord.setBatch(batch);
        returnRecord.setSupplier(batch.getSupplier());
        returnRecord.setReturnQuantity(dto.getReturnQuantity());
        returnRecord.setReturnReason(dto.getReturnReason());
        returnRecord.setHandler(dto.getHandler());
        returnRecord.setSupplierConfirmed(false);
        returnRecord.setSupplierRejected(false);
        returnRecord.setStatus("PENDING_SUPPLIER_CONFIRM");

        inspectionBatchService.updateStatus(dto.getBatchNo(), InspectionStatus.RETURN_PENDING);

        return returnRecordRepository.save(returnRecord);
    }

    @Transactional
    public ReturnRecord supplierConfirm(SupplierConfirmReturnDTO dto) {
        ReturnRecord returnRecord = returnRecordRepository.findByReturnNo(dto.getReturnNo())
                .orElseThrow(() -> new BusinessException("退货单不存在: " + dto.getReturnNo()));

        if (dto.getConfirmed()) {
            returnRecord.setSupplierConfirmed(true);
            returnRecord.setStatus("CONFIRMED");
            inspectionBatchService.updateStatus(returnRecord.getBatch().getBatchNo(), InspectionStatus.RETURNED);
        } else {
            returnRecord.setSupplierRejected(true);
            returnRecord.setSupplierRejectReason(dto.getRejectReason());
            returnRecord.setStatus("SUPPLIER_REJECTED");
            inspectionBatchService.updateStatus(returnRecord.getBatch().getBatchNo(), InspectionStatus.REJECTED_BY_SUPPLIER);
        }

        return returnRecordRepository.save(returnRecord);
    }

    public ReturnRecord getById(Long id) {
        return returnRecordRepository.findById(id)
                .orElseThrow(() -> new BusinessException("退货单不存在"));
    }

    public ReturnRecord getByReturnNo(String returnNo) {
        return returnRecordRepository.findByReturnNo(returnNo)
                .orElseThrow(() -> new BusinessException("退货单不存在: " + returnNo));
    }

    public List<ReturnRecord> list() {
        return returnRecordRepository.findAll();
    }

    public List<ReturnRecord> getByBatchId(Long batchId) {
        return returnRecordRepository.findByBatchId(batchId);
    }

    private String generateReturnNo() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int seq = returnCounter.incrementAndGet() % 1000;
        return "RT" + datePart + String.format("%03d", seq);
    }
}

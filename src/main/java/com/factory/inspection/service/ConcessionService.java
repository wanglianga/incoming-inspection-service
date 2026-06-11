package com.factory.inspection.service;

import com.factory.inspection.dto.ConcessionAcceptanceDTO;
import com.factory.inspection.dto.ConcessionApproveDTO;
import com.factory.inspection.entity.ConcessionAcceptance;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.ConcessionAcceptanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConcessionService {

    private final ConcessionAcceptanceRepository concessionAcceptanceRepository;
    private final InspectionBatchService inspectionBatchService;

    @Autowired
    public ConcessionService(ConcessionAcceptanceRepository concessionAcceptanceRepository,
                             InspectionBatchService inspectionBatchService) {
        this.concessionAcceptanceRepository = concessionAcceptanceRepository;
        this.inspectionBatchService = inspectionBatchService;
    }

    @Transactional
    public ConcessionAcceptance applyConcession(ConcessionAcceptanceDTO dto) {
        InspectionBatch batch = inspectionBatchService.getByBatchNo(dto.getBatchNo());

        if (batch.getStatus() != InspectionStatus.UNQUALIFIED) {
            throw new BusinessException("当前批次状态不允许申请让步接收，当前状态: " + batch.getStatus());
        }

        if (dto.getAcceptedQuantity() > batch.getArrivedQuantity()) {
            throw new BusinessException("接收数量不能大于到货数量");
        }

        ConcessionAcceptance concession = new ConcessionAcceptance();
        concession.setBatch(batch);
        concession.setConcessionReason(dto.getConcessionReason());
        concession.setUrgencyLevel(dto.getUrgencyLevel());
        concession.setAcceptedQuantity(dto.getAcceptedQuantity());
        concession.setConditions(dto.getConditions());
        concession.setApplicant(dto.getApplicant());
        concession.setApproveStatus("PENDING");

        inspectionBatchService.updateStatus(dto.getBatchNo(), InspectionStatus.CONCESSION_PENDING);

        return concessionAcceptanceRepository.save(concession);
    }

    @Transactional
    public ConcessionAcceptance approveConcession(ConcessionApproveDTO dto) {
        ConcessionAcceptance concession = concessionAcceptanceRepository.findById(dto.getConcessionId())
                .orElseThrow(() -> new BusinessException("让步接收申请不存在"));

        if (!"PENDING".equals(concession.getApproveStatus())) {
            throw new BusinessException("该申请已处理，当前状态: " + concession.getApproveStatus());
        }

        concession.setApprover(dto.getApprover());
        concession.setApproveRemark(dto.getApproveRemark());

        if (dto.getApproved()) {
            concession.setApproveStatus("APPROVED");
            inspectionBatchService.updateStatus(concession.getBatch().getBatchNo(), InspectionStatus.CONCESSION_APPROVED);
        } else {
            concession.setApproveStatus("REJECTED");
            inspectionBatchService.updateStatus(concession.getBatch().getBatchNo(), InspectionStatus.CONCESSION_REJECTED);
        }

        return concessionAcceptanceRepository.save(concession);
    }

    public ConcessionAcceptance getById(Long id) {
        return concessionAcceptanceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("让步接收申请不存在"));
    }

    public List<ConcessionAcceptance> list() {
        return concessionAcceptanceRepository.findAll();
    }

    public List<ConcessionAcceptance> getByBatchId(Long batchId) {
        return concessionAcceptanceRepository.findByBatchId(batchId);
    }
}

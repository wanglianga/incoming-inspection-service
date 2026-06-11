package com.factory.inspection.service;

import com.factory.inspection.common.VoConverter;
import com.factory.inspection.dto.ConcessionAcceptanceDTO;
import com.factory.inspection.dto.ConcessionApproveDTO;
import com.factory.inspection.entity.ConcessionAcceptance;
import com.factory.inspection.entity.InspectionBatch;
import com.factory.inspection.enums.InspectionStatus;
import com.factory.inspection.exception.BusinessException;
import com.factory.inspection.repository.ConcessionAcceptanceRepository;
import com.factory.inspection.vo.ConcessionAcceptanceVO;
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
    public ConcessionAcceptanceVO applyConcession(ConcessionAcceptanceDTO dto) {
        InspectionBatch batch = inspectionBatchService.getByBatchNoInternal(dto.getBatchNo());

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

        ConcessionAcceptance saved = concessionAcceptanceRepository.save(concession);
        if (saved.getBatch() != null) {
            saved.getBatch().getBatchNo();
        }
        return VoConverter.toConcessionAcceptanceVO(saved);
    }

    @Transactional
    public ConcessionAcceptanceVO approveConcession(ConcessionApproveDTO dto) {
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

        ConcessionAcceptance saved = concessionAcceptanceRepository.save(concession);
        if (saved.getBatch() != null) {
            saved.getBatch().getBatchNo();
        }
        return VoConverter.toConcessionAcceptanceVO(saved);
    }

    public ConcessionAcceptanceVO getById(Long id) {
        ConcessionAcceptance concession = concessionAcceptanceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("让步接收申请不存在"));
        if (concession.getBatch() != null) {
            concession.getBatch().getBatchNo();
        }
        return VoConverter.toConcessionAcceptanceVO(concession);
    }

    public List<ConcessionAcceptanceVO> list() {
        List<ConcessionAcceptance> list = concessionAcceptanceRepository.findAll();
        for (ConcessionAcceptance concession : list) {
            if (concession.getBatch() != null) {
                concession.getBatch().getBatchNo();
            }
        }
        return VoConverter.toConcessionAcceptanceVOList(list);
    }

    public List<ConcessionAcceptanceVO> getByBatchId(Long batchId) {
        List<ConcessionAcceptance> list = concessionAcceptanceRepository.findByBatchId(batchId);
        for (ConcessionAcceptance concession : list) {
            if (concession.getBatch() != null) {
                concession.getBatch().getBatchNo();
            }
        }
        return VoConverter.toConcessionAcceptanceVOList(list);
    }
}

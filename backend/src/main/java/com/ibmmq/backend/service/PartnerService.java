package com.ibmmq.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ibmmq.backend.dto.PartnerDTO;
import com.ibmmq.backend.dto.PartnerRequest;

public interface PartnerService {
    Page<PartnerDTO> getAllPartners(Pageable pageable);
    PartnerDTO getPartnerById(Long id);
    PartnerDTO createPartner(PartnerRequest partnerRequest);
    void deletePartner(Long id);
}

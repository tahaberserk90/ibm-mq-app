
package com.ibmmq.backend.service.impl;

import com.ibmmq.backend.dto.PartnerDTO;
import com.ibmmq.backend.dto.PartnerRequest;
import com.ibmmq.backend.exception.ResourceNotFoundException;
import com.ibmmq.backend.model.Partner;
import com.ibmmq.backend.repo.PartnerRepository;
import com.ibmmq.backend.service.PartnerService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final ModelMapper modelMapper;

    public PartnerServiceImpl(PartnerRepository partnerRepository, ModelMapper modelMapper) {
        this.partnerRepository = partnerRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all partners with pagination.
     *
     * @param pageable the pagination information
     * @return a page of PartnerDTO objects
     */
    public Page<PartnerDTO> getAllPartners(Pageable pageable) {
        return partnerRepository.findAll(pageable)
                .map(partner -> modelMapper.map(partner, PartnerDTO.class));
    }

    /**
     * Retrieves a partner by its ID.
     *
     * @param id the ID of the partner
     * @return the PartnerDTO object
     * @throws ResourceNotFoundException if the partner is not found
     */
    public PartnerDTO getPartnerById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("partenaire non trouve id: " + id));
        return modelMapper.map(partner, PartnerDTO.class);
    }

    /**
     * Creates a new partner.
     *
     * @param partnerRequest the PartnerRequest object containing partner details
     * @return the created PartnerDTO object
     * @throws IllegalArgumentException if a partner with the same alias already
     *                                  exists
     */
    public PartnerDTO createPartner(PartnerRequest partnerRequest) {
        if (partnerRepository.existsByAlias(partnerRequest.getAlias())) {
            throw new IllegalArgumentException(
                    "partenaire avec ce alias " + partnerRequest.getAlias() + " existe deja");
        }

        Partner partner = modelMapper.map(partnerRequest, Partner.class);
        partner = partnerRepository.save(partner);
        return modelMapper.map(partner, PartnerDTO.class);
    }

    /**
     * Updates an existing partner.
     *
     * @param id             the ID of the partner to update
     * @param partnerRequest the PartnerRequest object containing updated partner
     *                       details
     * @return the updated PartnerDTO object
     * @throws ResourceNotFoundException if the partner is not found
     */
    public void deletePartner(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new ResourceNotFoundException("partenaire non trouve id: " + id);
        }
        partnerRepository.deleteById(id);
    }
}
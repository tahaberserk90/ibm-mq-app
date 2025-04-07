package com.ibmmq.backend.service;

import com.ibmmq.backend.dto.PartnerDTO;
import com.ibmmq.backend.dto.PartnerRequest;
import com.ibmmq.backend.exception.ResourceNotFoundException;
import com.ibmmq.backend.model.Partner;
import com.ibmmq.backend.repo.PartnerRepository;
import com.ibmmq.backend.service.impl.PartnerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPartners_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Partner partner = new Partner();
        PartnerDTO partnerDTO = new PartnerDTO();
        Page<Partner> partnerPage = new PageImpl<>(Collections.singletonList(partner));

        when(partnerRepository.findAll(pageable)).thenReturn(partnerPage);
        when(modelMapper.map(partner, PartnerDTO.class)).thenReturn(partnerDTO);

        Page<PartnerDTO> result = partnerService.getAllPartners(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(partnerRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(partner, PartnerDTO.class);
    }

    @Test
    void testGetPartnerById_Success() {
        Long partnerId = 1L;
        Partner partner = new Partner();
        PartnerDTO partnerDTO = new PartnerDTO();

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));
        when(modelMapper.map(partner, PartnerDTO.class)).thenReturn(partnerDTO);

        PartnerDTO result = partnerService.getPartnerById(partnerId);

        assertNotNull(result);
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(modelMapper, times(1)).map(partner, PartnerDTO.class);
    }

    @Test
    void testGetPartnerById_NotFound() {
        Long partnerId = 1L;

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> partnerService.getPartnerById(partnerId));
        verify(partnerRepository, times(1)).findById(partnerId);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testCreatePartner_Success() {
        PartnerRequest partnerRequest = new PartnerRequest();
        partnerRequest.setAlias("uniqueAlias");
        Partner partner = new Partner();
        PartnerDTO partnerDTO = new PartnerDTO();

        when(partnerRepository.existsByAlias(partnerRequest.getAlias())).thenReturn(false);
        when(modelMapper.map(partnerRequest, Partner.class)).thenReturn(partner);
        when(partnerRepository.save(partner)).thenReturn(partner);
        when(modelMapper.map(partner, PartnerDTO.class)).thenReturn(partnerDTO);

        PartnerDTO result = partnerService.createPartner(partnerRequest);

        assertNotNull(result);
        verify(partnerRepository, times(1)).existsByAlias(partnerRequest.getAlias());
        verify(partnerRepository, times(1)).save(partner);
        verify(modelMapper, times(1)).map(partnerRequest, Partner.class);
        verify(modelMapper, times(1)).map(partner, PartnerDTO.class);
    }

    @Test
    void testCreatePartner_AliasExists() {
        PartnerRequest partnerRequest = new PartnerRequest();
        partnerRequest.setAlias("existingAlias");

        when(partnerRepository.existsByAlias(partnerRequest.getAlias())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> partnerService.createPartner(partnerRequest));
        verify(partnerRepository, times(1)).existsByAlias(partnerRequest.getAlias());
        verifyNoMoreInteractions(partnerRepository);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testDeletePartner_Success() {
        Long partnerId = 1L;

        when(partnerRepository.existsById(partnerId)).thenReturn(true);

        partnerService.deletePartner(partnerId);

        verify(partnerRepository, times(1)).existsById(partnerId);
        verify(partnerRepository, times(1)).deleteById(partnerId);
    }

    @Test
    void testDeletePartner_NotFound() {
        Long partnerId = 1L;

        when(partnerRepository.existsById(partnerId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> partnerService.deletePartner(partnerId));
        verify(partnerRepository, times(1)).existsById(partnerId);
        verifyNoMoreInteractions(partnerRepository);
    }
}
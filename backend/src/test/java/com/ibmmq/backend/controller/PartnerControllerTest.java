package com.ibmmq.backend.controller;

import com.ibmmq.backend.dto.PartnerDTO;
import com.ibmmq.backend.dto.PartnerRequest;
import com.ibmmq.backend.service.PartnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PartnerControllerTest {

    @Mock
    private PartnerService partnerService;

    @InjectMocks
    private PartnerController partnerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(partnerController).build();
    }

    @Test
    void testDeletePartner_Success() throws Exception {
        Long partnerId = 1L;

        doNothing().when(partnerService).deletePartner(partnerId);

        mockMvc.perform(delete("/api/partners/{id}", partnerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(partnerService, times(1)).deletePartner(partnerId);
    }

    @Test
    void testGetPartnerById_Success() throws Exception {
        Long partnerId = 1L;
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setId(partnerId);
        partnerDTO.setAlias("Test Partner");

        when(partnerService.getPartnerById(partnerId)).thenReturn(partnerDTO);

        mockMvc.perform(get("/api/partners/{id}", partnerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(partnerId))
                .andExpect(jsonPath("$.alias").value("Test Partner"));

        verify(partnerService, times(1)).getPartnerById(partnerId);
    }

    @Test
    void testCreatePartner_InvalidRequest() throws Exception {
        mockMvc.perform(post("/api/partners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        verify(partnerService, times(0)).createPartner(any(PartnerRequest.class));
    }
}
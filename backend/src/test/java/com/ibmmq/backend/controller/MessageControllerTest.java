package com.ibmmq.backend.controller;

import com.ibmmq.backend.dto.MessageDTO;
import com.ibmmq.backend.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void testGetMessageById() throws Exception {
        Long messageId = 1L;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(messageId);
        messageDTO.setContent("Test Message");
        when(messageService.getMessageById(messageId)).thenReturn(messageDTO);

        mockMvc.perform(get("/api/messages/{id}", messageId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(messageId))
                .andExpect(jsonPath("$.content").value("Test Message"));

        verify(messageService, times(1)).getMessageById(messageId);
    }
}
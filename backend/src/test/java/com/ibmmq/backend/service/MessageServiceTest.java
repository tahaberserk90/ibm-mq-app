package com.ibmmq.backend.service;

import com.ibmmq.backend.dto.MessageDTO;
import com.ibmmq.backend.exception.ResourceNotFoundException;
import com.ibmmq.backend.model.Message;
import com.ibmmq.backend.repo.MessageRepository;
import com.ibmmq.backend.service.impl.MessageServiceImpl;

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

class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMessages_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Message message = new Message();
        MessageDTO messageDTO = new MessageDTO();
        Page<Message> messagePage = new PageImpl<>(Collections.singletonList(message));

        when(messageRepository.findAll(pageable)).thenReturn(messagePage);
        when(modelMapper.map(message, MessageDTO.class)).thenReturn(messageDTO);

        Page<MessageDTO> result = messageService.getAllMessages(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(messageRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(message, MessageDTO.class);
    }

    @Test
    void testGetMessageById_Success() {
        Long messageId = 1L;
        Message message = new Message();
        MessageDTO messageDTO = new MessageDTO();

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));
        when(modelMapper.map(message, MessageDTO.class)).thenReturn(messageDTO);

        MessageDTO result = messageService.getMessageById(messageId);

        assertNotNull(result);
        verify(messageRepository, times(1)).findById(messageId);
        verify(modelMapper, times(1)).map(message, MessageDTO.class);
    }

    @Test
    void testGetMessageById_NotFound() {
        Long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> messageService.getMessageById(messageId));
        verify(messageRepository, times(1)).findById(messageId);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testSaveMessage_Success() {
        Message message = new Message();

        doReturn(message).when(messageRepository).save(message);

        messageService.saveMessage(message);

        verify(messageRepository, times(1)).save(message);
    }
}
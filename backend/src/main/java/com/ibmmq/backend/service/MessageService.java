package com.ibmmq.backend.service;

import com.ibmmq.backend.dto.MessageDTO;
import com.ibmmq.backend.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Page<MessageDTO> getAllMessages(Pageable pageable);
    MessageDTO getMessageById(Long id);
    void saveMessage(Message message);
}
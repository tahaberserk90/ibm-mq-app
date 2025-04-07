
package com.ibmmq.backend.service.impl;

import com.ibmmq.backend.dto.MessageDTO;
import com.ibmmq.backend.exception.ResourceNotFoundException;
import com.ibmmq.backend.model.Message;
import com.ibmmq.backend.repo.MessageRepository;
import com.ibmmq.backend.service.MessageService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all messages with pagination.
     *
     * @param pageable the pagination information
     * @return a page of MessageDTO objects
     */
    public Page<MessageDTO> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable)
                .map(message -> modelMapper.map(message, MessageDTO.class));
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param id the ID of the message
     * @return the MessageDTO object
     * @throws ResourceNotFoundException if the message is not found
     */
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message non trouve  id: " + id));
        return modelMapper.map(message, MessageDTO.class);
    }

    /**
     * Saves a message to the database.
     *
     * @param message the Message object to save
     */
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
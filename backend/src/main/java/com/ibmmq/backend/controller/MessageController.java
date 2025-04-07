package com.ibmmq.backend.controller;

import com.ibmmq.backend.dto.MessageDTO;
import com.ibmmq.backend.service.MessageService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Controller for handling message-related requests.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint to retrieve all messages with pagination.
     *
     * @param pageable the pagination information
     * @return a paginated list of messages
     */
    @GetMapping
    public ResponseEntity<Page<MessageDTO>> getAllMessages(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(messageService.getAllMessages(pageable));
    }

    /**
     * Endpoint to retrieve a message by its ID.
     *
     * @param id the ID of the message
     * @return the message with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }
}
package com.ibmmq.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private String content;
    private LocalDateTime receivedAt;
    private String messageId;
    private String correlationId;
}


package com.ibmmq.backend.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "correlation_id")
    private String correlationId;

    @PrePersist
    protected void onCreate() {
        receivedAt = LocalDateTime.now();
    }
}
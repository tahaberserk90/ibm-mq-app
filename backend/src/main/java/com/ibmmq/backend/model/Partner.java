
package com.ibmmq.backend.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "partners")
@Data
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String alias;

    @NotBlank
    private String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Direction direction;

    private String application;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "processed_flow_type")
    private ProcessedFlowType processedFlowType;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    public enum Direction {
        INBOUND, OUTBOUND
    }

    public enum ProcessedFlowType {
        MESSAGE, ALERTING, NOTIFICATION
    }
}
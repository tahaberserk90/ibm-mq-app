package com.ibmmq.backend.dto;

import com.ibmmq.backend.model.Partner.Direction;
import com.ibmmq.backend.model.Partner.ProcessedFlowType;
import lombok.Data;

@Data
public class PartnerDTO {
    private Long id;
    private String alias;
    private String type;
    private Direction direction;
    private String application;
    private ProcessedFlowType processedFlowType;
    private String description;
}

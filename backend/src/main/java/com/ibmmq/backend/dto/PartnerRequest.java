
package com.ibmmq.backend.dto;

import com.ibmmq.backend.model.Partner.Direction;
import com.ibmmq.backend.model.Partner.ProcessedFlowType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PartnerRequest {
    @NotBlank
    private String alias;

    @NotBlank
    private String type;

    @NotNull
    private Direction direction;

    private String application;

    @NotNull
    private ProcessedFlowType processedFlowType;

    @NotBlank
    private String description;
}
package com.brasiltec23.tinnova.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "DTO para consultar veículo")
public class VehicleDTO {

    @Schema(description = "Id", example = "1")
    private Long id;

    @Schema(description = "Indicador de vendido", example = "true")
    private Boolean sold;

    @Schema(description = "Data da inclusão", example = "2025-04-02T22:44:33.717319")
    private LocalDateTime createdIn;

    @Schema(description = "Data da alteração", example = "2025-04-02T22:44:33.717319")
    private LocalDateTime updatedIn;

    @JsonProperty("base")
    @Schema(description = "Dados de base")
    private BaseVehicleDTO baseVehicleDTO;
}

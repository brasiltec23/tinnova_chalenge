package com.brasiltec23.tinnova.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "DTO para atualizar veículo")
public class VehicleToUpdateDTO {

    @Schema(description = "Id", example = "1")
    private Long id;

    @Schema(description = "Indicador de vendido", example = "true")
    private Boolean sold;

    @JsonProperty("base")
    @Schema(description = "Dados de base")
    private BaseVehicleDTO baseVehicleDTO;
}

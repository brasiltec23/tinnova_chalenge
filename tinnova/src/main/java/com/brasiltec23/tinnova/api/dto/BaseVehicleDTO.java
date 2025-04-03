package com.brasiltec23.tinnova.api.dto;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "DTO de base veículo, e também para incluir um novo veículo")
public class BaseVehicleDTO {

    @Schema(description = "Nome", example = "Jetta")
    private String name;
    @Schema(description = "Marca do veículo")
    private BrandEnum brand;
    @Schema(description = "Ano de fabricação", example = "2023")
    private Integer yearOfManufacture;
    @Schema(description = "Descrição", example = "Jetta GLI 2.0")
    private String description;
    @Schema(description = "Cor", example = "Cinza com vermelho")
    private String color;
}

package com.brasiltec23.tinnova.api.filter;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleFilter {

    @Schema(description = "Marca do veículo")
    private BrandEnum brand;

    @Schema(description = "Ano de fabricação", example = "2023")
    private Integer yearOfManufacture;

    @Schema(description = "Cor", example = "Cinza com vermelho")
    private String color;
}

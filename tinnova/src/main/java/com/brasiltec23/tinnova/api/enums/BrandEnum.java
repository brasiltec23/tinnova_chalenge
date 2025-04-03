package com.brasiltec23.tinnova.api.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(
        description = "Enumeração de marcas de veículos disponíveis no sistema",
        allowableValues = {
                "VOLKSWAGEN", "MITSUBISHI", "BMW", "AUDI", "CHEVROLET",
                "FIAT", "CAOA_CHERY", "NISSAN", "TOYOTA", "HONDA",
                "FORD", "HYUNDAI", "JAGUAR", "LAND_HOVER", "FERRARI"
        }
)
public enum BrandEnum {

    VOLKSWAGEN("Volkswagen"),
    MITSUBISHI("Mitsubishi"),
    BMW("BMW"),
    AUDI("Audi"),
    CHEVROLET("Chevrolet"),
    FIAT("Fiat"),
    CAOA_CHERY("CAOA Chery"),
    NISSAN("Nissan"),
    TOYOTA("Toyota"),
    HONDA("Honda"),
    FORD("Ford"),
    HYUNDAI("Hyundai"),
    JAGUAR("Jaguar"),
    LAND_HOVER("Land Hover"),
    FERRARI("Ferrari");

    private final String brandName;
}

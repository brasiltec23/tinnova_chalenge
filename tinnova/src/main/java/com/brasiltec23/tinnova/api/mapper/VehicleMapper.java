package com.brasiltec23.tinnova.api.mapper;

import com.brasiltec23.tinnova.api.dto.BaseVehicleDTO;
import com.brasiltec23.tinnova.api.dto.VehicleDTO;
import com.brasiltec23.tinnova.api.dto.VehicleToUpdateDTO;
import com.brasiltec23.tinnova.api.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sold", expression = "java(false)")
    @Mapping(target = "createdIn", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedIn", ignore = true)
    Vehicle toEntity(BaseVehicleDTO vehicleRegistrationDTO);

    @Mapping(target = "baseVehicleDTO.name", source = "name")
    @Mapping(target = "baseVehicleDTO.brand", source = "brand")
    @Mapping(target = "baseVehicleDTO.yearOfManufacture", source = "yearOfManufacture")
    @Mapping(target = "baseVehicleDTO.description", source = "description")
    @Mapping(target = "baseVehicleDTO.color", source = "color")
    VehicleDTO toDto(Vehicle vehicle);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "baseVehicleDTO.name")
    @Mapping(target = "brand", source = "baseVehicleDTO.brand")
    @Mapping(target = "yearOfManufacture", source = "baseVehicleDTO.yearOfManufacture")
    @Mapping(target = "description", source = "baseVehicleDTO.description")
    @Mapping(target = "color", source = "baseVehicleDTO.color")
    @Mapping(target = "sold", source = "sold")
    @Mapping(target = "createdIn", ignore = true)
    @Mapping(target = "updatedIn", expression = "java(java.time.LocalDateTime.now())")
    Vehicle toEntity(VehicleToUpdateDTO dto);
}

package com.brasiltec23.tinnova.api.service;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import com.brasiltec23.tinnova.api.filter.VehicleFilter;
import com.brasiltec23.tinnova.api.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Long save(Vehicle vehicle);

    List<Vehicle> getByFilters(VehicleFilter vehicleFilter);

    Vehicle getById(Long id);

    Vehicle update(Vehicle vehicle);

    Vehicle partialUpdate(Vehicle vehicle);

    void deleteById(Long id);

    Long getCountByDecade(Integer decade);

    Long getUnsoldCount();

    Long getCountByBrand(BrandEnum brand);

    List<Vehicle> getLastIncludedInTheWeek();
}

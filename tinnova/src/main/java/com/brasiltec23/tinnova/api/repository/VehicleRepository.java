package com.brasiltec23.tinnova.api.repository;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import com.brasiltec23.tinnova.api.filter.VehicleFilter;
import com.brasiltec23.tinnova.api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Long countById(Long id);

    Long countBySoldFalse();

    Long countByYearOfManufactureBetween(Integer start, Integer end);

    Long countByBrand(BrandEnum brand);

    Optional<List<Vehicle>> findByCreatedInBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT vehicle FROM Vehicle vehicle
                WHERE (:#{#vehicleFilter.brand} IS NULL OR vehicle.brand =:#{#vehicleFilter.brand})
                AND (:#{#vehicleFilter.yearOfManufacture} IS NULL
                    OR vehicle.yearOfManufacture =:#{#vehicleFilter.yearOfManufacture})
                AND (:#{#vehicleFilter.color} IS NULL OR vehicle.color =:#{#vehicleFilter.color})
            """)
    Optional<List<Vehicle>> findByOptionalFilters(VehicleFilter vehicleFilter);
}

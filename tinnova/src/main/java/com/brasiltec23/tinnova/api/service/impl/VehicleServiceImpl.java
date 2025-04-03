package com.brasiltec23.tinnova.api.service.impl;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import com.brasiltec23.tinnova.api.filter.VehicleFilter;
import com.brasiltec23.tinnova.api.model.Vehicle;
import com.brasiltec23.tinnova.api.repository.VehicleRepository;
import com.brasiltec23.tinnova.api.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final MessageSource messageSource;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, MessageSource messageSource) {

        this.vehicleRepository = vehicleRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Long save(Vehicle vehicle) {
        log.info("VehicleServiceImpl.save {}", vehicle);
        return this.vehicleRepository.save(vehicle).getId();
    }

    @Override
    public List<Vehicle> getByFilters(VehicleFilter vehicleFilter) {
        log.info("VehicleServiceImpl.getVehicleByFilters {}", vehicleFilter);
        return this.vehicleRepository.findByOptionalFilters(vehicleFilter).orElse(new ArrayList<>());
    }

    @Override
    public Vehicle getById(Long id) {
        log.info("VehicleServiceImpl.getById {}", id);
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("vehicle.not.found", null, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        log.info("VehicleServiceImpl.update {}", vehicle);
        Vehicle existingVehicle = this.getById(vehicle.getId());
        BeanUtils.copyProperties(vehicle, existingVehicle, "id", "createdIn");
        return vehicleRepository.save(existingVehicle);
    }

    @Override
    @Transactional
    public Vehicle partialUpdate(Vehicle vehicle) {
        log.info("VehicleServiceImpl.partialUpdate {}", vehicle);
        Vehicle existingVehicle = this.getById(vehicle.getId());
        if (vehicle.getName() != null) {
            existingVehicle.setName(vehicle.getName());
        }
        if (vehicle.getBrand() != null) {
            existingVehicle.setBrand(vehicle.getBrand());
        }
        if (vehicle.getYearOfManufacture() != null) {
            existingVehicle.setYearOfManufacture(vehicle.getYearOfManufacture());
        }
        if (vehicle.getDescription() != null) {
            existingVehicle.setDescription(vehicle.getDescription());
        }
        if (vehicle.getColor() != null) {
            existingVehicle.setColor(vehicle.getColor());
        }
        if (vehicle.getSold() != null) {
            existingVehicle.setSold(vehicle.getSold());
        }
        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public void deleteById(Long id) {
        log.info("VehicleServiceImpl.deleteById {}", id);
        if(vehicleRepository.countById(id) == 0) {
            throw new EntityNotFoundException(messageSource.getMessage("vehicle.not.found", null,
                    LocaleContextHolder.getLocale()));
        }
        this.vehicleRepository.deleteById(id);
    }

    @Override
    public Long getCountByDecade(Integer decade) {
        log.info("VehicleServiceImpl.getCountByDecade {}", decade);
        return this.vehicleRepository.countByYearOfManufactureBetween(decade, decade + 10);
    }

    @Override
    public Long getUnsoldCount() {
        log.info("VehicleServiceImpl.getCountNotSold");
        return this.vehicleRepository.countBySoldFalse();
    }

    @Override
    public Long getCountByBrand(BrandEnum brand) {
        return this.vehicleRepository.countByBrand(brand);
    }

    @Override
    public List<Vehicle> getLastIncludedInTheWeek() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginningOfTheWeek = now.with(DayOfWeek.MONDAY)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        return this.vehicleRepository.findByCreatedInBetween(beginningOfTheWeek, now).orElse(new ArrayList<>());
    }
}

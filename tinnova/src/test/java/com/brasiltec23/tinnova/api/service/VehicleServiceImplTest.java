package com.brasiltec23.tinnova.api.service;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import com.brasiltec23.tinnova.api.filter.VehicleFilter;
import com.brasiltec23.tinnova.api.model.Vehicle;
import com.brasiltec23.tinnova.api.repository.VehicleRepository;
import com.brasiltec23.tinnova.api.service.impl.VehicleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;
    private final Clock fixedClock = Clock.fixed(Instant.parse("2023-01-05T12:00:00Z"), ZoneId.systemDefault());

    @BeforeEach
    void setUp() {
        vehicle = Vehicle.builder()
                .id(1L)
                .name("Gol")
                .brand(BrandEnum.VOLKSWAGEN)
                .yearOfManufacture(2020)
                .description("Carro popular")
                .color("Preto")
                .sold(false)
                .createdIn(LocalDateTime.now(fixedClock))
                .build();
    }

    @Test
    @DisplayName("save - Deve salvar um veículo com sucesso e retornar o ID")
    void save_ShouldSaveVehicleAndReturnId() {
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Long savedId = vehicleService.save(vehicle);

        Assertions.assertEquals(vehicle.getId(), savedId);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    @DisplayName("save - Deve lançar exceção quando ocorrer erro ao salvar")
    void save_ShouldThrowExceptionWhenSaveFails() {
        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(new RuntimeException("Database error"));

        Assertions.assertThrows(RuntimeException.class, () -> vehicleService.save(vehicle));
    }

    @Test
    @DisplayName("getByFilters - Deve retornar veículos filtrados")
    void getByFilters_ShouldReturnFilteredVehicles() {
        VehicleFilter filter = VehicleFilter.builder().build();
        when(vehicleRepository.findByOptionalFilters(filter)).thenReturn(Optional.of(List.of(vehicle)));

        List<Vehicle> result = vehicleService.getByFilters(filter);

        assertEquals(1, result.size());
        assertEquals(vehicle, result.getFirst());
    }

    @Test
    @DisplayName("getByFilters - Deve retornar lista vazia quando não encontrar veículos")
    void getByFilters_ShouldReturnEmptyListWhenNoVehiclesFound() {
        VehicleFilter filter = VehicleFilter.builder().build();
        when(vehicleRepository.findByOptionalFilters(filter)).thenReturn(Optional.empty());

        List<Vehicle> result = vehicleService.getByFilters(filter);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getById - Deve retornar veículo quando encontrado")
    void getById_ShouldReturnVehicleWhenFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getById(1L);

        assertEquals(vehicle, result);
    }

    @Test
    @DisplayName("getById - Deve lançar EntityNotFoundException quando veículo não encontrado")
    void getById_ShouldThrowEntityNotFoundExceptionWhenNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("vehicle.not.found"), isNull(), any()))
                .thenReturn("Veículo não encontrado");

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> vehicleService.getById(1L)
        );

        assertEquals("Veículo não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("update - Deve atualizar veículo existente")
    void update_ShouldUpdateExistingVehicle() {
        Vehicle updatedVehicle = Vehicle.builder()
                .id(1L)
                .name("Gol G5")
                .brand(BrandEnum.VOLKSWAGEN)
                .yearOfManufacture(2021)
                .description("Novo modelo")
                .color("Branco")
                .sold(true)
                .build();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Vehicle result = vehicleService.update(updatedVehicle);

        assertAll(
                () -> assertEquals(updatedVehicle.getName(), result.getName()),
                () -> assertEquals(updatedVehicle.getDescription(), result.getDescription()),
                () -> assertEquals(updatedVehicle.getColor(), result.getColor()),
                () -> assertEquals(updatedVehicle.getSold(), result.getSold()),
                () -> assertEquals(vehicle.getId(), result.getId()));
    }

    @Test
    @DisplayName("partialUpdate - Deve atualizar apenas o nome quando fornecido")
    void partialUpdate_ShouldUpdateOnlyNameWhenProvided() {
        Vehicle partialUpdate = new Vehicle();
        partialUpdate.setId(1L);
        partialUpdate.setName("Gol G5");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicle result = vehicleService.partialUpdate(partialUpdate);

        assertAll(
                () -> assertEquals("Gol G5", result.getName()),
                () -> assertEquals(vehicle.getBrand(), result.getBrand()),
                () -> assertEquals(vehicle.getColor(), result.getColor())
        );
    }

    @Test
    @DisplayName("partialUpdate - Deve atualizar apenas campos não nulos")
    void partialUpdate_ShouldUpdateOnlyNonNullFields() {
        Vehicle partialUpdate = new Vehicle();
        partialUpdate.setId(1L);
        partialUpdate.setColor("Vermelho");
        partialUpdate.setSold(true);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicle result = vehicleService.partialUpdate(partialUpdate);

        assertAll(
                () -> assertEquals("Vermelho", result.getColor()),
                () -> assertTrue(result.getSold()),
                () -> assertEquals(vehicle.getName(), result.getName()),
                () -> assertEquals(vehicle.getBrand(), result.getBrand())
        );
    }

    @Test
    @DisplayName("deleteById - Deve deletar veículo existente")
    void deleteById_ShouldDeleteExistingVehicle() {
        when(vehicleRepository.countById(1L)).thenReturn(1L);

        assertDoesNotThrow(() -> vehicleService.deleteById(1L));
        verify(vehicleRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById - Deve lançar exceção quando veículo não existe")
    void deleteById_ShouldThrowExceptionWhenVehicleNotFound() {
        when(vehicleRepository.countById(1L)).thenReturn(0L);
        when(messageSource.getMessage(eq("vehicle.not.found"), isNull(), any()))
                .thenReturn("Veículo não encontrado");

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> vehicleService.deleteById(1L)
        );

        assertEquals("Veículo não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("getCountByDecade - Deve retornar contagem de veículos por década")
    void getCountByDecade_ShouldReturnVehicleCountForDecade() {
        when(vehicleRepository.countByYearOfManufactureBetween(2020, 2030)).thenReturn(5L);

        Long count = vehicleService.getCountByDecade(2020);

        assertEquals(5L, count);
    }

    @Test
    @DisplayName("getUnsoldCount - Deve retornar contagem de veículos não vendidos")
    void getUnsoldCount_ShouldReturnCountOfUnsoldVehicles() {
        when(vehicleRepository.countBySoldFalse()).thenReturn(3L);

        Long count = vehicleService.getUnsoldCount();

        assertEquals(3L, count);
    }

    @Test
    @DisplayName("getCountByBrand - Deve retornar contagem de veículos por marca")
    void getCountByBrand_ShouldReturnVehicleCountByBrand() {
        when(vehicleRepository.countByBrand(BrandEnum.VOLKSWAGEN)).thenReturn(10L);

        Long count = vehicleService.getCountByBrand(BrandEnum.VOLKSWAGEN);

        assertEquals(10L, count);
    }
}
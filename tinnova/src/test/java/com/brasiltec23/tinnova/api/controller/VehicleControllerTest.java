package com.brasiltec23.tinnova.api.controller;

import com.brasiltec23.tinnova.api.dto.*;
import com.brasiltec23.tinnova.api.enums.BrandEnum;
import com.brasiltec23.tinnova.api.mapper.VehicleMapper;
import com.brasiltec23.tinnova.api.model.Vehicle;
import com.brasiltec23.tinnova.api.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VehicleService vehicleService;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleController vehicleController;

    private Vehicle vehicle;
    private BaseVehicleDTO baseVehicleDTO;
    private VehicleDTO vehicleDTO;
    private VehicleToUpdateDTO vehicleToUpdateDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();

        // Configuração do BaseVehicleDTO
        baseVehicleDTO = BaseVehicleDTO.builder()
                .name("Gol")
                .brand(BrandEnum.VOLKSWAGEN)
                .yearOfManufacture(2020)
                .description("Carro popular")
                .color("Preto")
                .build();

        // Configuração do VehicleDTO
        vehicleDTO = VehicleDTO.builder()
                .id(1L)
                .sold(false)
                .createdIn(LocalDateTime.now())
                .updatedIn(LocalDateTime.now())
                .baseVehicleDTO(baseVehicleDTO)
                .build();

        // Configuração do VehicleToUpdateDTO
        vehicleToUpdateDTO = VehicleToUpdateDTO.builder()
                .id(1L)
                .sold(true)
                .baseVehicleDTO(baseVehicleDTO)
                .build();

        // Configuração da entidade Vehicle
        vehicle = Vehicle.builder()
                .id(1L)
                .name("Gol")
                .brand(BrandEnum.VOLKSWAGEN)
                .yearOfManufacture(2020)
                .description("Carro popular")
                .color("Preto")
                .sold(false)
                .createdIn(LocalDateTime.now())
                .updatedIn(LocalDateTime.now())
                .build();
    }

    @Test
    void save_ShouldReturnCreatedStatus() throws Exception {
        when(vehicleMapper.toEntity(any(BaseVehicleDTO.class))).thenReturn(vehicle);
        when(vehicleService.save(any(Vehicle.class))).thenReturn(1L);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseVehicleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void getByFilters_ShouldReturnVehicles() throws Exception {
        when(vehicleService.getByFilters(any())).thenReturn(List.of(vehicle));
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(vehicleDTO);

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].sold").value(false))
                .andExpect(jsonPath("$[0].base.name").value("Gol")) // Acessando via 'base'
                .andExpect(jsonPath("$[0].base.brand").value("VOLKSWAGEN"))
                .andExpect(jsonPath("$[0].base.yearOfManufacture").value(2020))
                .andExpect(jsonPath("$[0].base.description").value("Carro popular"))
                .andExpect(jsonPath("$[0].base.color").value("Preto"));
    }

    @Test
    void getById_ShouldReturnVehicle() throws Exception {
        when(vehicleService.getById(1L)).thenReturn(vehicle);
        when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDTO);

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sold").value(false))
                .andExpect(jsonPath("$.base.name").value("Gol")) // Acessando via 'base'
                .andExpect(jsonPath("$.base.brand").value("VOLKSWAGEN"))
                .andExpect(jsonPath("$.base.yearOfManufacture").value(2020))
                .andExpect(jsonPath("$.base.description").value("Carro popular"))
                .andExpect(jsonPath("$.base.color").value("Preto"));
    }

    @Test
    void update_ShouldReturnUpdatedVehicle() throws Exception {
        when(vehicleMapper.toEntity(any(VehicleToUpdateDTO.class))).thenReturn(vehicle);
        when(vehicleService.update(any(Vehicle.class))).thenReturn(vehicle);
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(vehicleDTO);

        mockMvc.perform(put("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.base.name").value("Gol")); // Verificando campo aninhado
    }

    @Test
    void partialUpdate_ShouldReturnUpdatedVehicle() throws Exception {
        Vehicle updatedVehicle = Vehicle.builder()
                .id(1L)
                .sold(true)
                .name("Gol")
                .brand(BrandEnum.VOLKSWAGEN)
                .yearOfManufacture(2020)
                .description("Carro popular atualizado")
                .color("Preto")
                .build();

        VehicleDTO updatedVehicleDTO = VehicleDTO.builder()
                .id(1L)
                .sold(true)
                .createdIn(LocalDateTime.now())
                .updatedIn(LocalDateTime.now())
                .baseVehicleDTO(BaseVehicleDTO.builder()
                        .name("Gol")
                        .brand(BrandEnum.VOLKSWAGEN)
                        .yearOfManufacture(2020)
                        .description("Carro popular atualizado")
                        .color("Preto")
                        .build())
                .build();

        when(vehicleMapper.toEntity(any(VehicleToUpdateDTO.class))).thenReturn(updatedVehicle);
        when(vehicleService.partialUpdate(any(Vehicle.class))).thenReturn(updatedVehicle);
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(updatedVehicleDTO);

        mockMvc.perform(patch("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sold").value(true))
                .andExpect(jsonPath("$.base.description").value("Carro popular atualizado"));
    }

    @Test
    void delete_ShouldReturnCreatedStatus() throws Exception {
        doNothing().when(vehicleService).deleteById(1L);

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isCreated());
    }

    @Test
    void getCountByDecade_ShouldReturnCount() throws Exception {
        when(vehicleService.getCountByDecade(2020)).thenReturn(5L);

        mockMvc.perform(get("/api/vehicles/count/decade/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void getUnsoldCount_ShouldReturnCount() throws Exception {
        when(vehicleService.getUnsoldCount()).thenReturn(3L);

        mockMvc.perform(get("/api/vehicles/count/unsold"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    void getCountByBrand_ShouldReturnCount() throws Exception {
        when(vehicleService.getCountByBrand(BrandEnum.VOLKSWAGEN)).thenReturn(10L);

        mockMvc.perform(get("/api/vehicles/count/brand/VOLKSWAGEN"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void getLastWeekVehicles_ShouldReturnVehicles() throws Exception {
        when(vehicleService.getLastIncludedInTheWeek()).thenReturn(List.of(vehicle));
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(vehicleDTO);

        mockMvc.perform(get("/api/vehicles/last/week"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].base.name").value("Gol"));
    }

    @Test
    void getByFilters_ShouldReturnNoContentWhenEmpty() throws Exception {
        when(vehicleService.getByFilters(any())).thenReturn(List.of());

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getLastIncludedInTheWeek_ShouldReturnNoContentWhenEmpty() throws Exception {
        when(vehicleService.getLastIncludedInTheWeek()).thenReturn(List.of());

        mockMvc.perform(get("/api/vehicles/last/week"))
                .andExpect(status().isNoContent());
    }
}
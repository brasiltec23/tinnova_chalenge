package com.brasiltec23.tinnova.api.controller;

import com.brasiltec23.tinnova.api.dto.BaseVehicleDTO;
import com.brasiltec23.tinnova.api.dto.VehicleDTO;
import com.brasiltec23.tinnova.api.dto.VehicleToUpdateDTO;
import com.brasiltec23.tinnova.api.enums.BrandEnum;
import com.brasiltec23.tinnova.api.filter.VehicleFilter;
import com.brasiltec23.tinnova.api.mapper.VehicleMapper;
import com.brasiltec23.tinnova.api.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Veículos", description = "Operações relacionadas a venda de veículos")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @PostMapping
    @Operation(summary = "Incluir veículo",
            description = "Inclui o veículo e retorna o id criado")
    @ApiResponse(responseCode = "201", description = "Veículo incluído com sucesso")
    @ApiResponse(responseCode = "400", description = "Marca não encontrada")
    public ResponseEntity<Long> save(@RequestBody BaseVehicleDTO baseVehicleDTO) {
        log.info("VehicleController.save {}", baseVehicleDTO);
        return new ResponseEntity<>(this.vehicleService.save(
                this.vehicleMapper.toEntity(baseVehicleDTO)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Filtrar veículos",
            description = "Retorna veículos baseado nos critérios de filtro fornecidos")
    @ApiResponse(responseCode = "200", description = "Veículos encontrados com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))))
    @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado com os critérios informados")
    @ApiResponse(responseCode = "400", description = "Marca não encontrada")
    public ResponseEntity<List<VehicleDTO>> getByFilters(
            @Parameter(description = "Filtros de busca") VehicleFilter filter) {
        log.info("VehicleController.getByFilters {}", filter);
        return vehicleService.getByFilters(filter)
                .stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.isEmpty()
                                ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok(list)
                ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Filtrar veículo por id ",
            description = "Retorna veículos baseado no id")
    @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))))
    @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    public ResponseEntity<VehicleDTO> getById(@PathVariable Long id) {
        log.info("VehicleController.getById {}", id);
        return ResponseEntity.ok(this.vehicleMapper.toDto(vehicleService.getById(id)));
    }

    @PutMapping
    @Operation(summary = "Alterar veículo",
            description = "Altera o veículo e retorna o id criado")
    @ApiResponse(responseCode = "201", description = "Veículo alterado com sucesso")
    @ApiResponse(responseCode = "400", description = "Marca não encontrada")
    @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    public ResponseEntity<VehicleDTO> udpate(@RequestBody VehicleToUpdateDTO vehicleToUpdateDTO) {
        log.info("VehicleController.udpate {}", vehicleToUpdateDTO);
        return ResponseEntity.ok()
                .body(vehicleMapper.toDto(vehicleService.update(vehicleMapper.toEntity(vehicleToUpdateDTO))));
    }

    @PatchMapping
    @Operation(summary = "Alterar veículo parcialmente",
            description = "Altera o veículo somente com as informações não nulas e retorna o id criado")
    @ApiResponse(responseCode = "201", description = "Veículo alterado com sucesso")
    @ApiResponse(responseCode = "400", description = "Marca não encontrada")
    @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    public ResponseEntity<VehicleDTO> partialUdpate(@RequestBody VehicleToUpdateDTO vehicleToUpdateDTO) {
        log.info("VehicleController.partialUdpate {}", vehicleToUpdateDTO);
        return ResponseEntity.ok()
                .body(vehicleMapper.toDto(vehicleService.partialUpdate(vehicleMapper.toEntity(vehicleToUpdateDTO))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veículo",
            description = "Deleta um veículo pelo id")
    @ApiResponse(responseCode = "201", description = "Veículo deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("VehicleController.delete {}", id);
        this.vehicleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/count/decade/{decade}")
    @Operation(summary = "Filtrar a quantidade de veículos por década",
            description = "Retorna a quantidade de veículos filtrados por década")
    @ApiResponse(responseCode = "200", description = "Contagem efetuada com sucesso")
    public ResponseEntity<Long> getCountByDecade(@PathVariable Integer decade) {
        log.info("VehicleController.getCountByDecade {}", decade);
        return ResponseEntity.ok().body(this.vehicleService.getCountByDecade(decade));
    }

    @GetMapping("/count/unsold")
    @Operation(summary = "Filtrar a quantidade de veículos não vendidos",
            description = "Retorna a quantidade de veículos filtrados por não vendidos")
    @ApiResponse(responseCode = "200", description = "Contagem efetuada com sucesso")
    public ResponseEntity<Long> getUnsoldCount() {
        log.info("VehicleController.getCountNotSold");
        return ResponseEntity.ok().body(this.vehicleService.getUnsoldCount());
    }

    @GetMapping("/count/brand/{brand}")
    @Operation(summary = "Filtrar a quantidade de veículos por marca",
            description = "Retorna a quantidade de veículos filtrados por marca")
    @ApiResponse(responseCode = "200", description = "Contagem efetuada com sucesso")
    @ApiResponse(responseCode = "400", description = "Marca não encontrada")
    public ResponseEntity<Long> getCountByBrand(@PathVariable BrandEnum brand) {
        log.info("VehicleController.getCountByBrand");
        return ResponseEntity.ok().body(this.vehicleService.getCountByBrand(brand));
    }

    @GetMapping("/last/week")
    @Operation(summary = "Filtrar veículos da última semana",
            description = "Retorna veículos baseado na última semana")
    @ApiResponse(responseCode = "200", description = "Veículos encontrados com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))))
    @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado com os critérios informados")
    public ResponseEntity<List<VehicleDTO>> getLastIncludedInTheWeek() {
        log.info("VehicleController.getLastIncludedInTheWeek");
        return vehicleService.getLastIncludedInTheWeek()
                .stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.isEmpty()
                                ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok(list)
                ));
    }
}
package com.brasiltec23.tinnova.api.model;

import com.brasiltec23.tinnova.api.enums.BrandEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BrandEnum brand;
    private Integer yearOfManufacture;
    private String description;
    private String color;
    private Boolean sold;
    private LocalDateTime createdIn;
    private LocalDateTime updatedIn;
}

package com.autospec.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação e atualização de veículos.
 * Implementa validações usando Bean Validation.
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDTO {

    @NotBlank(message = "Marca é obrigatória")
    private String brand;

    @NotBlank(message = "Modelo é obrigatório")
    private String model;

    @NotBlank(message = "Versão é obrigatória")
    private String version;

    @NotNull(message = "Ano do modelo é obrigatório")
    @Positive(message = "Ano do modelo deve ser positivo")
    private Integer yearModel;

    @NotNull(message = "Ano de fabricação é obrigatório")
    @Positive(message = "Ano de fabricação deve ser positivo")
    private Integer yearManufacture;

    // Especificações do Motor
    private String engine;
    private String horsepower;
    private String torque;
    private String fuelType;
    private String transmission;

    // Consumo e Performance
    private String fuelConsumptionCity;
    private String fuelConsumptionHighway;
    private String fuelConsumptionAverage;
    private String acceleration0To100;
    private String maxSpeed;

    // Dimensões e Capacidades
    private String length;
    private String width;
    private String height;
    private String wheelbase;
    private String groundClearance;
    private String cargoCapacity;
    private String fuelTankCapacity;

    // Tração e Suspensão
    private String traction;
    private String frontSuspension;
    private String rearSuspension;
    private String frontBrakes;
    private String rearBrakes;

    // Rodas e Pneus
    private String wheels;
    private String tires;

    // Segurança e Tecnologia
    private String airbags;
    private Boolean abs;
    private Boolean stabilityControl;
    private Boolean tractionControl;
    private Boolean cruiseControl;
    private Boolean parkingSensors;
    private Boolean rearCamera;

    // Conforto e Conveniência
    private String airConditioning;
    private String soundSystem;
    private String connectivity;

    // Metadados
    private BigDecimal price;
    private String observations;
}

// Made with Bob

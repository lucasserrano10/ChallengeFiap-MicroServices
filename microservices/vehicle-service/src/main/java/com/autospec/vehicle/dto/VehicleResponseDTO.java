package com.autospec.vehicle.dto;

import com.autospec.vehicle.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para respostas de consultas de veículos.
 * Encapsula os dados da entidade Vehicle para exposição via API.
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponseDTO {

    private Long id;
    private String brand;
    private String model;
    private String version;
    private Integer yearModel;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converte uma entidade Vehicle para VehicleResponseDTO.
     * 
     * @param vehicle Entidade a ser convertida
     * @return DTO com os dados do veículo
     */
    public static VehicleResponseDTO fromEntity(Vehicle vehicle) {
        return VehicleResponseDTO.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .version(vehicle.getVersion())
                .yearModel(vehicle.getYearModel())
                .yearManufacture(vehicle.getYearManufacture())
                .engine(vehicle.getEngine())
                .horsepower(vehicle.getHorsepower())
                .torque(vehicle.getTorque())
                .fuelType(vehicle.getFuelType())
                .transmission(vehicle.getTransmission())
                .fuelConsumptionCity(vehicle.getFuelConsumptionCity())
                .fuelConsumptionHighway(vehicle.getFuelConsumptionHighway())
                .fuelConsumptionAverage(vehicle.getFuelConsumptionAverage())
                .acceleration0To100(vehicle.getAcceleration0To100())
                .maxSpeed(vehicle.getMaxSpeed())
                .length(vehicle.getLength())
                .width(vehicle.getWidth())
                .height(vehicle.getHeight())
                .wheelbase(vehicle.getWheelbase())
                .groundClearance(vehicle.getGroundClearance())
                .cargoCapacity(vehicle.getCargoCapacity())
                .fuelTankCapacity(vehicle.getFuelTankCapacity())
                .traction(vehicle.getTraction())
                .frontSuspension(vehicle.getFrontSuspension())
                .rearSuspension(vehicle.getRearSuspension())
                .frontBrakes(vehicle.getFrontBrakes())
                .rearBrakes(vehicle.getRearBrakes())
                .wheels(vehicle.getWheels())
                .tires(vehicle.getTires())
                .airbags(vehicle.getAirbags())
                .abs(vehicle.getAbs())
                .stabilityControl(vehicle.getStabilityControl())
                .tractionControl(vehicle.getTractionControl())
                .cruiseControl(vehicle.getCruiseControl())
                .parkingSensors(vehicle.getParkingSensors())
                .rearCamera(vehicle.getRearCamera())
                .airConditioning(vehicle.getAirConditioning())
                .soundSystem(vehicle.getSoundSystem())
                .connectivity(vehicle.getConnectivity())
                .price(vehicle.getPrice())
                .observations(vehicle.getObservations())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}

// Made with Bob

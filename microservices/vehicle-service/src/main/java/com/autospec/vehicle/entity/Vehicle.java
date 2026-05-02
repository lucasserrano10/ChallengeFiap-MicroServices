package com.autospec.vehicle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um veículo com suas especificações técnicas completas.
 * Implementa o padrão de domínio rico com validações e encapsulamento.
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
@Entity
@Table(name = "vehicles", indexes = {
    @Index(name = "idx_brand", columnList = "brand"),
    @Index(name = "idx_model", columnList = "model"),
    @Index(name = "idx_brand_model", columnList = "brand, model"),
    @Index(name = "idx_year", columnList = "year_model")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 100)
    private String version;

    @Column(name = "year_model", nullable = false)
    private Integer yearModel;

    @Column(name = "year_manufacture", nullable = false)
    private Integer yearManufacture;

    // ========== Especificações do Motor ==========
    
    @Column(length = 200)
    private String engine;

    @Column(length = 100)
    private String horsepower;

    @Column(length = 100)
    private String torque;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    @Column(length = 100)
    private String transmission;

    // ========== Consumo e Performance ==========
    
    @Column(name = "fuel_consumption_city", length = 50)
    private String fuelConsumptionCity;

    @Column(name = "fuel_consumption_highway", length = 50)
    private String fuelConsumptionHighway;

    @Column(name = "fuel_consumption_average", length = 50)
    private String fuelConsumptionAverage;

    @Column(name = "acceleration_0_100", length = 50)
    private String acceleration0To100;

    @Column(name = "max_speed", length = 50)
    private String maxSpeed;

    // ========== Dimensões e Capacidades ==========
    
    @Column(length = 50)
    private String length;

    @Column(length = 50)
    private String width;

    @Column(length = 50)
    private String height;

    @Column(length = 50)
    private String wheelbase;

    @Column(name = "ground_clearance", length = 50)
    private String groundClearance;

    @Column(name = "cargo_capacity", length = 50)
    private String cargoCapacity;

    @Column(name = "fuel_tank_capacity", length = 50)
    private String fuelTankCapacity;

    // ========== Tração e Suspensão ==========
    
    @Column(length = 100)
    private String traction;

    @Column(name = "front_suspension", length = 200)
    private String frontSuspension;

    @Column(name = "rear_suspension", length = 200)
    private String rearSuspension;

    @Column(name = "front_brakes", length = 100)
    private String frontBrakes;

    @Column(name = "rear_brakes", length = 100)
    private String rearBrakes;

    // ========== Rodas e Pneus ==========
    
    @Column(length = 100)
    private String wheels;

    @Column(length = 100)
    private String tires;

    // ========== Segurança e Tecnologia ==========
    
    @Column(length = 100)
    private String airbags;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean abs;

    @Column(name = "stability_control", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean stabilityControl;

    @Column(name = "traction_control", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean tractionControl;

    @Column(name = "cruise_control", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean cruiseControl;

    @Column(name = "parking_sensors", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean parkingSensors;

    @Column(name = "rear_camera", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean rearCamera;

    // ========== Conforto e Conveniência ==========
    
    @Column(name = "air_conditioning", length = 100)
    private String airConditioning;

    @Column(name = "sound_system", length = 200)
    private String soundSystem;

    @Column(length = 200)
    private String connectivity;

    // ========== Metadados ==========
    
    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Retorna uma descrição completa do veículo.
     * 
     * @return String formatada com marca, modelo e versão
     */
    public String getFullDescription() {
        return String.format("%s %s %s (%d)", brand, model, version, yearModel);
    }

    /**
     * Verifica se o veículo possui especificações completas de motor.
     * 
     * @return true se todas as especificações de motor estão preenchidas
     */
    public boolean hasCompleteEngineSpecs() {
        return engine != null && horsepower != null && torque != null;
    }

    /**
     * Verifica se o veículo possui dados de consumo.
     * 
     * @return true se possui pelo menos um dado de consumo
     */
    public boolean hasFuelConsumptionData() {
        return fuelConsumptionCity != null || 
               fuelConsumptionHighway != null || 
               fuelConsumptionAverage != null;
    }
}

// Made with Bob

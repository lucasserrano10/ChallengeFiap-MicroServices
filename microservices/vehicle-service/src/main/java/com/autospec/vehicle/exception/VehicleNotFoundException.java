package com.autospec.vehicle.exception;

/**
 * Exceção lançada quando um veículo não é encontrado no sistema.
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(Long id) {
        super(String.format("Veículo com ID %d não encontrado", id));
    }

    public VehicleNotFoundException(String brand, String model, String version) {
        super(String.format("Veículo %s %s %s não encontrado", brand, model, version));
    }
}

// Made with Bob

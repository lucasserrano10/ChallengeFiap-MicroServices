package com.autospec.vehicle.service;

import com.autospec.vehicle.dto.VehicleRequestDTO;
import com.autospec.vehicle.dto.VehicleResponseDTO;
import com.autospec.vehicle.entity.Vehicle;
import com.autospec.vehicle.exception.VehicleNotFoundException;
import com.autospec.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada a veículos.
 * Implementa operações CRUD e consultas especializadas.
 * Segue os princípios de SOA (Service-Oriented Architecture).
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    /**
     * Busca todos os veículos cadastrados.
     * 
     * @return Lista de DTOs com todos os veículos
     */
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findAll() {
        log.info("Buscando todos os veículos");
        return vehicleRepository.findAll().stream()
                .map(VehicleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um veículo por ID.
     * 
     * @param id ID do veículo
     * @return DTO com os dados do veículo
     * @throws VehicleNotFoundException se o veículo não for encontrado
     */
    @Transactional(readOnly = true)
    public VehicleResponseDTO findById(Long id) {
        log.info("Buscando veículo com ID: {}", id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        return VehicleResponseDTO.fromEntity(vehicle);
    }

    /**
     * Busca veículos por marca.
     * 
     * @param brand Marca do veículo
     * @return Lista de DTOs com os veículos da marca
     */
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findByBrand(String brand) {
        log.info("Buscando veículos da marca: {}", brand);
        return vehicleRepository.findByBrandIgnoreCase(brand).stream()
                .map(VehicleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca veículos por marca e modelo.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @return Lista de DTOs com os veículos
     */
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findByBrandAndModel(String brand, String model) {
        log.info("Buscando veículos: {} {}", brand, model);
        return vehicleRepository.findByBrandIgnoreCaseAndModelIgnoreCase(brand, model).stream()
                .map(VehicleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um veículo específico por marca, modelo e versão.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @param version Versão do veículo
     * @return DTO com os dados do veículo
     * @throws VehicleNotFoundException se o veículo não for encontrado
     */
    @Transactional(readOnly = true)
    public VehicleResponseDTO findByBrandModelVersion(String brand, String model, String version) {
        log.info("Buscando veículo: {} {} {}", brand, model, version);
        Vehicle vehicle = vehicleRepository
                .findByBrandIgnoreCaseAndModelIgnoreCaseAndVersionIgnoreCase(brand, model, version)
                .orElseThrow(() -> new VehicleNotFoundException(brand, model, version));
        return VehicleResponseDTO.fromEntity(vehicle);
    }

    /**
     * Cria um novo veículo.
     * 
     * @param dto DTO com os dados do veículo
     * @return DTO com os dados do veículo criado
     */
    public VehicleResponseDTO create(VehicleRequestDTO dto) {
        log.info("Criando novo veículo: {} {} {}", dto.getBrand(), dto.getModel(), dto.getVersion());
        
        // Verifica se já existe um veículo com a mesma combinação
        if (vehicleRepository.existsByBrandIgnoreCaseAndModelIgnoreCaseAndVersionIgnoreCase(
                dto.getBrand(), dto.getModel(), dto.getVersion())) {
            throw new IllegalArgumentException(
                    String.format("Veículo %s %s %s já existe no sistema", 
                            dto.getBrand(), dto.getModel(), dto.getVersion()));
        }
        
        Vehicle vehicle = convertToEntity(dto);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        log.info("Veículo criado com ID: {}", savedVehicle.getId());
        
        return VehicleResponseDTO.fromEntity(savedVehicle);
    }

    /**
     * Atualiza um veículo existente.
     * 
     * @param id ID do veículo
     * @param dto DTO com os novos dados
     * @return DTO com os dados atualizados
     * @throws VehicleNotFoundException se o veículo não for encontrado
     */
    public VehicleResponseDTO update(Long id, VehicleRequestDTO dto) {
        log.info("Atualizando veículo com ID: {}", id);
        
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        
        updateEntityFromDTO(existingVehicle, dto);
        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        log.info("Veículo atualizado: {}", id);
        
        return VehicleResponseDTO.fromEntity(updatedVehicle);
    }

    /**
     * Remove um veículo.
     * 
     * @param id ID do veículo
     * @throws VehicleNotFoundException se o veículo não for encontrado
     */
    public void delete(Long id) {
        log.info("Removendo veículo com ID: {}", id);
        
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException(id);
        }
        
        vehicleRepository.deleteById(id);
        log.info("Veículo removido: {}", id);
    }

    /**
     * Busca todas as marcas distintas.
     * 
     * @return Lista de marcas
     */
    @Transactional(readOnly = true)
    public List<String> findAllBrands() {
        log.info("Buscando todas as marcas");
        return vehicleRepository.findDistinctBrands();
    }

    /**
     * Busca todos os modelos de uma marca.
     * 
     * @param brand Marca do veículo
     * @return Lista de modelos
     */
    @Transactional(readOnly = true)
    public List<String> findModelsByBrand(String brand) {
        log.info("Buscando modelos da marca: {}", brand);
        return vehicleRepository.findDistinctModelsByBrand(brand);
    }

    /**
     * Converte um DTO em entidade.
     * 
     * @param dto DTO a ser convertido
     * @return Entidade Vehicle
     */
    private Vehicle convertToEntity(VehicleRequestDTO dto) {
        return Vehicle.builder()
                .brand(dto.getBrand())
                .model(dto.getModel())
                .version(dto.getVersion())
                .yearModel(dto.getYearModel())
                .yearManufacture(dto.getYearManufacture())
                .engine(dto.getEngine())
                .horsepower(dto.getHorsepower())
                .torque(dto.getTorque())
                .fuelType(dto.getFuelType())
                .transmission(dto.getTransmission())
                .fuelConsumptionCity(dto.getFuelConsumptionCity())
                .fuelConsumptionHighway(dto.getFuelConsumptionHighway())
                .fuelConsumptionAverage(dto.getFuelConsumptionAverage())
                .acceleration0To100(dto.getAcceleration0To100())
                .maxSpeed(dto.getMaxSpeed())
                .length(dto.getLength())
                .width(dto.getWidth())
                .height(dto.getHeight())
                .wheelbase(dto.getWheelbase())
                .groundClearance(dto.getGroundClearance())
                .cargoCapacity(dto.getCargoCapacity())
                .fuelTankCapacity(dto.getFuelTankCapacity())
                .traction(dto.getTraction())
                .frontSuspension(dto.getFrontSuspension())
                .rearSuspension(dto.getRearSuspension())
                .frontBrakes(dto.getFrontBrakes())
                .rearBrakes(dto.getRearBrakes())
                .wheels(dto.getWheels())
                .tires(dto.getTires())
                .airbags(dto.getAirbags())
                .abs(dto.getAbs())
                .stabilityControl(dto.getStabilityControl())
                .tractionControl(dto.getTractionControl())
                .cruiseControl(dto.getCruiseControl())
                .parkingSensors(dto.getParkingSensors())
                .rearCamera(dto.getRearCamera())
                .airConditioning(dto.getAirConditioning())
                .soundSystem(dto.getSoundSystem())
                .connectivity(dto.getConnectivity())
                .price(dto.getPrice())
                .observations(dto.getObservations())
                .build();
    }

    /**
     * Atualiza uma entidade com dados do DTO.
     * 
     * @param vehicle Entidade a ser atualizada
     * @param dto DTO com os novos dados
     */
    private void updateEntityFromDTO(Vehicle vehicle, VehicleRequestDTO dto) {
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setVersion(dto.getVersion());
        vehicle.setYearModel(dto.getYearModel());
        vehicle.setYearManufacture(dto.getYearManufacture());
        vehicle.setEngine(dto.getEngine());
        vehicle.setHorsepower(dto.getHorsepower());
        vehicle.setTorque(dto.getTorque());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setTransmission(dto.getTransmission());
        vehicle.setFuelConsumptionCity(dto.getFuelConsumptionCity());
        vehicle.setFuelConsumptionHighway(dto.getFuelConsumptionHighway());
        vehicle.setFuelConsumptionAverage(dto.getFuelConsumptionAverage());
        vehicle.setAcceleration0To100(dto.getAcceleration0To100());
        vehicle.setMaxSpeed(dto.getMaxSpeed());
        vehicle.setLength(dto.getLength());
        vehicle.setWidth(dto.getWidth());
        vehicle.setHeight(dto.getHeight());
        vehicle.setWheelbase(dto.getWheelbase());
        vehicle.setGroundClearance(dto.getGroundClearance());
        vehicle.setCargoCapacity(dto.getCargoCapacity());
        vehicle.setFuelTankCapacity(dto.getFuelTankCapacity());
        vehicle.setTraction(dto.getTraction());
        vehicle.setFrontSuspension(dto.getFrontSuspension());
        vehicle.setRearSuspension(dto.getRearSuspension());
        vehicle.setFrontBrakes(dto.getFrontBrakes());
        vehicle.setRearBrakes(dto.getRearBrakes());
        vehicle.setWheels(dto.getWheels());
        vehicle.setTires(dto.getTires());
        vehicle.setAirbags(dto.getAirbags());
        vehicle.setAbs(dto.getAbs());
        vehicle.setStabilityControl(dto.getStabilityControl());
        vehicle.setTractionControl(dto.getTractionControl());
        vehicle.setCruiseControl(dto.getCruiseControl());
        vehicle.setParkingSensors(dto.getParkingSensors());
        vehicle.setRearCamera(dto.getRearCamera());
        vehicle.setAirConditioning(dto.getAirConditioning());
        vehicle.setSoundSystem(dto.getSoundSystem());
        vehicle.setConnectivity(dto.getConnectivity());
        vehicle.setPrice(dto.getPrice());
        vehicle.setObservations(dto.getObservations());
    }
}

// Made with Bob

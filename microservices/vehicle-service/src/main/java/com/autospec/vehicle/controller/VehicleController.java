package com.autospec.vehicle.controller;

import com.autospec.vehicle.dto.*;
import com.autospec.vehicle.service.ComparisonService;
import com.autospec.vehicle.service.SpecificationService;
import com.autospec.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para gerenciamento de veículos e suas especificações.
 * Implementa endpoints RESTful seguindo as melhores práticas.
 * Parte da camada de apresentação da arquitetura SOA.
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Veículos", description = "APIs para gerenciamento de veículos e especificações técnicas")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService vehicleService;
    private final SpecificationService specificationService;
    private final ComparisonService comparisonService;

    // ========== CRUD ENDPOINTS ==========

    /**
     * Lista todos os veículos cadastrados.
     * 
     * @return Lista de veículos
     */
    @GetMapping
    @Operation(summary = "Listar todos os veículos", 
               description = "Retorna uma lista com todos os veículos cadastrados no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class)))
    })
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        log.info("GET /api/vehicles - Listando todos os veículos");
        List<VehicleResponseDTO> vehicles = vehicleService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Busca um veículo por ID.
     * 
     * @param id ID do veículo
     * @return Veículo encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID", 
               description = "Retorna os detalhes completos de um veículo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo encontrado",
                    content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<VehicleResponseDTO> getVehicleById(
            @Parameter(description = "ID do veículo") @PathVariable Long id) {
        log.info("GET /api/vehicles/{} - Buscando veículo", id);
        VehicleResponseDTO vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Cria um novo veículo.
     * 
     * @param request DTO com os dados do veículo
     * @return Veículo criado
     */
    @PostMapping
    @Operation(summary = "Criar novo veículo", 
               description = "Cadastra um novo veículo no sistema com suas especificações técnicas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Veículo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<VehicleResponseDTO> createVehicle(
            @Valid @RequestBody VehicleRequestDTO request) {
        log.info("POST /api/vehicles - Criando veículo: {} {} {}", 
                request.getBrand(), request.getModel(), request.getVersion());
        VehicleResponseDTO created = vehicleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza um veículo existente.
     * 
     * @param id ID do veículo
     * @param request DTO com os novos dados
     * @return Veículo atualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veículo", 
               description = "Atualiza os dados de um veículo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @Parameter(description = "ID do veículo") @PathVariable Long id,
            @Valid @RequestBody VehicleRequestDTO request) {
        log.info("PUT /api/vehicles/{} - Atualizando veículo", id);
        VehicleResponseDTO updated = vehicleService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Remove um veículo.
     * 
     * @param id ID do veículo
     * @return Resposta sem conteúdo
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover veículo", 
               description = "Remove um veículo do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<Void> deleteVehicle(
            @Parameter(description = "ID do veículo") @PathVariable Long id) {
        log.info("DELETE /api/vehicles/{} - Removendo veículo", id);
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ========== BUSINESS ENDPOINTS ==========

    /**
     * Busca especificações técnicas personalizadas de um veículo.
     * Endpoint principal do sistema para obter fichas técnicas padronizadas.
     * 
     * @param request DTO com marca, modelo, versão e lista de especificações desejadas
     * @return Map com as especificações solicitadas
     */
    @PostMapping("/specifications")
    @Operation(summary = "Obter especificações personalizadas", 
               description = "Retorna uma ficha técnica padronizada com apenas os campos solicitados. " +
                           "Campos não disponíveis retornam 'Não disponível'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especificações retornadas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Map<String, String>> getCustomSpecifications(
            @Valid @RequestBody SpecificationRequestDTO request) {
        log.info("POST /api/vehicles/specifications - Buscando specs: {} {} {}", 
                request.getBrand(), request.getModel(), request.getVersion());
        Map<String, String> specifications = specificationService.getCustomSpecifications(request);
        return ResponseEntity.ok(specifications);
    }

    /**
     * Busca todas as especificações de um veículo.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @param version Versão do veículo
     * @return Map com todas as especificações
     */
    @GetMapping("/specifications")
    @Operation(summary = "Obter todas as especificações", 
               description = "Retorna todas as especificações disponíveis de um veículo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especificações retornadas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<Map<String, String>> getAllSpecifications(
            @Parameter(description = "Marca do veículo") @RequestParam String brand,
            @Parameter(description = "Modelo do veículo") @RequestParam String model,
            @Parameter(description = "Versão do veículo") @RequestParam String version) {
        log.info("GET /api/vehicles/specifications?brand={}&model={}&version={}", brand, model, version);
        Map<String, String> specifications = specificationService.getAllSpecifications(brand, model, version);
        return ResponseEntity.ok(specifications);
    }

    /**
     * Compara múltiplos veículos.
     * 
     * @param request DTO com IDs dos veículos e campos opcionais para comparar
     * @return Map estruturado com a comparação
     */
    @PostMapping("/compare")
    @Operation(summary = "Comparar veículos", 
               description = "Compara múltiplos veículos em campos específicos ou todos os campos principais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comparação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Um ou mais veículos não encontrados"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Map<String, Object>> compareVehicles(
            @Valid @RequestBody CompareRequestDTO request) {
        log.info("POST /api/vehicles/compare - Comparando {} veículos", request.getVehicleIds().size());
        Map<String, Object> comparison = comparisonService.compareVehicles(request);
        return ResponseEntity.ok(comparison);
    }

    /**
     * Compara todas as versões de um modelo.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @return Map com a comparação
     */
    @GetMapping("/compare/{brand}/{model}")
    @Operation(summary = "Comparar versões de um modelo", 
               description = "Compara todas as versões disponíveis de um modelo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comparação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum veículo encontrado")
    })
    public ResponseEntity<Map<String, Object>> compareByBrandAndModel(
            @Parameter(description = "Marca do veículo") @PathVariable String brand,
            @Parameter(description = "Modelo do veículo") @PathVariable String model) {
        log.info("GET /api/vehicles/compare/{}/{} - Comparando versões", brand, model);
        Map<String, Object> comparison = comparisonService.compareByBrandAndModel(brand, model);
        return ResponseEntity.ok(comparison);
    }

    // ========== QUERY ENDPOINTS ==========

    /**
     * Busca veículos por marca.
     * 
     * @param brand Marca do veículo
     * @return Lista de veículos
     */
    @GetMapping("/brand/{brand}")
    @Operation(summary = "Buscar veículos por marca", 
               description = "Retorna todos os veículos de uma marca específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<VehicleResponseDTO>> getVehiclesByBrand(
            @Parameter(description = "Marca do veículo") @PathVariable String brand) {
        log.info("GET /api/vehicles/brand/{} - Buscando veículos", brand);
        List<VehicleResponseDTO> vehicles = vehicleService.findByBrand(brand);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Busca veículos por marca e modelo.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @return Lista de veículos
     */
    @GetMapping("/brand/{brand}/model/{model}")
    @Operation(summary = "Buscar veículos por marca e modelo", 
               description = "Retorna todos os veículos de uma marca e modelo específicos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<VehicleResponseDTO>> getVehiclesByBrandAndModel(
            @Parameter(description = "Marca do veículo") @PathVariable String brand,
            @Parameter(description = "Modelo do veículo") @PathVariable String model) {
        log.info("GET /api/vehicles/brand/{}/model/{} - Buscando veículos", brand, model);
        List<VehicleResponseDTO> vehicles = vehicleService.findByBrandAndModel(brand, model);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Lista todas as marcas disponíveis.
     * 
     * @return Lista de marcas
     */
    @GetMapping("/brands")
    @Operation(summary = "Listar marcas", 
               description = "Retorna todas as marcas de veículos cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<String>> getAllBrands() {
        log.info("GET /api/vehicles/brands - Listando marcas");
        List<String> brands = vehicleService.findAllBrands();
        return ResponseEntity.ok(brands);
    }

    /**
     * Lista todos os modelos de uma marca.
     * 
     * @param brand Marca do veículo
     * @return Lista de modelos
     */
    @GetMapping("/brands/{brand}/models")
    @Operation(summary = "Listar modelos de uma marca", 
               description = "Retorna todos os modelos disponíveis de uma marca específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<String>> getModelsByBrand(
            @Parameter(description = "Marca do veículo") @PathVariable String brand) {
        log.info("GET /api/vehicles/brands/{}/models - Listando modelos", brand);
        List<String> models = vehicleService.findModelsByBrand(brand);
        return ResponseEntity.ok(models);
    }

    /**
     * Health check do controller.
     * 
     * @return Status da API
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica se a API está funcionando")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API funcionando corretamente")
    })
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "AutoSpec Nexus API",
                "version", "1.0.0"
        ));
    }
}

// Made with Bob

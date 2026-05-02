package com.autospec.vehicle.repository;

import com.autospec.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de persistência da entidade Vehicle.
 * Implementa o padrão Repository do Spring Data JPA.
 * 
 * @author AutoSpec Nexus Team
 * @version 1.0
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * Busca veículos por marca.
     * 
     * @param brand Marca do veículo
     * @return Lista de veículos da marca especificada
     */
    List<Vehicle> findByBrandIgnoreCase(String brand);

    /**
     * Busca veículos por modelo.
     * 
     * @param model Modelo do veículo
     * @return Lista de veículos do modelo especificado
     */
    List<Vehicle> findByModelIgnoreCase(String model);

    /**
     * Busca veículos por marca e modelo.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @return Lista de veículos da marca e modelo especificados
     */
    List<Vehicle> findByBrandIgnoreCaseAndModelIgnoreCase(String brand, String model);

    /**
     * Busca um veículo específico por marca, modelo e versão.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @param version Versão do veículo
     * @return Optional contendo o veículo se encontrado
     */
    Optional<Vehicle> findByBrandIgnoreCaseAndModelIgnoreCaseAndVersionIgnoreCase(
            String brand, String model, String version);

    /**
     * Busca veículos por ano do modelo.
     * 
     * @param yearModel Ano do modelo
     * @return Lista de veículos do ano especificado
     */
    List<Vehicle> findByYearModel(Integer yearModel);

    /**
     * Busca veículos por tipo de combustível.
     * 
     * @param fuelType Tipo de combustível
     * @return Lista de veículos com o tipo de combustível especificado
     */
    List<Vehicle> findByFuelTypeIgnoreCase(String fuelType);

    /**
     * Busca veículos por tipo de tração.
     * 
     * @param traction Tipo de tração
     * @return Lista de veículos com o tipo de tração especificado
     */
    List<Vehicle> findByTractionIgnoreCase(String traction);

    /**
     * Busca veículos dentro de uma faixa de preço.
     * 
     * @param minPrice Preço mínimo
     * @param maxPrice Preço máximo
     * @return Lista de veículos na faixa de preço especificada
     */
    @Query("SELECT v FROM Vehicle v WHERE v.price BETWEEN :minPrice AND :maxPrice")
    List<Vehicle> findByPriceRange(@Param("minPrice") Double minPrice, 
                                   @Param("maxPrice") Double maxPrice);

    /**
     * Verifica se existe um veículo com a combinação marca, modelo e versão.
     * 
     * @param brand Marca do veículo
     * @param model Modelo do veículo
     * @param version Versão do veículo
     * @return true se existe, false caso contrário
     */
    boolean existsByBrandIgnoreCaseAndModelIgnoreCaseAndVersionIgnoreCase(
            String brand, String model, String version);

    /**
     * Busca todas as marcas distintas cadastradas.
     * 
     * @return Lista de marcas únicas
     */
    @Query("SELECT DISTINCT v.brand FROM Vehicle v ORDER BY v.brand")
    List<String> findDistinctBrands();

    /**
     * Busca todos os modelos distintos de uma marca.
     * 
     * @param brand Marca do veículo
     * @return Lista de modelos únicos da marca
     */
    @Query("SELECT DISTINCT v.model FROM Vehicle v WHERE LOWER(v.brand) = LOWER(:brand) ORDER BY v.model")
    List<String> findDistinctModelsByBrand(@Param("brand") String brand);
}

// Made with Bob

package com.autospec.vehicle;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Vehicle Service - Microserviço de Gerenciamento de Veículos
 * Porta: 8081
 * Responsável por: CRUD de veículos e especificações técnicas
 */
@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
    info = @Info(
        title = "Vehicle Service API",
        version = "1.0.0",
        description = "Microserviço para gerenciamento de veículos e especificações técnicas",
        contact = @Contact(
            name = "AutoSpec Nexus Team",
            email = "contato@autospecnexus.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class VehicleServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(VehicleServiceApplication.class, args);
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🚗 Vehicle Service iniciado com sucesso!");
        System.out.println("🌐 API: http://localhost:8081/api/vehicles");
        System.out.println("📚 Swagger: http://localhost:8081/swagger-ui.html");
        System.out.println("📊 Registrado no Eureka Server");
        System.out.println("=".repeat(60) + "\n");
    }
}

// Made with Bob

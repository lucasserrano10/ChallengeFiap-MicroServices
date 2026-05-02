package com.autospec.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway - Ponto de entrada único para todos os microserviços
 * Porta: 8080
 * Roteamento automático via Eureka
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🚪 API Gateway iniciado com sucesso!");
        System.out.println("🌐 Endpoint: http://localhost:8080");
        System.out.println("📋 Rotas disponíveis:");
        System.out.println("   - /api/vehicles/** → vehicle-service");
        System.out.println("   - /api/specifications/** → specification-service");
        System.out.println("   - /api/compare/** → comparison-service");
        System.out.println("=".repeat(60) + "\n");
    }
}

// Made with Bob

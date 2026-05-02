package com.autospec.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server - Service Discovery
 * Porta: 8761
 * Dashboard: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 Eureka Server iniciado com sucesso!");
        System.out.println("📊 Dashboard: http://localhost:8761");
        System.out.println("=".repeat(60) + "\n");
    }
}

// Made with Bob

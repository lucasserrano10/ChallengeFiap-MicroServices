# 🏗️ Arquitetura AutoSpec Nexus - Diagrama Completo

## 📊 Visão Geral da Arquitetura

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                            CAMADA DE CLIENTE                                │
│                                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Browser    │  │   Postman    │  │     cURL     │  │  Mobile App  │  │
│  │              │  │              │  │              │  │              │  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  │
│         │                 │                 │                 │           │
│         └─────────────────┴─────────────────┴─────────────────┘           │
│                                    │                                       │
└────────────────────────────────────┼───────────────────────────────────────┘
                                     │
                                     │ HTTP/REST
                                     │ JSON
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                          CAMADA DE GATEWAY                                  │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                        API GATEWAY                                    │ │
│  │                     Spring Cloud Gateway                              │ │
│  │                        Porta: 8080                                    │ │
│  │                                                                       │ │
│  │  ┌─────────────────────────────────────────────────────────────────┐ │ │
│  │  │  Roteamento:                                                    │ │ │
│  │  │  • /api/vehicles/** → vehicle-service                           │ │ │
│  │  │  • /api/specifications/** → specification-service (futuro)     │ │ │
│  │  │  • /api/compare/** → comparison-service (futuro)               │ │ │
│  │  └─────────────────────────────────────────────────────────────────┘ │ │
│  │                                                                       │ │
│  │  ┌─────────────────────────────────────────────────────────────────┐ │ │
│  │  │  Funcionalidades:                                               │ │ │
│  │  │  ✓ Load Balancing                                               │ │ │
│  │  │  ✓ Service Discovery Integration                                │ │ │
│  │  │  ✓ Circuit Breaker (preparado)                                  │ │ │
│  │  │  ✓ Rate Limiting (preparado)                                    │ │ │
│  │  └─────────────────────────────────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
│                                    │                                       │
└────────────────────────────────────┼───────────────────────────────────────┘
                                     │
                    ┌────────────────┼────────────────┐
                    │                │                │
                    ▼                ▼                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                      CAMADA DE SERVICE DISCOVERY                            │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                       EUREKA SERVER                                   │ │
│  │                  Netflix Service Discovery                            │ │
│  │                        Porta: 8761                                    │ │
│  │                                                                       │ │
│  │  ┌─────────────────────────────────────────────────────────────────┐ │ │
│  │  │  Serviços Registrados:                                          │ │ │
│  │  │  • api-gateway (1 instância)                                    │ │ │
│  │  │  • vehicle-service (1 instância)                                │ │ │
│  │  │  • specification-service (futuro)                               │ │ │
│  │  │  • comparison-service (futuro)                                  │ │ │
│  │  └─────────────────────────────────────────────────────────────────┘ │ │
│  │                                                                       │ │
│  │  ┌─────────────────────────────────────────────────────────────────┐ │ │
│  │  │  Funcionalidades:                                               │ │ │
│  │  │  ✓ Registro automático de serviços                              │ │ │
│  │  │  ✓ Health checks (heartbeat)                                    │ │ │
│  │  │  ✓ Dashboard web (http://localhost:8761)                        │ │ │
│  │  │  ✓ Service discovery dinâmico                                   │ │ │
│  │  └─────────────────────────────────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
                                     │
                    ┌────────────────┼────────────────┐
                    │                │                │
                    ▼                ▼                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                      CAMADA DE MICROSERVIÇOS                                │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      VEHICLE SERVICE                                │   │
│  │                        Porta: 8081                                  │   │
│  │                                                                     │   │
│  │  ┌────────────────────────────────────────────────────────────┐    │   │
│  │  │                  PRESENTATION LAYER                        │    │   │
│  │  │                                                            │    │   │
│  │  │  ┌──────────────────────────────────────────────────────┐ │    │   │
│  │  │  │         VehicleController (REST)                     │ │    │   │
│  │  │  │  • GET    /api/vehicles                              │ │    │   │
│  │  │  │  • GET    /api/vehicles/{id}                         │ │    │   │
│  │  │  │  • POST   /api/vehicles                              │ │    │   │
│  │  │  │  • PUT    /api/vehicles/{id}                         │ │    │   │
│  │  │  │  • DELETE /api/vehicles/{id}                         │ │    │   │
│  │  │  │  • GET    /api/vehicles/brand/{brand}                │ │    │   │
│  │  │  │  • GET    /api/vehicles/search                       │ │    │   │
│  │  │  └──────────────────────────────────────────────────────┘ │    │   │
│  │  │                                                            │    │   │
│  │  │  ┌──────────────────────────────────────────────────────┐ │    │   │
│  │  │  │         Swagger/OpenAPI Documentation                │ │    │   │
│  │  │  │  • Interactive API docs                              │ │    │   │
│  │  │  │  • Try it out functionality                          │ │    │   │
│  │  │  │  • Schema definitions                                │ │    │   │
│  │  │  └──────────────────────────────────────────────────────┘ │    │   │
│  │  └────────────────────────────────────────────────────────────┘    │   │
│  │                              │                                     │   │
│  │                              ▼                                     │   │
│  │  ┌────────────────────────────────────────────────────────────┐    │   │
│  │  │                    SERVICE LAYER                           │    │   │
│  │  │                                                            │    │   │
│  │  │  ┌──────────────────────────────────────────────────────┐ │    │   │
│  │  │  │         VehicleService                               │ │    │   │
│  │  │  │  • Business logic                                    │ │    │   │
│  │  │  │  • CRUD operations                                   │ │    │   │
│  │  │  │  • Data validation                                   │ │    │   │
│  │  │  │  • DTO ↔ Entity conversion                           │ │    │   │
│  │  │  └──────────────────────────────────────────────────────┘ │    │   │
│  │  │                                                            │    │   │
│  │  │  ┌──────────────────────────────────────────────────────┐ │    │   │
│  │  │  │         Exception Handling                           │ │    │   │
│  │  │  │  • GlobalExceptionHandler                            │ │    │   │
│  │  │  │  • VehicleNotFoundException                          │ │    │   │
│  │  │  │  • Custom error responses                            │ │    │   │
│  │  │  └──────────────────────────────────────────────────────┘ │    │   │
│  │  └────────────────────────────────────────────────────────────┘    │   │
│  │                              │                                     │   │
│  │                              ▼                                     │   │
│  │  ┌────────────────────────────────────────────────────────────┐    │   │
│  │  │                    DATA LAYER                              │    │   │
│  │  │                                                            │    │   │
│  │  │  ┌──────────────────────────────────────────────────────┐ │    │   │
│  │  │  │         VehicleRepository (JPA)                      │ │    │   │
│  │  │  │  • findAll()                                         │ │    │   │
│  │  │  │  • findById()                                        │ │    │   │
│  │  │  │  • save()                                            │ │    │   │
│  │  │  │  • delete()                                          │ │    │   │
│  │  │  │  • findByBrand()                                     │ │    │   │
│  │  │  │  • findByModel()                                     │ │    │   │
│  │  │  │  • Custom queries                                    │ │    │   │
│  │  │  └──────────────────────────────────────────────────────┘ │    │   │
│  │  │                                                            │    │   │
│  │  │  ┌──────────────────────────────────────────────────────┐ │    │   │
│  │  │  │         Vehicle Entity (JPA)                         │ │    │   │
│  │  │  │  • 50+ technical fields                              │ │    │   │
│  │  │  │  • Relationships                                     │ │    │   │
│  │  │  │  • Indexes                                           │ │    │   │
│  │  │  │  • Timestamps                                        │ │    │   │
│  │  │  └──────────────────────────────────────────────────────┘ │    │   │
│  │  └────────────────────────────────────────────────────────────┘    │   │
│  │                              │                                     │   │
│  └──────────────────────────────┼─────────────────────────────────────┘   │
│                                 │                                         │
│  ┌──────────────────────────────┼─────────────────────────────────────┐   │
│  │                              ▼                                     │   │
│  │  ┌────────────────────────────────────────────────────────────┐    │   │
│  │  │              SPECIFICATION SERVICE (Futuro)                │    │   │
│  │  │                     Porta: 8082                            │    │   │
│  │  │  • Custom specification extraction                         │    │   │
│  │  │  • Field mapping                                           │    │   │
│  │  │  • Data normalization                                      │    │   │
│  │  └────────────────────────────────────────────────────────────┘    │   │
│  │                                                                     │   │
│  │  ┌────────────────────────────────────────────────────────────┐    │   │
│  │  │              COMPARISON SERVICE (Futuro)                   │    │   │
│  │  │                     Porta: 8083                            │    │   │
│  │  │  • Vehicle comparison                                      │    │   │
│  │  │  • Side-by-side analysis                                   │    │   │
│  │  │  • Aggregation logic                                       │    │   │
│  │  └────────────────────────────────────────────────────────────┘    │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                 │                                         │
└─────────────────────────────────┼─────────────────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                        CAMADA DE PERSISTÊNCIA                               │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                          MySQL 8.0                                    │ │
│  │                        Porta: 3306                                    │ │
│  │                                                                       │ │
│  │  ┌─────────────────────────────────────────────────────────────────┐ │ │
│  │  │  Database: vehicle_db                                           │ │ │
│  │  │                                                                 │ │ │
│  │  │  ┌───────────────────────────────────────────────────────────┐ │ │ │
│  │  │  │  Table: vehicles                                          │ │ │ │
│  │  │  │  • id (PK, AUTO_INCREMENT)                                │ │ │ │
│  │  │  │  • brand, model, version                                  │ │ │ │
│  │  │  │  • year_model, year_manufacture                           │ │ │ │
│  │  │  │  • engine, horsepower, torque                             │ │ │ │
│  │  │  │  • fuel_type, transmission                                │ │ │ │
│  │  │  │  • fuel_consumption_*                                      │ │ │ │
│  │  │  │  • dimensions (length, width, height)                     │ │ │ │
│  │  │  │  • traction, suspension, brakes                           │ │ │ │
│  │  │  │  • safety features (airbags, abs, etc)                    │ │ │ │
│  │  │  │  • comfort features                                        │ │ │ │
│  │  │  │  • price, observations                                     │ │ │ │
│  │  │  │  • created_at, updated_at                                  │ │ │ │
│  │  │  │                                                            │ │ │ │
│  │  │  │  Indexes:                                                  │ │ │ │
│  │  │  │  • idx_brand (brand)                                       │ │ │ │
│  │  │  │  • idx_model (model)                                       │ │ │ │
│  │  │  │  • idx_brand_model (brand, model)                          │ │ │ │
│  │  │  │  • idx_year (year_model)                                   │ │ │ │
│  │  │  └───────────────────────────────────────────────────────────┘ │ │ │
│  │  │                                                                 │ │ │
│  │  │  ┌───────────────────────────────────────────────────────────┐ │ │ │
│  │  │  │  Flyway Migrations:                                       │ │ │ │
│  │  │  │  • V1__create_vehicle_table.sql                           │ │ │ │
│  │  │  │    - Schema creation                                      │ │ │ │
│  │  │  │    - Sample data (Ford Ranger Raptor)                     │ │ │ │
│  │  │  │    - Additional examples                                  │ │ │ │
│  │  │  └───────────────────────────────────────────────────────────┘ │ │ │
│  │  └─────────────────────────────────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔧 Tecnologias por Camada

### Camada de Gateway
- **Spring Cloud Gateway** - Roteamento e load balancing
- **Spring Cloud Netflix Eureka Client** - Service discovery integration
- **Spring Boot Actuator** - Health checks e métricas

### Camada de Service Discovery
- **Spring Cloud Netflix Eureka Server** - Service registry
- **Spring Boot** - Framework base

### Camada de Microserviços
- **Spring Boot 3.1.5** - Framework principal
- **Spring Web** - REST controllers
- **Spring Data JPA** - Persistência
- **Spring Validation** - Validação de dados
- **Lombok** - Redução de boilerplate
- **Springdoc OpenAPI** - Documentação Swagger

### Camada de Persistência
- **MySQL 8.0** - Banco de dados relacional
- **Flyway** - Migrations e versionamento
- **Hibernate** - ORM (via Spring Data JPA)

---

## 📡 Fluxo de Comunicação

### 1. Requisição do Cliente
```
Cliente → API Gateway (8080)
```

### 2. Service Discovery
```
API Gateway → Eureka Server (8761)
  ↓
Eureka retorna endereço do Vehicle Service
```

### 3. Roteamento
```
API Gateway → Vehicle Service (8081)
```

### 4. Processamento
```
Vehicle Service:
  Controller → Service → Repository → MySQL
```

### 5. Resposta
```
MySQL → Repository → Service → Controller → API Gateway → Cliente
```

---

## 🔐 Padrões Implementados

### Arquiteturais
- ✅ **Microservices Architecture** - Serviços independentes
- ✅ **Service-Oriented Architecture (SOA)** - Serviços reutilizáveis
- ✅ **API Gateway Pattern** - Ponto de entrada único
- ✅ **Service Discovery Pattern** - Registro dinâmico
- ✅ **Layered Architecture** - Separação de responsabilidades

### Design
- ✅ **Repository Pattern** - Abstração de persistência
- ✅ **DTO Pattern** - Transferência de dados
- ✅ **Builder Pattern** - Construção de objetos (Lombok)
- ✅ **Dependency Injection** - Inversão de controle

### Comunicação
- ✅ **RESTful API** - HTTP/JSON
- ✅ **Client-Side Load Balancing** - Via Eureka
- ✅ **Health Check Pattern** - Monitoramento

---

## 📊 Escalabilidade

### Horizontal Scaling
```
                    API Gateway
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
Vehicle Service 1  Vehicle Service 2  Vehicle Service 3
   (Port 8081)        (Port 8082)        (Port 8083)
        │                │                │
        └────────────────┼────────────────┘
                         │
                    MySQL (Shared)
```

### Vertical Scaling
- Aumentar recursos (CPU, RAM) de cada instância
- Otimizar queries do banco
- Cache (Redis - futuro)

---

## 🚀 Expansão Futura

### Novos Microserviços
```
┌─────────────────────────────────────────────┐
│  Specification Service (8082)               │
│  • Extração customizada de specs           │
│  • Normalização de dados                   │
│  • Comunicação via Feign Client             │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│  Comparison Service (8083)                  │
│  • Comparação de múltiplos veículos         │
│  • Agregação de dados                       │
│  • Análise side-by-side                     │
└─────────────────────────────────────────────┘
```

### Infraestrutura Adicional
```
┌─────────────────────────────────────────────┐
│  Config Server                              │
│  • Centralização de configurações           │
│  • Refresh dinâmico                         │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│  Circuit Breaker (Resilience4j)             │
│  • Tolerância a falhas                      │
│  • Fallback methods                         │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│  Distributed Tracing (Zipkin)               │
│  • Rastreamento de requisições              │
│  • Análise de performance                   │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│  Redis Cache                                │
│  • Cache distribuído                        │
│  • Melhoria de performance                  │
└─────────────────────────────────────────────┘
```

---

## 📈 Monitoramento

### Endpoints de Monitoramento
- **Eureka Dashboard:** http://localhost:8761
- **Swagger UI:** http://localhost:8081/swagger-ui.html
- **Actuator Health:** http://localhost:8081/actuator/health
- **Actuator Metrics:** http://localhost:8081/actuator/metrics

### Métricas Disponíveis
- Status dos serviços registrados
- Health checks automáticos
- Tempo de resposta das APIs
- Uso de recursos

---

**Desenvolvido para FIAP - Sprint Arquitetura Orientada a Serviços e Web Services**
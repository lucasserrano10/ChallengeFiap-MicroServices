# 🚀 AutoSpec Nexus - Arquitetura de Microserviços

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2022.0.4-blue.svg)](https://spring.io/projects/spring-cloud)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

Sistema distribuído de gerenciamento de especificações técnicas de veículos baseado em microserviços com Service Discovery e API Gateway.

---

## 📋 Visão Geral

**AutoSpec Nexus** é uma plataforma SOA que permite:
- ✅ Gerenciar veículos e especificações técnicas
- ✅ Consultar dados padronizados via API RESTful
- ✅ Arquitetura escalável e distribuída
- ✅ Service Discovery automático (Eureka)
- ✅ Roteamento inteligente (API Gateway)

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                        Cliente                               │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │    API Gateway       │
              │    Porta: 8080       │
              └──────────┬───────────┘
                         │
                         ▼
                ┌────────────────┐
                │ Vehicle Service│
                │  Porta: 8081   │
                └────────┬───────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
┌────────────────┐ ┌────────────┐ ┌────────────┐
│ Eureka Server  │ │   MySQL    │ │  Swagger   │
│  Porta: 8761   │ │vehicle_db  │ │    UI      │
└────────────────┘ └────────────┘ └────────────┘
```

---

## 📦 Microserviços

### 1. Eureka Server (Service Discovery)
- **Porta:** 8761
- **Função:** Registro e descoberta de serviços
- **Dashboard:** http://localhost:8761

### 2. API Gateway
- **Porta:** 8080
- **Função:** Ponto de entrada único, roteamento
- **Rotas:** `/api/vehicles/**` → vehicle-service

### 3. Vehicle Service
- **Porta:** 8081
- **Função:** CRUD de veículos e especificações
- **Banco:** MySQL (vehicle_db)
- **Swagger:** http://localhost:8081/swagger-ui.html

---

## 🚀 Quick Start

### Pré-requisitos

```bash
# Java 17
java -version

# Maven 3.8+
mvn -version

# MySQL 8.0
mysql --version
```

### 1. Configurar Banco de Dados

```bash
mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS vehicle_db;
EXIT;
```

### 2. Compilar Projeto

```bash
cd autospec-nexus/microservices
mvn clean install -DskipTests
```

### 3. Iniciar Microserviços (3 Terminais)

#### Terminal 1 - Eureka Server
```bash
cd microservices/eureka-server
mvn spring-boot:run
```
✅ Aguarde: `🎯 Eureka Server iniciado`  
✅ Acesse: http://localhost:8761

#### Terminal 2 - Vehicle Service
```bash
cd microservices/vehicle-service
mvn spring-boot:run
```
✅ Aguarde: `🚗 Vehicle Service iniciado`  
✅ Acesse: http://localhost:8081/swagger-ui.html

#### Terminal 3 - API Gateway
```bash
cd microservices/api-gateway
mvn spring-boot:run
```
✅ Aguarde: `🚪 API Gateway iniciado`

---

## 🧪 Testar

### Via API Gateway (Recomendado)

```bash
# Listar todos os veículos
curl http://localhost:8080/api/vehicles

# Buscar por ID
curl http://localhost:8080/api/vehicles/1

# Criar novo veículo
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Toyota",
    "model": "Hilux",
    "version": "SRX",
    "yearModel": 2024,
    "yearManufacture": 2024,
    "engine": "2.8 Turbo Diesel",
    "horsepower": "204 cv",
    "torque": "500 Nm"
  }'
```

### Ou use o script de testes:

```bash
cd microservices
./COMANDOS_TESTE.sh
```

---

## 📊 Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/vehicles` | Listar todos os veículos |
| GET | `/api/vehicles/{id}` | Buscar veículo por ID |
| POST | `/api/vehicles` | Criar novo veículo |
| PUT | `/api/vehicles/{id}` | Atualizar veículo |
| DELETE | `/api/vehicles/{id}` | Deletar veículo |
| GET | `/api/vehicles/brand/{brand}` | Buscar por marca |
| GET | `/api/vehicles/search?brand=X&model=Y` | Buscar por marca e modelo |

---

## 📚 Documentação

- **[QUICK_START.md](microservices/QUICK_START.md)** - Guia de execução rápida (5 minutos)
- **[README_MICROSERVICES.md](microservices/README_MICROSERVICES.md)** - Documentação completa

---

## 🛠️ Tecnologias

- **Java 17** - Linguagem de programação
- **Spring Boot 3.1.5** - Framework principal
- **Spring Cloud 2022.0.4** - Microserviços
- **Spring Cloud Netflix Eureka** - Service Discovery
- **Spring Cloud Gateway** - API Gateway
- **Spring Data JPA** - Persistência
- **MySQL 8.0** - Banco de dados
- **Flyway** - Migrations
- **Lombok** - Redução de boilerplate
- **Swagger/OpenAPI** - Documentação de API
- **Maven** - Gerenciamento de dependências

---

## 📁 Estrutura do Projeto

```
autospec-nexus/
├── README.md                          ← Este arquivo
└── microservices/                     ← Microserviços
    ├── pom.xml                        ← Parent POM
    ├── QUICK_START.md                 ← Guia rápido
    ├── README_MICROSERVICES.md        ← Documentação completa
    ├── ENTREGA_FINAL.md               ← Resumo acadêmico
    ├── COMANDOS_TESTE.sh              ← Script de testes
    ├── eureka-server/                 ← Service Discovery
    │   ├── pom.xml
    │   └── src/
    ├── api-gateway/                   ← API Gateway
    │   ├── pom.xml
    │   └── src/
    └── vehicle-service/               ← Microserviço de veículos
        ├── pom.xml
        └── src/
            ├── main/java/com/autospec/vehicle/
            │   ├── VehicleServiceApplication.java
            │   ├── controller/
            │   ├── service/
            │   ├── repository/
            │   ├── entity/
            │   ├── dto/
            │   └── exception/
            └── main/resources/
                ├── application.yml
                └── db/migration/
```

---

## 🎯 Critérios Acadêmicos Atendidos

### ✅ Arquitetura SOA (Service-Oriented Architecture)
- Serviços independentes e reutilizáveis
- Baixo acoplamento entre serviços
- Comunicação via HTTP/REST

### ✅ Microserviços
- Serviços pequenos e focados
- Deploy independente
- Escalabilidade horizontal

### ✅ Service Discovery (Eureka)
- Registro automático de serviços
- Descoberta dinâmica
- Health checks automáticos

### ✅ API Gateway
- Ponto de entrada único
- Roteamento inteligente
- Load balancing

### ✅ Web Services RESTful
- Métodos HTTP corretos
- Status codes apropriados
- JSON como formato de dados

### ✅ Banco de Dados
- MySQL relacional
- Migrations com Flyway
- JPA/Hibernate

### ✅ Documentação
- Swagger/OpenAPI
- README completo
- Guias de execução

---

## 🔧 Troubleshooting

### Problema: Porta já em uso

```bash
# Verificar processos
lsof -i :8761  # Eureka
lsof -i :8080  # Gateway
lsof -i :8081  # Vehicle Service

# Matar processo
kill -9 <PID>
```

### Problema: Erro de conexão com MySQL

```bash
# Verificar MySQL
mysql -u root -p

# Criar banco
CREATE DATABASE IF NOT EXISTS vehicle_db;
```

### Problema: Lombok não funciona no IntelliJ

1. Settings → Plugins → Instalar "Lombok"
2. Settings → Build → Compiler → Annotation Processors → ✅ Enable
3. File → Invalidate Caches / Restart

---

## 📞 URLs Importantes

| Serviço | URL | Descrição |
|---------|-----|-----------|
| Eureka Dashboard | http://localhost:8761 | Visualizar serviços registrados |
| Vehicle Service Swagger | http://localhost:8081/swagger-ui.html | Documentação interativa |
| API Gateway | http://localhost:8080/api/vehicles | Ponto de entrada único |
| Vehicle Service Direto | http://localhost:8081/api/vehicles | Acesso direto ao serviço |

---

## 🏆 Desenvolvido para

**FIAP - Sprint Arquitetura Orientada a Serviços e Web Services**

Projeto acadêmico demonstrando:
- Arquitetura SOA
- Microserviços
- Service Discovery
- API Gateway
- Web Services RESTful
- Boas práticas de desenvolvimento

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

---

**Made with ❤️ by AutoSpec Nexus Team**

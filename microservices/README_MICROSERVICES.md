# 🚀 AutoSpec Nexus - Arquitetura de Microserviços

## 📋 Visão Geral

Sistema distribuído baseado em microserviços com Service Discovery (Eureka) e API Gateway para roteamento inteligente.

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
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
┌────────────────┐ ┌────────────┐ ┌────────────┐
│ Vehicle Service│ │Specification│ │ Comparison │
│  Porta: 8081   │ │   Service   │ │  Service   │
│                │ │ Porta: 8082 │ │Porta: 8083 │
└────────┬───────┘ └──────┬─────┘ └─────┬──────┘
         │                │              │
         └────────────────┼──────────────┘
                          │
                          ▼
                 ┌────────────────┐
                 │ Eureka Server  │
                 │  Porta: 8761   │
                 └────────────────┘
```

## 📦 Microserviços

### 1. Eureka Server (Service Discovery)
- **Porta:** 8761
- **Função:** Registro e descoberta de serviços
- **Dashboard:** http://localhost:8761

### 2. API Gateway
- **Porta:** 8080
- **Função:** Ponto de entrada único, roteamento de requisições
- **Rotas:**
  - `/api/vehicles/**` → vehicle-service
  - `/api/specifications/**` → specification-service
  - `/api/compare/**` → comparison-service

### 3. Vehicle Service
- **Porta:** 8081
- **Função:** CRUD de veículos
- **Banco:** MySQL (vehicle_db)
- **Swagger:** http://localhost:8081/swagger-ui.html

### 4. Specification Service (Futuro)
- **Porta:** 8082
- **Função:** Extração de especificações customizadas

### 5. Comparison Service (Futuro)
- **Porta:** 8083
- **Função:** Comparação entre veículos

## 🚀 Como Executar

### Pré-requisitos

```bash
# Java 17
java -version

# Maven
mvn -version

# MySQL rodando na porta 3306
mysql -u root -p
```

### Passo 1: Configurar MySQL

```sql
CREATE DATABASE IF NOT EXISTS vehicle_db;
```

### Passo 2: Compilar Todos os Módulos

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices

# Compilar parent POM
mvn clean install -DskipTests
```

### Passo 3: Iniciar os Serviços (ORDEM IMPORTANTE!)

#### Terminal 1 - Eureka Server

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices/eureka-server
mvn spring-boot:run
```

**Aguarde até ver:**
```
🎯 Eureka Server iniciado com sucesso!
📊 Dashboard: http://localhost:8761
```

**Verifique:** Abra http://localhost:8761 no navegador

---

#### Terminal 2 - Vehicle Service

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices/vehicle-service
mvn spring-boot:run
```

**Aguarde até ver:**
```
🚗 Vehicle Service iniciado com sucesso!
🌐 API: http://localhost:8081/api/vehicles
📚 Swagger: http://localhost:8081/swagger-ui.html
📊 Registrado no Eureka Server
```

**Verifique:** 
- Swagger: http://localhost:8081/swagger-ui.html
- Eureka: http://localhost:8761 (deve mostrar VEHICLE-SERVICE registrado)

---

#### Terminal 3 - API Gateway

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices/api-gateway
mvn spring-boot:run
```

**Aguarde até ver:**
```
🚪 API Gateway iniciado com sucesso!
🌐 Endpoint: http://localhost:8080
```

**Verifique:** Eureka deve mostrar API-GATEWAY registrado

---

## 🧪 Testar os Microserviços

### 1. Testar Diretamente no Vehicle Service

```bash
# Listar todos os veículos
curl http://localhost:8081/api/vehicles

# Buscar veículo por ID
curl http://localhost:8081/api/vehicles/1

# Criar novo veículo
curl -X POST http://localhost:8081/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Toyota",
    "model": "Hilux",
    "version": "SRX 2.8",
    "yearModel": 2024,
    "yearManufacture": 2024,
    "engine": "2.8 Turbo Diesel",
    "horsepower": "204 cv",
    "torque": "500 Nm",
    "fuelType": "Diesel",
    "transmission": "Automática 6 marchas"
  }'
```

### 2. Testar Via API Gateway

```bash
# Listar veículos via Gateway
curl http://localhost:8080/api/vehicles

# Buscar por ID via Gateway
curl http://localhost:8080/api/vehicles/1

# Criar via Gateway
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Nissan",
    "model": "Frontier",
    "version": "Attack 2.3",
    "yearModel": 2024,
    "yearManufacture": 2024
  }'
```

### 3. Testar no Swagger

Acesse: http://localhost:8081/swagger-ui.html

1. Expanda `vehicle-controller`
2. Clique em `GET /api/vehicles`
3. Clique em "Try it out"
4. Clique em "Execute"

## 📊 Monitoramento

### Eureka Dashboard
- **URL:** http://localhost:8761
- **Visualizar:** Serviços registrados, status, instâncias

### Swagger UI
- **Vehicle Service:** http://localhost:8081/swagger-ui.html
- **Documentação interativa de todos os endpoints**

### Health Checks
```bash
# Vehicle Service
curl http://localhost:8081/actuator/health

# API Gateway
curl http://localhost:8080/actuator/health
```

## 🔧 Troubleshooting

### Problema: Eureka não mostra serviços registrados

**Solução:**
1. Aguarde 30 segundos após iniciar cada serviço
2. Verifique logs de cada serviço
3. Confirme que Eureka está em http://localhost:8761

### Problema: Erro de conexão com MySQL

**Solução:**
```bash
# Verificar se MySQL está rodando
mysql -u root -p

# Criar banco se não existir
CREATE DATABASE IF NOT EXISTS vehicle_db;

# Verificar credenciais em application.yml
# username: root
# password: root
```

### Problema: Porta já em uso

**Solução:**
```bash
# Verificar processos nas portas
lsof -i :8761  # Eureka
lsof -i :8080  # Gateway
lsof -i :8081  # Vehicle Service

# Matar processo se necessário
kill -9 <PID>
```

### Problema: Lombok não funciona

**Solução:**
1. IntelliJ: Settings → Plugins → Instalar "Lombok"
2. Settings → Build → Compiler → Annotation Processors → Enable
3. File → Invalidate Caches / Restart

## 📈 Endpoints Disponíveis

### Via API Gateway (http://localhost:8080)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/vehicles` | Listar todos os veículos |
| GET | `/api/vehicles/{id}` | Buscar veículo por ID |
| POST | `/api/vehicles` | Criar novo veículo |
| PUT | `/api/vehicles/{id}` | Atualizar veículo |
| DELETE | `/api/vehicles/{id}` | Deletar veículo |
| GET | `/api/vehicles/brand/{brand}` | Buscar por marca |
| GET | `/api/vehicles/search` | Buscar por marca e modelo |

### Direto no Vehicle Service (http://localhost:8081)

Mesmos endpoints acima, mas acessando diretamente o serviço.

## 🎯 Exemplo Completo de Uso

```bash
# 1. Criar um veículo
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Ford",
    "model": "Ranger",
    "version": "Storm 2.2",
    "yearModel": 2024,
    "yearManufacture": 2024,
    "engine": "2.2 Turbo Diesel",
    "horsepower": "160 cv",
    "torque": "385 Nm",
    "fuelType": "Diesel",
    "transmission": "Manual 6 marchas",
    "fuelConsumptionAverage": "11,2 km/l",
    "traction": "4x4",
    "price": 219990.00
  }'

# 2. Listar todos
curl http://localhost:8080/api/vehicles | jq

# 3. Buscar por marca
curl http://localhost:8080/api/vehicles/brand/Ford | jq

# 4. Buscar por ID (substitua {id} pelo ID retornado)
curl http://localhost:8080/api/vehicles/1 | jq

# 5. Atualizar
curl -X PUT http://localhost:8080/api/vehicles/1 \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Ford",
    "model": "Ranger",
    "version": "Storm 2.2 4x4",
    "yearModel": 2024,
    "yearManufacture": 2024,
    "price": 229990.00
  }'

# 6. Deletar
curl -X DELETE http://localhost:8080/api/vehicles/1
```

## 🎓 Conceitos Demonstrados

### ✅ Service-Oriented Architecture (SOA)
- Serviços independentes e reutilizáveis
- Comunicação via HTTP/REST
- Baixo acoplamento entre serviços

### ✅ Microserviços
- Serviços pequenos e focados
- Deploy independente
- Escalabilidade horizontal

### ✅ Service Discovery
- Registro automático de serviços
- Descoberta dinâmica via Eureka
- Health checks automáticos

### ✅ API Gateway Pattern
- Ponto de entrada único
- Roteamento inteligente
- Load balancing via Eureka

### ✅ RESTful APIs
- Métodos HTTP corretos (GET, POST, PUT, DELETE)
- Status codes apropriados
- JSON como formato de dados

### ✅ Spring Cloud Netflix
- Eureka Server/Client
- Spring Cloud Gateway
- Integração perfeita com Spring Boot

## 📚 Próximos Passos

1. **Adicionar Specification Service:**
   - Extrair especificações customizadas
   - Comunicação com Vehicle Service via Feign Client

2. **Adicionar Comparison Service:**
   - Comparar múltiplos veículos
   - Agregação de dados de múltiplos serviços

3. **Adicionar Config Server:**
   - Centralizar configurações
   - Refresh dinâmico de configs

4. **Adicionar Circuit Breaker (Resilience4j):**
   - Tolerância a falhas
   - Fallback methods

5. **Adicionar Distributed Tracing (Zipkin):**
   - Rastreamento de requisições
   - Análise de performance

## 🏆 Critérios Acadêmicos Atendidos

- ✅ Arquitetura SOA implementada
- ✅ Web Services RESTful
- ✅ Service Discovery (Eureka)
- ✅ API Gateway
- ✅ Microserviços independentes
- ✅ Banco de dados relacional (MySQL)
- ✅ Migrations (Flyway)
- ✅ Documentação OpenAPI/Swagger
- ✅ Boas práticas de código
- ✅ Separação de camadas (Controller, Service, Repository)
- ✅ DTOs para transferência de dados
- ✅ Exception handling global
- ✅ Validações com Bean Validation

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique os logs de cada serviço
2. Consulte o Eureka Dashboard
3. Teste endpoints no Swagger
4. Verifique a seção Troubleshooting

---

**Desenvolvido para FIAP - Sprint Arquitetura Orientada a Serviços e Web Services**

**Made with ❤️ by AutoSpec Nexus Team**
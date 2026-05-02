# ⚡ Quick Start - Microserviços AutoSpec Nexus

## 🎯 Objetivo

Ter os microserviços rodando em **5 minutos**.

## ✅ Pré-requisitos

```bash
# Verificar Java 17
java -version

# Verificar Maven
mvn -version

# Verificar MySQL rodando
mysql -u root -p
```

## 🚀 Execução Rápida

### 1️⃣ Preparar Banco de Dados

```bash
mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS vehicle_db;
EXIT;
```

### 2️⃣ Compilar Projeto

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices
mvn clean install -DskipTests
```

### 3️⃣ Iniciar Serviços (3 Terminais)

#### Terminal 1 - Eureka Server

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices/eureka-server
mvn spring-boot:run
```

✅ **Aguarde ver:** `🎯 Eureka Server iniciado`  
✅ **Acesse:** http://localhost:8761

---

#### Terminal 2 - Vehicle Service

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices/vehicle-service
mvn spring-boot:run
```

✅ **Aguarde ver:** `🚗 Vehicle Service iniciado`  
✅ **Acesse:** http://localhost:8081/swagger-ui.html

---

#### Terminal 3 - API Gateway

```bash
cd ~/Desktop/Challenge-fiap/autospec-nexus/microservices/api-gateway
mvn spring-boot:run
```

✅ **Aguarde ver:** `🚪 API Gateway iniciado`

---

## 🧪 Testar

### Teste 1: Listar Veículos

```bash
curl http://localhost:8080/api/vehicles | jq
```

**Resultado esperado:** Lista com Ford Ranger Raptor e outros veículos

### Teste 2: Buscar por ID

```bash
curl http://localhost:8080/api/vehicles/1 | jq
```

**Resultado esperado:** Dados completos da Ford Ranger Raptor

### Teste 3: Criar Veículo

```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Toyota",
    "model": "Hilux",
    "version": "SRX",
    "yearModel": 2024,
    "yearManufacture": 2024,
    "engine": "2.8 Turbo",
    "horsepower": "204 cv",
    "torque": "500 Nm"
  }' | jq
```

**Resultado esperado:** Veículo criado com ID gerado

### Teste 4: Swagger UI

Abra no navegador: http://localhost:8081/swagger-ui.html

1. Expanda `vehicle-controller`
2. Teste qualquer endpoint
3. Veja a documentação interativa

### Teste 5: Eureka Dashboard

Abra no navegador: http://localhost:8761

**Deve mostrar:**
- ✅ VEHICLE-SERVICE (1 instância)
- ✅ API-GATEWAY (1 instância)

## 📊 URLs Importantes

| Serviço | URL | Descrição |
|---------|-----|-----------|
| Eureka Dashboard | http://localhost:8761 | Visualizar serviços registrados |
| Vehicle Service Swagger | http://localhost:8081/swagger-ui.html | Documentação interativa |
| API Gateway | http://localhost:8080/api/vehicles | Ponto de entrada único |
| Vehicle Service Direto | http://localhost:8081/api/vehicles | Acesso direto ao serviço |

## ⚠️ Problemas Comuns

### Erro: "Address already in use"

```bash
# Verificar portas
lsof -i :8761
lsof -i :8080
lsof -i :8081

# Matar processo
kill -9 <PID>
```

### Erro: "Unable to connect to MySQL"

```bash
# Verificar MySQL
mysql -u root -p

# Criar banco
CREATE DATABASE IF NOT EXISTS vehicle_db;
```

### Erro: Lombok não funciona

1. IntelliJ → Settings → Plugins → Instalar "Lombok"
2. Settings → Build → Compiler → Annotation Processors → ✅ Enable
3. File → Invalidate Caches / Restart

## 🎉 Sucesso!

Se você conseguiu:
- ✅ Ver Eureka Dashboard com 2 serviços
- ✅ Acessar Swagger do Vehicle Service
- ✅ Fazer requisições via API Gateway
- ✅ Criar/Listar veículos

**Parabéns! Sua arquitetura de microserviços está funcionando!** 🚀

## 📚 Próximos Passos

1. Explore todos os endpoints no Swagger
2. Teste via Postman ou Insomnia
3. Veja os logs de cada serviço
4. Monitore o Eureka Dashboard

## 📖 Documentação Completa

Para mais detalhes, consulte:
- `README_MICROSERVICES.md` - Documentação completa
- `IMPLEMENTACAO_COMPLETA.md` - Detalhes técnicos

---

**Made with ❤️ for FIAP**
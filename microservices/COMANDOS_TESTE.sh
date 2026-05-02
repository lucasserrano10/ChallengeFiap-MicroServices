#!/bin/bash

# ========================================
# AutoSpec Nexus - Comandos de Teste
# ========================================

echo "🧪 Testando Microserviços AutoSpec Nexus"
echo "========================================"
echo ""

# Cores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para testar endpoint
test_endpoint() {
    local method=$1
    local url=$2
    local description=$3
    local data=$4
    
    echo -e "${BLUE}Testando:${NC} $description"
    echo -e "${YELLOW}$method $url${NC}"
    
    if [ -z "$data" ]; then
        curl -s -X $method "$url" | jq '.' 2>/dev/null || curl -s -X $method "$url"
    else
        curl -s -X $method "$url" \
            -H "Content-Type: application/json" \
            -d "$data" | jq '.' 2>/dev/null || curl -s -X $method "$url" -H "Content-Type: application/json" -d "$data"
    fi
    
    echo ""
    echo "---"
    echo ""
}

# Verificar se serviços estão rodando
echo "1️⃣ Verificando se os serviços estão rodando..."
echo ""

echo "Eureka Server (8761):"
curl -s http://localhost:8761/actuator/health 2>/dev/null && echo -e "${GREEN}✅ OK${NC}" || echo -e "${YELLOW}⚠️  Não está rodando${NC}"

echo "Vehicle Service (8081):"
curl -s http://localhost:8081/actuator/health 2>/dev/null && echo -e "${GREEN}✅ OK${NC}" || echo -e "${YELLOW}⚠️  Não está rodando${NC}"

echo "API Gateway (8080):"
curl -s http://localhost:8080/actuator/health 2>/dev/null && echo -e "${GREEN}✅ OK${NC}" || echo -e "${YELLOW}⚠️  Não está rodando${NC}"

echo ""
echo "========================================"
echo ""

# Testes via API Gateway
echo "2️⃣ Testando endpoints via API Gateway (porta 8080)"
echo ""

# GET - Listar todos os veículos
test_endpoint "GET" "http://localhost:8080/api/vehicles" "Listar todos os veículos"

# GET - Buscar por ID
test_endpoint "GET" "http://localhost:8080/api/vehicles/1" "Buscar Ford Ranger Raptor (ID 1)"

# GET - Buscar por marca
test_endpoint "GET" "http://localhost:8080/api/vehicles/brand/Ford" "Buscar veículos da marca Ford"

# POST - Criar novo veículo
NEW_VEHICLE='{
  "brand": "Toyota",
  "model": "Hilux",
  "version": "SRX 2.8",
  "yearModel": 2024,
  "yearManufacture": 2024,
  "engine": "2.8 Turbo Diesel",
  "horsepower": "204 cv",
  "torque": "500 Nm",
  "fuelType": "Diesel",
  "transmission": "Automática 6 marchas",
  "fuelConsumptionAverage": "10,5 km/l",
  "traction": "4x4",
  "price": 289990.00
}'

test_endpoint "POST" "http://localhost:8080/api/vehicles" "Criar novo veículo (Toyota Hilux)" "$NEW_VEHICLE"

echo ""
echo "========================================"
echo ""

# Testes direto no Vehicle Service
echo "3️⃣ Testando endpoints direto no Vehicle Service (porta 8081)"
echo ""

test_endpoint "GET" "http://localhost:8081/api/vehicles" "Listar todos os veículos (direto)"

test_endpoint "GET" "http://localhost:8081/api/vehicles/1" "Buscar por ID (direto)"

echo ""
echo "========================================"
echo ""

# Teste de busca com parâmetros
echo "4️⃣ Testando buscas com parâmetros"
echo ""

test_endpoint "GET" "http://localhost:8080/api/vehicles/search?brand=Ford&model=Ranger" "Buscar Ford Ranger"

test_endpoint "GET" "http://localhost:8080/api/vehicles/brand/Chevrolet" "Buscar veículos Chevrolet"

echo ""
echo "========================================"
echo ""

# URLs importantes
echo "5️⃣ URLs Importantes"
echo ""
echo -e "${BLUE}Eureka Dashboard:${NC} http://localhost:8761"
echo -e "${BLUE}Swagger UI:${NC} http://localhost:8081/swagger-ui.html"
echo -e "${BLUE}API Gateway:${NC} http://localhost:8080/api/vehicles"
echo -e "${BLUE}Vehicle Service:${NC} http://localhost:8081/api/vehicles"
echo ""

echo "========================================"
echo -e "${GREEN}✅ Testes concluídos!${NC}"
echo "========================================"

# Made with Bob

-- ===============================
-- AutoSpec Nexus - Database Schema
-- Migration V1: Create Vehicle Table
-- ===============================

CREATE TABLE IF NOT EXISTS vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(100) NOT NULL COMMENT 'Marca do veículo (ex: Ford, Chevrolet)',
    model VARCHAR(100) NOT NULL COMMENT 'Modelo do veículo (ex: Ranger, S10)',
    version VARCHAR(100) NOT NULL COMMENT 'Versão do veículo (ex: Raptor, XLT)',
    year_model INT NOT NULL COMMENT 'Ano do modelo',
    year_manufacture INT NOT NULL COMMENT 'Ano de fabricação',
    
    -- Especificações Técnicas do Motor
    engine VARCHAR(200) COMMENT 'Especificação do motor (ex: 3.0 V6 Bi-Turbo)',
    horsepower VARCHAR(100) COMMENT 'Potência (ex: 397 cv)',
    torque VARCHAR(100) COMMENT 'Torque (ex: 583 Nm)',
    fuel_type VARCHAR(50) COMMENT 'Tipo de combustível (ex: Diesel, Gasolina)',
    transmission VARCHAR(100) COMMENT 'Transmissão (ex: Automática 10 marchas)',
    
    -- Consumo e Performance
    fuel_consumption_city VARCHAR(50) COMMENT 'Consumo urbano (ex: 7,5 km/l)',
    fuel_consumption_highway VARCHAR(50) COMMENT 'Consumo rodoviário (ex: 9,2 km/l)',
    fuel_consumption_average VARCHAR(50) COMMENT 'Consumo médio (ex: 8,1 km/l)',
    acceleration_0_100 VARCHAR(50) COMMENT 'Aceleração 0-100 km/h (ex: 7,5s)',
    max_speed VARCHAR(50) COMMENT 'Velocidade máxima (ex: 180 km/h)',
    
    -- Dimensões e Capacidades
    length VARCHAR(50) COMMENT 'Comprimento (ex: 5370 mm)',
    width VARCHAR(50) COMMENT 'Largura (ex: 2180 mm)',
    height VARCHAR(50) COMMENT 'Altura (ex: 1884 mm)',
    wheelbase VARCHAR(50) COMMENT 'Entre-eixos (ex: 3220 mm)',
    ground_clearance VARCHAR(50) COMMENT 'Altura do solo (ex: 283 mm)',
    cargo_capacity VARCHAR(50) COMMENT 'Capacidade de carga (ex: 1000 kg)',
    fuel_tank_capacity VARCHAR(50) COMMENT 'Capacidade do tanque (ex: 80 litros)',
    
    -- Tração e Suspensão
    traction VARCHAR(100) COMMENT 'Tipo de tração (ex: 4x4, 4x2)',
    front_suspension VARCHAR(200) COMMENT 'Suspensão dianteira',
    rear_suspension VARCHAR(200) COMMENT 'Suspensão traseira',
    front_brakes VARCHAR(100) COMMENT 'Freios dianteiros',
    rear_brakes VARCHAR(100) COMMENT 'Freios traseiros',
    
    -- Rodas e Pneus
    wheels VARCHAR(100) COMMENT 'Rodas (ex: 17 polegadas)',
    tires VARCHAR(100) COMMENT 'Pneus (ex: 285/70 R17)',
    
    -- Segurança e Tecnologia
    airbags VARCHAR(100) COMMENT 'Airbags (ex: 6 airbags)',
    abs BOOLEAN DEFAULT FALSE COMMENT 'Sistema ABS',
    stability_control BOOLEAN DEFAULT FALSE COMMENT 'Controle de estabilidade',
    traction_control BOOLEAN DEFAULT FALSE COMMENT 'Controle de tração',
    cruise_control BOOLEAN DEFAULT FALSE COMMENT 'Piloto automático',
    parking_sensors BOOLEAN DEFAULT FALSE COMMENT 'Sensores de estacionamento',
    rear_camera BOOLEAN DEFAULT FALSE COMMENT 'Câmera de ré',
    
    -- Conforto e Conveniência
    air_conditioning VARCHAR(100) COMMENT 'Ar condicionado (ex: Automático dual zone)',
    sound_system VARCHAR(200) COMMENT 'Sistema de som',
    connectivity VARCHAR(200) COMMENT 'Conectividade (ex: Apple CarPlay, Android Auto)',
    
    -- Metadados
    price DECIMAL(12,2) COMMENT 'Preço sugerido',
    observations TEXT COMMENT 'Observações adicionais',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação do registro',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data de atualização do registro',
    
    -- Índices para otimização de consultas
    INDEX idx_brand (brand),
    INDEX idx_model (model),
    INDEX idx_brand_model (brand, model),
    INDEX idx_year (year_model)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de veículos com especificações técnicas completas';

-- ===============================
-- Inserir dados de exemplo: Ford Ranger Raptor
-- ===============================
INSERT INTO vehicles (
    brand, model, version, year_model, year_manufacture,
    engine, horsepower, torque, fuel_type, transmission,
    fuel_consumption_city, fuel_consumption_highway, fuel_consumption_average,
    acceleration_0_100, max_speed,
    length, width, height, wheelbase, ground_clearance,
    cargo_capacity, fuel_tank_capacity,
    traction, front_suspension, rear_suspension,
    front_brakes, rear_brakes,
    wheels, tires,
    airbags, abs, stability_control, traction_control,
    cruise_control, parking_sensors, rear_camera,
    air_conditioning, sound_system, connectivity,
    price, observations
) VALUES (
    'Ford', 'Ranger', 'Raptor', 2024, 2024,
    '3.0 V6 Bi-Turbo', '397 cv @ 5650 rpm', '583 Nm @ 3500 rpm', 'Diesel', 'Automática 10 marchas',
    '7,5 km/l', '9,2 km/l', '8,1 km/l',
    '7,5 segundos', '180 km/h',
    '5370 mm', '2180 mm', '1884 mm', '3220 mm', '283 mm',
    '1000 kg', '80 litros',
    '4x4 com reduzida', 'Independente com amortecedores FOX', 'Eixo rígido com amortecedores FOX',
    'Discos ventilados', 'Discos ventilados',
    '17 polegadas (liga leve)', 'BF Goodrich 285/70 R17',
    '6 airbags', TRUE, TRUE, TRUE,
    TRUE, TRUE, TRUE,
    'Automático dual zone', 'B&O Sound System com 10 alto-falantes', 'SYNC 4 com tela 12", Apple CarPlay, Android Auto',
    389990.00, 'Versão top de linha com suspensão off-road FOX Racing e modo Baja para alta performance'
);

-- ===============================
-- Inserir mais exemplos para demonstração
-- ===============================
INSERT INTO vehicles (
    brand, model, version, year_model, year_manufacture,
    engine, horsepower, torque, fuel_type, transmission,
    fuel_consumption_average, traction, price
) VALUES 
(
    'Ford', 'Ranger', 'XLT 3.2', 2024, 2024,
    '3.2 I5 Turbo Diesel', '200 cv', '470 Nm', 'Diesel', 'Automática 6 marchas',
    '9,8 km/l', '4x4', 259990.00
),
(
    'Ford', 'Ranger', 'Limited 2.0', 2024, 2024,
    '2.0 Bi-Turbo Diesel', '170 cv', '420 Nm', 'Diesel', 'Automática 10 marchas',
    '10,5 km/l', '4x2', 229990.00
),
(
    'Chevrolet', 'S10', 'High Country 2.8', 2024, 2024,
    '2.8 Turbo Diesel', '200 cv', '500 Nm', 'Diesel', 'Automática 6 marchas',
    '9,5 km/l', '4x4', 279990.00
);

-- Made with Bob

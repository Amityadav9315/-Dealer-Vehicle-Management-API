-- Dealer & Vehicle Management Database Schema
-- PostgreSQL Database

-- Create database (run this manually if needed)
-- CREATE DATABASE dealer_vehicle_db;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS dealers CASCADE;

-- Create dealers table
CREATE TABLE dealers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    subscription_type VARCHAR(20) NOT NULL CHECK (subscription_type IN ('BASIC', 'PREMIUM')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create vehicles table
CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    dealer_id BIGINT NOT NULL,
    model VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    status VARCHAR(20) NOT NULL CHECK (status IN ('AVAILABLE', 'SOLD')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_dealer FOREIGN KEY (dealer_id) REFERENCES dealers(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_vehicles_dealer_id ON vehicles(dealer_id);
CREATE INDEX idx_vehicles_status ON vehicles(status);
CREATE INDEX idx_dealers_subscription_type ON dealers(subscription_type);
CREATE INDEX idx_dealers_email ON dealers(email);

-- Insert sample data (optional)
INSERT INTO dealers (name, email, subscription_type) VALUES
('Premium Auto Dealer', 'premium@example.com', 'PREMIUM'),
('Basic Car Sales', 'basic@example.com', 'BASIC'),
('Elite Motors', 'elite@example.com', 'PREMIUM'),
('Standard Vehicles', 'standard@example.com', 'BASIC');

INSERT INTO vehicles (dealer_id, model, price, status) VALUES
(1, 'Toyota Camry', 25000.00, 'AVAILABLE'),
(1, 'Honda Accord', 27000.00, 'AVAILABLE'),
(1, 'BMW 3 Series', 45000.00, 'SOLD'),
(2, 'Ford Focus', 20000.00, 'AVAILABLE'),
(3, 'Mercedes-Benz C-Class', 50000.00, 'AVAILABLE'),
(3, 'Audi A4', 48000.00, 'AVAILABLE'),
(4, 'Nissan Altima', 22000.00, 'SOLD');


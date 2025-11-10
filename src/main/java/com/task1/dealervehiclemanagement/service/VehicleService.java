package com.task1.dealervehiclemanagement.service;

import com.task1.dealervehiclemanagement.dto.VehicleDTO;
import com.task1.dealervehiclemanagement.model.Dealer;
import com.task1.dealervehiclemanagement.model.SubscriptionType;
import com.task1.dealervehiclemanagement.model.Vehicle;
import com.task1.dealervehiclemanagement.repository.DealerRepository;
import com.task1.dealervehiclemanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private DealerRepository dealerRepository;
    
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return convertToDTO(vehicle);
    }
    
    public List<VehicleDTO> getVehiclesByDealerId(Long dealerId) {
        return vehicleRepository.findByDealerId(dealerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<VehicleDTO> getVehiclesByPremiumDealers() {
        return vehicleRepository.findByPremiumDealers(SubscriptionType.PREMIUM).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        Dealer dealer = dealerRepository.findById(vehicleDTO.getDealerId())
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + vehicleDTO.getDealerId()));
        
        Vehicle vehicle = convertToEntity(vehicleDTO, dealer);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(savedVehicle);
    }
    
    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        
        Dealer dealer = dealerRepository.findById(vehicleDTO.getDealerId())
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + vehicleDTO.getDealerId()));
        
        vehicle.setDealer(dealer);
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setPrice(vehicleDTO.getPrice());
        vehicle.setStatus(vehicleDTO.getStatus());
        
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(updatedVehicle);
    }
    
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }
    
    private VehicleDTO convertToDTO(Vehicle vehicle) {
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getDealerId(),
                vehicle.getModel(),
                vehicle.getPrice(),
                vehicle.getStatus()
        );
    }
    
    private Vehicle convertToEntity(VehicleDTO vehicleDTO, Dealer dealer) {
        Vehicle vehicle = new Vehicle();
        vehicle.setDealer(dealer);
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setPrice(vehicleDTO.getPrice());
        vehicle.setStatus(vehicleDTO.getStatus());
        return vehicle;
    }
}


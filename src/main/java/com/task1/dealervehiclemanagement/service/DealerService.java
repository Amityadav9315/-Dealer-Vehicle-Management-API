package com.task1.dealervehiclemanagement.service;

import com.task1.dealervehiclemanagement.dto.DealerDTO;
import com.task1.dealervehiclemanagement.model.Dealer;
import com.task1.dealervehiclemanagement.repository.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealerService {
    
    @Autowired
    private DealerRepository dealerRepository;
    
    public List<DealerDTO> getAllDealers() {
        return dealerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public DealerDTO getDealerById(Long id) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + id));
        return convertToDTO(dealer);
    }
    
    public DealerDTO createDealer(DealerDTO dealerDTO) {
        // Check if email already exists
        if (dealerRepository.findByEmail(dealerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Dealer with email " + dealerDTO.getEmail() + " already exists");
        }
        
        Dealer dealer = convertToEntity(dealerDTO);
        Dealer savedDealer = dealerRepository.save(dealer);
        return convertToDTO(savedDealer);
    }
    
    public DealerDTO updateDealer(Long id, DealerDTO dealerDTO) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (!dealer.getEmail().equals(dealerDTO.getEmail())) {
            if (dealerRepository.findByEmail(dealerDTO.getEmail()).isPresent()) {
                throw new RuntimeException("Dealer with email " + dealerDTO.getEmail() + " already exists");
            }
        }
        
        dealer.setName(dealerDTO.getName());
        dealer.setEmail(dealerDTO.getEmail());
        dealer.setSubscriptionType(dealerDTO.getSubscriptionType());
        
        Dealer updatedDealer = dealerRepository.save(dealer);
        return convertToDTO(updatedDealer);
    }
    
    public void deleteDealer(Long id) {
        if (!dealerRepository.existsById(id)) {
            throw new RuntimeException("Dealer not found with id: " + id);
        }
        dealerRepository.deleteById(id);
    }
    
    private DealerDTO convertToDTO(Dealer dealer) {
        return new DealerDTO(
                dealer.getId(),
                dealer.getName(),
                dealer.getEmail(),
                dealer.getSubscriptionType()
        );
    }
    
    private Dealer convertToEntity(DealerDTO dealerDTO) {
        Dealer dealer = new Dealer();
        dealer.setName(dealerDTO.getName());
        dealer.setEmail(dealerDTO.getEmail());
        dealer.setSubscriptionType(dealerDTO.getSubscriptionType());
        return dealer;
    }
}


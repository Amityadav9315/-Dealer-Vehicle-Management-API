package com.task1.dealervehiclemanagement.controller;

import com.task1.dealervehiclemanagement.dto.DealerDTO;
import com.task1.dealervehiclemanagement.service.DealerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {
    
    @Autowired
    private DealerService dealerService;
    
    @GetMapping
    public ResponseEntity<List<DealerDTO>> getAllDealers() {
        List<DealerDTO> dealers = dealerService.getAllDealers();
        return ResponseEntity.ok(dealers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DealerDTO> getDealerById(@PathVariable Long id) {
        DealerDTO dealer = dealerService.getDealerById(id);
        return ResponseEntity.ok(dealer);
    }
    
    @PostMapping
    public ResponseEntity<DealerDTO> createDealer(@Valid @RequestBody DealerDTO dealerDTO) {
        DealerDTO createdDealer = dealerService.createDealer(dealerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDealer);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DealerDTO> updateDealer(@PathVariable Long id, @Valid @RequestBody DealerDTO dealerDTO) {
        DealerDTO updatedDealer = dealerService.updateDealer(id, dealerDTO);
        return ResponseEntity.ok(updatedDealer);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id) {
        dealerService.deleteDealer(id);
        return ResponseEntity.noContent().build();
    }
}


package com.musalasoft.drones.controllers; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import com.musalasoft.drones.dto.DroneDto; 
import com.musalasoft.drones.services.DroneRepositoryService;
import com.musalasoft.drones.services.ManageDroneService; 

@RestController
@RequestMapping("/api/management/drones") 
@Api(value = "Drone Management Controller", protocols = "http")
public class DroneController {

    private final DroneRepositoryService droneRepositoryService;
    private final ManageDroneService manageDroneService; 
    public DroneController(DroneRepositoryService droneService, ManageDroneService manageDroneService) {
        this.droneRepositoryService = droneService;
        this.manageDroneService = manageDroneService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){ return droneRepositoryService.findAll();}

    @PostMapping("/saveDrone/")
    public ResponseEntity<?> save(@RequestBody DroneDto drone){ return manageDroneService.registerDrone(drone); }

    @PostMapping("/findDrone/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){ return droneRepositoryService.findById(id); }

    @DeleteMapping("/deleteDrone/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){ return droneRepositoryService.deleteById(id); }
}
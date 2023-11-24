package com.musalasoft.drones.controllers; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import com.musalasoft.drones.dto.DroneDto;
import com.musalasoft.drones.dto.DroneLoaderRequestDto;
import com.musalasoft.drones.dto.DroneRequestDto; 
import com.musalasoft.drones.services.ManageDroneService; 

@RestController
@RequestMapping("/api/management/drones")
@Api(value = "Drone Management Controller", protocols = "http")
public class DispatchController {

    private   ManageDroneService manageDroneService;

    public DispatchController(ManageDroneService manageDroneService) {
        this.manageDroneService = manageDroneService;
    }

    //Register Drone
    @PostMapping("/register-drone")
    public ResponseEntity<?> saveDrone(@RequestBody DroneDto drone){ return manageDroneService.registerDrone(drone); }

     //Update Drone Info
    @PostMapping("/update-drone")
    public ResponseEntity<?> updateDrone(@RequestBody DroneDto drone){ return manageDroneService.updateDrone(drone); }


    //Get Available  Drones 
    @GetMapping("/get-available-drones")
    public ResponseEntity<?> getAvailableDrones()
        { return manageDroneService.getAvailableDronesForLoading(); } 

    //Load Drone with medicine
    @PostMapping("/load-drone")
    public ResponseEntity<?> loadDroneWithMedication(@RequestBody DroneLoaderRequestDto request)
        { return manageDroneService.loadDroneWithMedications(request.getDroneId(), request.getMedications()); }

    //get loaded medications for a given drone
    @GetMapping("/get-drone-medications")
    public ResponseEntity<?> getDroneContents(@RequestBody DroneRequestDto request)
        { return manageDroneService.getDroneMedicationContent(request.getDroneId() ); }

    //get battery level for a given drone
    @GetMapping("/get-drone-batterylevel")
    public ResponseEntity<?> getDroneBatteryLevel(@RequestBody DroneRequestDto request)
        { return manageDroneService.checkDroneBatteryLevel(request.getDroneId() ); } 
}
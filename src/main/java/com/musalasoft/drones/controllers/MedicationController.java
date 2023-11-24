package com.musalasoft.drones.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.http.HttpStatus; 
import io.swagger.annotations.Api;

import com.musalasoft.drones.entities.Medication;
import com.musalasoft.drones.services.MedicationRepositoryService; 
import com.musalasoft.drones.dto.MedicationDto;
 
@RestController
@RequestMapping("/api/management/medications")
@Api(value = "Drone Management Controller", protocols = "http")
public class MedicationController {

    private final MedicationRepositoryService medicationRepositoryService;

    public MedicationController(MedicationRepositoryService medicationService) {
        this.medicationRepositoryService = medicationService;
    }

    @GetMapping("/getall-medications")
    public ResponseEntity<?> findAll(){ return medicationRepositoryService.findAll(); } 
    /**
     * @param medication
     * @return
     */
    @PostMapping( "/create-medication")
    public ResponseEntity<?> save(@RequestBody MedicationDto medication){
         byte[] imageBytes =null;
         Path imagePath = Paths.get(medication.getImagePath());
         
         String contentType = null;
         try {
             contentType = Files.probeContentType(imagePath);
         } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content Type cannot be read!"); 
         }

         if (contentType != null && contentType.equals("image/jpeg")) {
            // Read the image file into a byte array 
            try {
                imageBytes = Files.readAllBytes(imagePath);
                 // Process the imageBytes array as needed 
                return medicationRepositoryService.createMedication(imageBytes,medication.getName() , medication.getCode(), medication.getWeight());
 
            } catch (IOException e) {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image cannot be read!"); 
            }

           
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is not in acceptable image format. Expected format is image/jpeg!");
        }  
          }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){ return medicationRepositoryService.findById(id);}

    @GetMapping("/findbyname/{name}")
    public List<Medication> findByName(@PathVariable String name){ return medicationRepositoryService.findByName(name);}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){ return medicationRepositoryService.deleteById(id);}
}
 

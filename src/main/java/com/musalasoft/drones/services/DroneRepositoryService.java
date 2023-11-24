package com.musalasoft.drones.services;

 
import org.springframework.stereotype.Service; 
import com.musalasoft.drones.entities.Drone; 
import com.musalasoft.drones.repositories.DroneRepository; 

@Service
public class DroneRepositoryService extends RepositoryService<Drone, Long>{
 private final DroneRepository droneRepository; 
    /**
     * @param droneRepository
     */
    public DroneRepositoryService(DroneRepository droneRepository) {
        super(droneRepository, "Drone");
        this.droneRepository=droneRepository;
    }

    public Drone findBySerialNo(String serialNo) {
        return droneRepository.findBySerialNo(serialNo);
    }
     
}
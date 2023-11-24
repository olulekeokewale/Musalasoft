package com.musalasoft.drones.services;

import java.security.Identity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.musalasoft.drones.dto.DroneCapacityDto;
import com.musalasoft.drones.dto.DroneDto;
import com.musalasoft.drones.dto.Response;
import com.musalasoft.drones.entities.AuditEvent;
import com.musalasoft.drones.entities.Drone;
import com.musalasoft.drones.entities.Medication;
import com.musalasoft.drones.repositories.AuditEventRepository;
import com.musalasoft.drones.repositories.DroneRepository;
import com.musalasoft.drones.repositories.MedicationRepository;
import com.musalasoft.drones.entities.DroneState;


@Service
public class ManageDroneService {

    public static final int MAX_DRONE_CAPACITY = 500;
    public static final int MAX_DRONE_COUNT= 10;
    public static final int MAX_BATTERY_CAPACITY= 100;
    private   DroneRepository droneRepository; 
    private   MedicationRepository medicationRepository;  
    private   AuditEventRepository auditEventRepository;
    private   DroneRepositoryService droneRepositoryService;


    public  ManageDroneService(DroneRepository droneRepository,
                                  MedicationRepository medicationRepository,
                                  AuditEventRepository auditEventRepository,
                                  DroneRepositoryService droneRepositoryService) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.auditEventRepository = auditEventRepository;
        this.droneRepositoryService=droneRepositoryService;
    }

    public ResponseEntity<?> registerDrone(DroneDto droneDto ) {
            long droneCount = droneRepository.count();
            if (droneCount < MAX_DRONE_COUNT) { 
                    Drone drone = droneDto.toEntity();
                    drone.setDroneState(DroneState.IDLE);
                    drone.setMedications(null);
                    droneRepository.save(drone);

                    Response response = new Response();
                    response.setMessage("Drone Addedd to the queue");
                    response.setStatus("Success"); 
                    
                    return ResponseEntity.status(HttpStatus.OK).body( response);
            } 
           else{
                    Response response = new Response();
                    response.setMessage("Drone Limit is filled!");
                    response.setStatus("Failed"); 
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
           }
    }
    public ResponseEntity<?> updateDrone(DroneDto droneDto ) {
            Drone drone = droneRepositoryService.findBySerialNo(  droneDto.getSerialNumber()); 
          //  droneDto.setId(drone.getId());
//d1_0.id,d1_0.battery_capacity,d1_0.drone_model,d1_0.drone_state,d1_0.serial_number,d1_0.weight_limit
            if (drone!=null) { 

                   // drone.setId(droneDto.getId());
                    drone.setBatteryCapacity(MAX_BATTERY_CAPACITY);
                    drone.setDroneModel(droneDto.getDroneModel()); 
                    drone.setWeightLimit(droneDto.getWeightLimit()); 
                    drone.setDroneState(droneDto.getDroneState());
                    drone.setMedications(null);
                    droneRepository.save(drone);

                    Response response = new Response();
                    response.setMessage("Drone Updated");
                    response.setStatus("Success"); 

                    return ResponseEntity.status(HttpStatus.OK).body(response);
            } 
           else{
                    Response response = new Response();
                    response.setMessage("Drone Not Found!");
                    response.setStatus("Failed"); 
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Drone Not Found!");
           }
    }
    
    public ResponseEntity<?> loadDroneWithMedications(Long droneId, List<Long> medicationListId) {
        try {
            Drone drone = droneRepository.findById(droneId).get();
            DroneState state = drone.getDroneState();

            if(!state.equals(DroneState.LOADED)){
                    Response response = new Response();
                    response.setMessage("The drone is already "+state +"!");
                    response.setStatus("Success"); 
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else if (drone.getBatteryCapacity() < 25) {
                    Response response = new Response();
                    response.setMessage("Drone battery is below 25%!");
                    response.setStatus("Failed"); 
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
 
            }
             

            List<Medication> medicationList = drone.getMedications();
            double droneAvailableCapacity = checkDroneCapacity(drone);

            for (Long medicationId : medicationListId) {
                Medication medication = medicationRepository.findById(medicationId).get();
                double medicationWeight = medication.getWeight();

                if (droneAvailableCapacity + medicationWeight <= MAX_DRONE_CAPACITY) {
                    medicationList.add(medication);
                    droneAvailableCapacity += medication.getWeight();
                    state=DroneState.LOADING;
                } else {
                    state=DroneState.LOADED;
                    Response response = new Response();
                    response.setMessage("The drone capacity is filled! It cannot accomodate additional load!");
                    response.setStatus("Success"); 
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

            drone.setMedications(medicationList);
            drone.setDroneState(state);
            droneRepository.save(drone);
            return ResponseEntity.status(HttpStatus.OK).body(drone);
        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }

     

    public ResponseEntity<?> getAvailableDronesForLoading() {
        try {
            List<Drone> droneList = droneRepository.findAll();
            List<DroneCapacityDto> availableDrones = new ArrayList<>();

            if (droneList.isEmpty()) {
                    Response response = new Response();
                    response.setMessage("Drones not found!");
                    response.setStatus("Success"); 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            for (Drone drone : droneList) {
                double droneAvailableCapacity = checkDroneCapacity(drone);
                double weightLimit = drone.getWeightLimit();

                if (droneAvailableCapacity <= weightLimit) {
                    double availableCapacity = weightLimit - droneAvailableCapacity;
                    availableDrones.add(new DroneCapacityDto(drone, availableCapacity));
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(availableDrones);

        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }
    
    public ResponseEntity<?> getDroneMedicationContent(Long droneId) {

        try {
            Drone drone = droneRepository.findById(droneId).get();
            List<Medication> medicationList = new ArrayList<>(drone.getMedications());
            return ResponseEntity.status(HttpStatus.OK).body(medicationList);

        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }

 
    }

    public ResponseEntity<?> checkBatteryCapacity(Long id) {
        try {
            if (droneRepository.findById(id).isPresent()) {
                Drone drone = droneRepository.findById(id).get();
                HashMap<String, Integer> checkBattery = new HashMap<>();
                checkBattery.put("batteryCapacity", drone.getBatteryCapacity());
                return ResponseEntity.status(HttpStatus.OK).body(checkBattery);
            }
                    Response response = new Response();
                    response.setMessage("Drone not found with id: " + id);
                    response.setStatus("Failed"); 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }
    public  ResponseEntity<?> checkDroneBatteryLevel(Long droneId){
        try{
           Drone drone = droneRepository.findById(droneId).get(); 
           Response response = new Response();
           response.setMessage("Battery of drone "+drone.getId()+" is "+drone.getBatteryCapacity()+"%.\n");
           response.setStatus("Success"); 
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }

    @Scheduled(fixedRate = 300000)
    public void checkDronesBatteryLevels(){
        try{
            List<Drone> droneList = droneRepository.findAll();
            for (Drone drone: droneList){
                AuditEvent auditEvent = new AuditEvent();
                auditEvent.setEventType("checking_battery_capacity");
                auditEvent.setDescription("Battery of drone "+drone.getId()+" is "+drone.getBatteryCapacity()+"%.\n");
                auditEvent.setTimestamp(LocalDateTime.now());
                auditEventRepository.save(auditEvent);
            }
        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }

    private double checkDroneCapacity(Drone drone) {
        double droneAvailableCapacity = 0;

        for (Medication medication : drone.getMedications()) {
            droneAvailableCapacity += medication.getWeight();
        }

        return droneAvailableCapacity;
    }
}

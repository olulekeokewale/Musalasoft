package com.musalasoft.drones.dto;

import java.util.ArrayList;
import java.util.List;

import com.musalasoft.drones.entities.Drone;
import com.musalasoft.drones.entities.Medication;
import com.musalasoft.drones.services.MedicationRepositoryService;
import com.musalasoft.drones.entities.DroneModel;
import com.musalasoft.drones.entities.DroneState;

public class DroneDto {

 
    private MedicationRepositoryService medicationService;

    private Long id;
    private String serialNumber;
    private double weightLimit;
    private int batteryCapacity;
    private DroneModel droneModel ;
    private DroneState droneState ;

    private List<Long> medications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    } 

    public DroneModel getDroneModel () {
        return droneModel ;
    }

    public void setDroneModel (DroneModel droneModel ) {
      this.droneModel  = droneModel ;
    }

    public DroneState getDroneState () {
        return droneState ;
    }

    public void setDroneState(DroneState droneState ) {
         this.droneState = droneState ;
    }

    public List<Long> getMedications() {
        return medications;
    }

    public void setMedications(List<Long> medications) {
        this.medications = medications;
    }

    public Drone toEntity(){
        Drone drone = new Drone();

        drone.setId(id);
        drone.setBatteryCapacity(batteryCapacity);
        drone.setSerialNumber(serialNumber);
        drone.setWeightLimit(weightLimit);
        drone.setDroneModel( droneModel );
        drone.setDroneState(droneState);
        List<Medication> medicationList = new ArrayList<>();
        
        if(medications!=null && medications.size()>0){
            for(Long medicationId : medications){
                medicationList.add(medicationService.findMedicationById(medicationId));
            }
            drone.setMedications(medicationList); 
        }
        
        return drone;
    }

    public DroneDto toDTO(Drone drone){
        if(drone == null){
            return null;
        }

        DroneDto droneDTO = new DroneDto();

        droneDTO.setId(drone.getId());
        droneDTO.setSerialNumber(drone.getSerialNumber());
        droneDTO.setBatteryCapacity(drone.getBatteryCapacity());
        droneDTO.setWeightLimit(drone.getWeightLimit());
        droneDTO.setDroneModel(drone.getDroneModel());
        droneDTO.setDroneState(drone.getDroneState() );
        List<Long> medicationList = new ArrayList<>();
        for(Medication medication : drone.getMedications()){
            medicationList.add(medication.getId());
        }
        droneDTO.setMedications(medicationList);

        return droneDTO;
    }

}
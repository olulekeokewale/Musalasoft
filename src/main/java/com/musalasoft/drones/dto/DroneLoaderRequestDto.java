package com.musalasoft.drones.dto;

import java.util.List;

 
public class DroneLoaderRequestDto {

    private Long droneId;
    private List<Long> medications;

    public Long getDroneId() {
        return droneId;
    }
    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }
    public List<Long> getMedications() {
        return medications;
    }
    public void setMedications(List<Long> medications) {
        this.medications = medications;
    }
}

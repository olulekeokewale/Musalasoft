package com.musalasoft.drones.dto;

import com.musalasoft.drones.entities.Drone;

public class DroneCapacityDto {
    private Drone drone;
    private double capacityAvailable;

     public DroneCapacityDto() {
         
    }

    public DroneCapacityDto(Drone drone, double capacityAvail) {
        this.drone = drone;
        this.capacityAvailable = capacityAvail;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public double getCapacityAvailable() {
        return capacityAvailable;
    }

    public void setCapacityAvailable(int capacity) {
        this.capacityAvailable = capacity;
    }
}

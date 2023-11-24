package com.musalasoft.drones.entities; 

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max; 

@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 100, unique = true)
    private String serialNumber;

    @Column(name = "weight_limit", nullable = false)
    private double weightLimit;

    @Min(0)
    @Max(100)
    @Column(name = "battery_capacity", nullable = false)
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneModel droneModel;
    @Enumerated(EnumType.STRING)
    private DroneState droneState;

    @ManyToMany
    @JoinTable(name = "drone_medications",
            joinColumns = @JoinColumn(name = "drone_id"),
            inverseJoinColumns = @JoinColumn(name = "medications_id"))
    private List<Medication> medications = new ArrayList<>();

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public DroneState getDroneState() {
        return droneState;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setDroneState(DroneState droneState) {
        this.droneState = droneState;
    }

    public DroneModel getDroneModel() {
        return droneModel;
    }

    public void setDroneModel(DroneModel droneModel) {
        this.droneModel = droneModel;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

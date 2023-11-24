package com.musalasoft.drones.dto;

import org.springframework.web.multipart.MultipartFile;

import com.musalasoft.drones.entities.Medication;

public class MedicationDto {
    private String name;
    private double weight;
    private String code;
    private String imagePath;
    private byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public Medication toEntity(){
        Medication medication = new Medication();
       // byte[] imageByte=convertMultiPartToBytes(image);

        medication.setName(name);
        medication.setCode(code);
        medication.setWeight(weight);
        medication.setImage(image);

        return medication;
    }

    public   byte[] convertMultiPartToBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
            return null;
        }
    }
}

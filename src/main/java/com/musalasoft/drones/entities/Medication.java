package com.musalasoft.drones.entities; 

import jakarta.persistence.*; 
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Medication implements NamedEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "code", nullable = false, unique = true)
    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;

    @Column(name = "image")
    @Lob
    @Size(max = 524288) // 512 KB
    private byte[] image;


    @Override
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
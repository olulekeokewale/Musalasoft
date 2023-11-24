package com.musalasoft.drones.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException; 

import com.musalasoft.drones.entities.Medication;
import com.musalasoft.drones.repositories.MedicationRepository;

@Service
public class MedicationRepositoryService extends RepositoryService<Medication, Long>{

    private final MedicationRepository medicationRepository; 
   
    public MedicationRepositoryService(MedicationRepository medicationRepository) {
        super(medicationRepository, "Medication");
        this.medicationRepository = medicationRepository;
        
    }


    public ResponseEntity<?> createMedication( byte[]  image, String name, String code, double weight) {
         
        try{
                    Medication medication = new Medication(); 
                    medication.setImage(image);
                    medication.setName(name);
                    medication.setCode(code);
                    medication.setWeight(weight);

                    return ResponseEntity.status(HttpStatus.OK).body(medicationRepository.save(medication)); 
         
        } catch (Exception e){
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }
    
    public List<Medication> findByName(String name) {
         
            Medication medication  = new Medication(); 
            medication.setName(name);  

        Example<Medication> example = Example.of(medication);
        return findAll(example);
    }
    
    public Medication findMedicationById(Long id){
        try{
            Optional<Medication> optional = medicationRepository.findById(id);
            Medication medication = (Medication) optional.get();
            return medication;
        }catch (Exception e){
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }
    
}
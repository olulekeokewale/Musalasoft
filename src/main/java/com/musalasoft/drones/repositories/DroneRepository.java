package com.musalasoft.drones.repositories;

 
import com.musalasoft.drones.entities.Drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DroneRepository extends JpaRepository <Drone, Long>  {
     
    @Query(value = "SELECT d.* FROM Drone d WHERE d.serial_number = ?1", nativeQuery = true)
    Drone findBySerialNo(@Param("serial_number") String  seriaNo);
}
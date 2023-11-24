package com.musalasoft.drones.repositories;

import com.musalasoft.drones.entities.AuditEvent; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {
}
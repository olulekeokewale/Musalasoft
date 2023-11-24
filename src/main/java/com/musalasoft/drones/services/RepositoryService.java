package com.musalasoft.drones.services;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example; 
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;

public class RepositoryService<T , ID> {

    private final JpaRepository<T, ID> jpaRepository; 
    //private final JpaSpecificationExecutor<T> jpaSpecificationExecutor;
    private final String ENTITY_NAME;


    public RepositoryService(
        JpaRepository<T,ID> jpaRepository, 
        //JpaSpecificationExecutor<T> jpaSpecificationExecutor,
        String entityName) {
        this.jpaRepository = jpaRepository;
      //  this.jpaSpecificationExecutor = jpaSpecificationExecutor;
        ENTITY_NAME = entityName;
    }

    public ResponseEntity<?> save(T entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(jpaRepository.save(entity));
        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
         }
    }

    public ResponseEntity<?> findAll() {
        try {
            Optional<Object> optional = Optional.of(jpaRepository.findAll());
            return optional.map(o -> new ResponseEntity<>(o, HttpStatus.OK))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ENTITY_NAME+" not found!"));
        } catch (Exception e){
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }
    public ResponseEntity<?> findByFieldName(String fieldName, Object value) {
        try {
            System.out.println("Searching for " + fieldName +" :  with " + value); // Debug info
            Specification<T> spec = (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(fieldName), value);

            
         
            System.out.println("jpaSpecificationExecutor is null !"); // Debug info
             
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ENTITY_NAME + " not found with " + fieldName + ": " + value);
        } catch (Exception e) {
            
            System.out.println(e.getMessage()); // Debug info
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }
    public ResponseEntity<?> findById(ID id) {
        try {
            return jpaRepository.existsById(id)
                    ? ResponseEntity.status(HttpStatus.OK).body(jpaRepository.findById(id))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ENTITY_NAME+" not found with id: " + id);

       } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return jpaRepository.findAll(example);
    }
    
    public ResponseEntity<?> deleteById(ID id) {
        try {
            if (jpaRepository.existsById(id)) {
                jpaRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(ENTITY_NAME+" deleted!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ENTITY_NAME+" not found with id: " + id);
        } catch (Exception e) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                    null
            );
        }
    }

}
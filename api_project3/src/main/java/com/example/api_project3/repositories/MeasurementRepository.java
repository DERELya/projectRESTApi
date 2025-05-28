package com.example.api_project3.repositories;

import com.example.api_project3.model.Measurements;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurements,Integer> {
    List<Measurements> findByRainingIsTrue();
}

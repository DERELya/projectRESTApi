package com.example.api_project3.services;

import com.example.api_project3.model.Measurements;
import com.example.api_project3.repositories.MeasurementRepository;
import com.example.api_project3.util.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurements> findAll() {
        return measurementRepository.findAll();
    }
    public Measurements findById(int id) {
        Optional<Measurements> measurements = measurementRepository.findById(id);
        return measurements.orElseThrow(MeasurementNotFoundException::new);
    }

    @Transactional
    public void save(Measurements measurements) {
        enrichMeasurements(measurements);
        measurementRepository.save(measurements);
    }

    public void enrichMeasurements(Measurements measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
    }
    public int findAllRainingCount() {
        System.out.println(measurementRepository.findByRainingIsTrue().size());
        return measurementRepository.findByRainingIsTrue().size();
    }

}

package com.example.api_project3.services;

import com.example.api_project3.dto.MeasurementsDTO;
import com.example.api_project3.model.Sensor;
import com.example.api_project3.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public Sensor getById(int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.orElse(null);
    }
    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findSensorByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
         sensorRepository.save(sensor);
    }
}

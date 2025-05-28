package com.example.api_project3.controllers;

import com.example.api_project3.dto.SensorDTO;
import com.example.api_project3.model.Sensor;
import com.example.api_project3.services.SensorService;
import com.example.api_project3.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        Sensor sensorToAdd = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToAdd,bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }

        sensorService.save(sensorToAdd);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sensor saved successfully");
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException ex) {
        MeasurementErrorResponse responce = new MeasurementErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }


    public Sensor convertToSensor(SensorDTO sensordto) {
        return modelMapper.map(sensordto, Sensor.class);
    }
}

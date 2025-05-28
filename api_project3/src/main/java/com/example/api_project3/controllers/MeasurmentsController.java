package com.example.api_project3.controllers;

import com.example.api_project3.dto.MeasurementsDTO;
import com.example.api_project3.dto.MeasurementsResponce;
import com.example.api_project3.dto.SensorDTO;
import com.example.api_project3.model.Measurements;
import com.example.api_project3.model.Sensor;
import com.example.api_project3.services.MeasurementService;
import com.example.api_project3.services.SensorService;
import com.example.api_project3.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.api_project3.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurmentsController {
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final MeasurementValidator measurementValidator;
    private final SensorValidator sensorValidator;

    @Autowired
    public MeasurmentsController(ModelMapper modelMapper, MeasurementService measurementService, SensorService sensorService, MeasurementValidator measurementValidator, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.measurementValidator = measurementValidator;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public MeasurementsResponce getAllMeasurements() {
        return new MeasurementsResponce(measurementService.findAll().stream().map(this::convertToMeasurementsDTO)
                .collect(Collectors.toList()));
    }
    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.findAllRainingCount();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult bindingResult) {
        // Валидация перед конвертацией
        Measurements measurementsAdd = convertToMeasurements(measurementsDTO);
        measurementValidator.validate(measurementsAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }
        measurementService.save(measurementsAdd);
        return ResponseEntity.status(HttpStatus.CREATED).body("Measurement saved successfully");
    }


    @ExceptionHandler
    private ResponseEntity<MeasurementException> handleException(MeasurementNotFoundException e) {
        MeasurementException responce= new MeasurementException(
                    "Measure not found");
        return new ResponseEntity<>(responce, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementException> handleException(MeasurementNotCreatedException e) {
        MeasurementException responce= new MeasurementException(
                "Measure not created");
        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }


    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurements) {
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }

    private Measurements convertToMeasurements(MeasurementsDTO dto) {
        Measurements measurements = modelMapper.map(dto, Measurements.class);
        measurements.setMeasurementDateTime(LocalDateTime.now());
        return measurements;
    }
}

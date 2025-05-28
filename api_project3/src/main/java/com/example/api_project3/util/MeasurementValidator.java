package com.example.api_project3.util;

import com.example.api_project3.model.Measurements;
import com.example.api_project3.repositories.MeasurementRepository;
import com.example.api_project3.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementValidator(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurements measurements = (Measurements) target;
        if (measurements.getSensor() == null) {
            errors.rejectValue("sensor", "measurements.required", "Measurement sensor is required");
        }
    }
}

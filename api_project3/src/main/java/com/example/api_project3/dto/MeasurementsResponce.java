package com.example.api_project3.dto;

import java.util.List;

public class MeasurementsResponce {
    List<MeasurementsDTO> measurements;

    public MeasurementsResponce(List<MeasurementsDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementsDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementsDTO> measurements) {
        this.measurements = measurements;
    }
}

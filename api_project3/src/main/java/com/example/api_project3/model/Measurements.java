package com.example.api_project3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    @Max(100)
    @Min(-100)
    @NotNull
    private Double value;

    @Column(name = "raining")
    @NotNull
    private boolean raining;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "sensor",referencedColumnName = "name")
    private Sensor sensor;

    @Column(name = "measurement_date_time")
    @NotNull
    private LocalDateTime measurementDateTime;


    public Measurements() {}
    public Measurements(Double value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }
}

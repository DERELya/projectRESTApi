package com.example.api_project3.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 3, max = 30)
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

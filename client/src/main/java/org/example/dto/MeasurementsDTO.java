package org.example.dto;

public class MeasurementsDTO {
    private Double  value;
    private Boolean isRaining;
    private SensorDTO sensor;


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return isRaining;
    }

    public void setRaining(Boolean raining) {
        isRaining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MeasurementsDTO{");
        sb.append("value=").append(value);
        sb.append(", isRaining=").append(isRaining);
        sb.append(", sensor=").append(sensor.getName());
        sb.append('}');
        return sb.toString();
    }
}

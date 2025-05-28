package org.example;

import org.example.dto.MeasurementsDTO;
import org.example.dto.MeasurementsResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.knowm.xchart.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int option;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Menu");
            System.out.println("1. Register New Sensor");
            System.out.println("2. Register new measurements");
            System.out.println("3. Register 1000 measurements");
            System.out.println("4. Display measurements");
            System.out.println("5. Display count of measurements with rain is true");
            System.out.println("6. Display chart of temperatures");
            System.out.println("0. Exit");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Register New Sensor");
                    System.out.println("Enter Sensor name: ");
                    String nameSensor = scanner.next();
                    RegisterSensor(nameSensor);
                    break;
                case 2:
                    System.out.println("Register New Measurement");
                    System.out.println("enter name: ");
                    String name = scanner.next();
                    System.out.println("enter value: ");
                    double value = scanner.nextDouble();
                    System.out.println("enter rain: ");
                    boolean rain = scanner.nextBoolean();
                    sendMeasurement(value, rain, name);
                    break;
                case 3:
                    System.out.println("Register 1000 measurements");
                    Random rand = new Random();
                    double minValue=-100,maxValue=100;
                    String sensorName="something";
                    for (int i = 0; i < 1000; i++) {
                        sendMeasurement(minValue + rand.nextDouble(200), rand.nextBoolean(),sensorName);
                    }
                    break;
                case 4:
                    System.out.println("Display measurements");
                    List<MeasurementsDTO> list=getMeasurementsFromServer();
                    for (MeasurementsDTO dto : list) {
                        System.out.println(dto.toString());
                    }
                    break;
                case 5:
                    System.out.println("Display count of measurements with rain is true");
                    System.out.println("count: "+getCountOfMeasurementsWithRain());
                    break;
                case 6:
                    System.out.println("Display chart of temperatures");
                    List<Double> list2=getTemparaturesFromServer();
                    DrawChart(list2);
                    break;
            }
        } while (option != 0);
        scanner.close();
    }

    private static void RegisterSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/add";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequestWithJSONData(url,jsonData);
    }
    private static List<Double> getTemparaturesFromServer(){
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements";
        MeasurementsResponse jsonResponse = restTemplate.getForObject(url, MeasurementsResponse.class);

        if (jsonResponse==null || jsonResponse.getMeasurements()==null){
            return Collections.emptyList();
        }

        return jsonResponse.getMeasurements().stream().map(MeasurementsDTO::getValue)
                .collect(Collectors.toList());
    }

    private static List<MeasurementsDTO> getMeasurementsFromServer(){
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements";
        MeasurementsResponse jsonResponse = restTemplate.getForObject(url, MeasurementsResponse.class);
        if (jsonResponse==null || jsonResponse.getMeasurements()==null){
            return Collections.emptyList();

        }
        return jsonResponse.getMeasurements().stream().toList();
    }
    private static void sendMeasurement(double value,boolean raining,String sensorName) {
        final String url = "http://localhost:8080/measurements";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor",Map.of("name",sensorName));

        makePostRequestWithJSONData(url,jsonData);
    }
    private static int getCountOfMeasurementsWithRain(){
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements/rainyDaysCount";
        return restTemplate.getForObject(url, Integer.class);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);
        try{
            String response = restTemplate.postForObject(url, request, String.class);
            System.out.println(response);
        }catch (HttpClientErrorException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void DrawChart(List<Double> temperatures) {
        double[] xData= IntStream.range(0,temperatures.size()).asDoubleStream().toArray();
        double[] yData= temperatures.stream().mapToDouble(x->x).toArray();

        XYChart chart=QuickChart.getChart("Temperatures","X","Y"
                ,"Temperatures",xData,yData);
        new SwingWrapper(chart).displayChart();
    }
}
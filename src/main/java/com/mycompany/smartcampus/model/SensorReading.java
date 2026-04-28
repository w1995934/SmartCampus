package com.mycompany.smartcampus.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SensorReading {

    private String readingID;
    private String sensorID;
    private double value;
    private String unit;
    private String timestamp;

    public SensorReading() {}

public SensorReading(String readingID, String sensorID, double value, String unit) {
    this.readingID = readingID;
    this.sensorID = sensorID;
    this.value = value;
    this.unit = unit;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
}

    public String getReadingID() { return readingID; }
    public void setReadingID(String readingID) { this.readingID = readingID; }
    public String getSensorID() { return sensorID; }
    public void setSensorID(String sensorID) { this.sensorID = sensorID; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
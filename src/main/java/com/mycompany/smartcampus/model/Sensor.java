package com.mycompany.smartcampus.model;

public class Sensor {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_MAINTENANCE = "MAINTENANCE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    private String sensorID;
    private String type;
    private String status;
    private String roomID;
    private double currentValue;

    public Sensor() {}
    public Sensor( String type, String sensorID,String status,double currentValue,String roomID) {
        this.sensorID = sensorID;
        this.type = type;
        this.status = status;
        this.roomID = roomID;
        this.currentValue = currentValue;
    }

    public String getSensorID() { return sensorID; }
    public void setSensorID(String sensorID) { this.sensorID = sensorID; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRoomID() { return roomID; }
    public void setRoomID(String roomID) { this.roomID = roomID; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }
}
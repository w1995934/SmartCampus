package com.mycompany.smartcampus.exception;

public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String sensorID) {
        super("Sensor " + sensorID + " is under maintenance. Please try again later.");
    }
    
}

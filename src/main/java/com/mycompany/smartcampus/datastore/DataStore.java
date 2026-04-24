package com.mycompany.smartcampus.datastore;

import com.mycompany.smartcampus.model.Room;
import com.mycompany.smartcampus.model.Sensor;
import com.mycompany.smartcampus.model.SensorReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStore {

    public static final HashMap<String, Room> rooms = new HashMap<>();
    public static final HashMap<String, Sensor> sensors = new HashMap<>();
    public static final HashMap<String, List<SensorReading>> readings = new HashMap<>();

    static {
        // samples for postman
        rooms.put("room1", new Room("room1", "Lab A", "Engineering block", 1));
        rooms.put("room2", new Room("room2", "Lecture hall B", "Science block", 2));
        rooms.put("room3", new Room("room3", "Server room", "IT block", 0));

        sensors.put("sensor1", new Sensor("sensor1", "CO2", Sensor.STATUS_ACTIVE, "room1", 418.5));
        sensors.put("sensor2", new Sensor("sensor2", "Temperature", Sensor.STATUS_ACTIVE, "room1", 21.1));
        sensors.put("sensor3", new Sensor("sensor3", "Humidity", Sensor.STATUS_MAINTENANCE, "room2", 49.0));

        List<SensorReading> sensor1Readings = new ArrayList<>();
        sensor1Readings.add(new SensorReading("reading1", "sensor1", 408.0, "ppm"));
        sensor1Readings.add(new SensorReading("reading2", "sensor1", 409.5, "ppm"));
        readings.put("sensor1", sensor1Readings);

        List<SensorReading> sensor2Readings = new ArrayList<>();
        sensor2Readings.add(new SensorReading("reading3", "sensor2", 19.3, "°C"));
        readings.put("sensor2", sensor2Readings);
    }
}
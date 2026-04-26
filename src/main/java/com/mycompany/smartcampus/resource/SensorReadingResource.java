package com.mycompany.smartcampus.resource;

import com.mycompany.smartcampus.datastore.DataStore;
import com.mycompany.smartcampus.exception.SensorUnavailableException;
import com.mycompany.smartcampus.model.Sensor;
import com.mycompany.smartcampus.model.SensorReading;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class SensorReadingResource {

    private String sensorID;

    public SensorReadingResource(String sensorID) {
        this.sensorID = sensorID;}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        if (!DataStore.sensors.containsKey(sensorID)) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Sensor with ID " + sensorID + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        List<SensorReading> readings = DataStore.readings.getOrDefault(sensorID, new ArrayList<>());
        return Response.ok(readings).build();}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorID);

        if (sensor == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Sensor with ID " + sensorID + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (sensor.getStatus().equals(Sensor.STATUS_MAINTENANCE)) {
            throw new SensorUnavailableException(sensorID);
        }

        reading.setReadingID(UUID.randomUUID().toString());
        reading.setSensorID(sensorID);
        DataStore.readings.computeIfAbsent(sensorID, k -> new ArrayList<>()).add(reading);
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }}
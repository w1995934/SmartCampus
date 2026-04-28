package com.mycompany.smartcampus.resource;

import com.mycompany.smartcampus.datastore.DataStore;
import com.mycompany.smartcampus.exception.LinkedResourceNotFoundException;
import com.mycompany.smartcampus.model.Sensor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/v1/sensors")
public class SensorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensorList = new ArrayList<>(DataStore.sensors.values());

        if (type != null && !type.trim().isEmpty()) {
            List<Sensor> filtered = new ArrayList<>();
            for (Sensor sensor : sensorList) {
                if (sensor.getType().equalsIgnoreCase(type)) {
                    filtered.add(sensor);
                }
            }
            return Response.ok(filtered).build();
        }

        return Response.ok(sensorList).build();}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (sensor.getSensorID() == null || sensor.getSensorID().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Sensor ID cannot be empty\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (!DataStore.rooms.containsKey(sensor.getRoomID())) {
            throw new LinkedResourceNotFoundException("Room with ID " + sensor.getRoomID() + " does not exist");
        }

        if (DataStore.sensors.containsKey(sensor.getSensorID())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\": \"Sensor with this ID already exists\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        DataStore.sensors.put(sensor.getSensorID(), sensor);
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @Path("/{sensorID}/readings")
    public SensorReadingResource getSensorReadings(@PathParam("sensorID") String sensorID) {
        return new SensorReadingResource(sensorID);
    }}
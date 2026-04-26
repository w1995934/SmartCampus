package com.mycompany.smartcampus.resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.smartcampus.datastore.DataStore;
import com.mycompany.smartcampus.exception.RoomNotEmptyException;
import com.mycompany.smartcampus.model.Room;


@Path("/rooms")
public class RoomResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms() {
        return Response.ok(DataStore.rooms.values()).build();}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        if (room.getRoomID() == null || room.getRoomID().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Room ID cannot beempty\"}" )
                    .type(MediaType.APPLICATION_JSON)
                    .build();
                }

        if (DataStore.rooms.containsKey(room.getRoomID())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\": \"Room with this ID already exists\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();}
DataStore.rooms.put(room.getRoomID(), room);

    return Response.status(Response.Status.CREATED).entity(room).build();}
    @GET
    @Path("/{roomID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomByID(@PathParam("roomID") String roomID) {
        Room room = DataStore.rooms.get(roomID);

        if (room == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Room with ID " + roomID + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    return Response.ok(room).build();}

    @DELETE
    @Path("/{roomID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomID") String roomID) {
        Room room = DataStore.rooms.get(roomID);

        if (room == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Room with ID " + roomID + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();}

        boolean hasSensors = DataStore.sensors.values().stream()
                .anyMatch(sensor -> sensor.getRoomID().equals(roomID));

        if (hasSensors) {
            throw new RoomNotEmptyException(roomID);
        }
    DataStore.rooms.remove(roomID);

        Map<String, String> message = new HashMap<>();
        message.put("message", "Room " + roomID + " successfully deleted");
        return Response.ok(message).build();
    }
}

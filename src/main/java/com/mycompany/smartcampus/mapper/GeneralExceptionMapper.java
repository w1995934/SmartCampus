package com.mycompany.smartcampus.mapper;

import com.mycompany.smartcampus.exception.LinkedResourceNotFoundException;
import com.mycompany.smartcampus.exception.RoomNotEmptyException;
import com.mycompany.smartcampus.exception.SensorUnavailableException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = Logger.getLogger(GeneralExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof NotFoundException) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", "The requested resource was not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof LinkedResourceNotFoundException) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 422);
            error.put("error", "Unprocessable Entity");
            error.put("message", exception.getMessage());
            return Response.status(422)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof RoomNotEmptyException) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 409);
            error.put("error", "Conflict");
            error.put("message", exception.getMessage());
            return Response.status(Response.Status.CONFLICT)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof SensorUnavailableException) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 403);
            error.put("error", "Forbidden");
            error.put("message", exception.getMessage());
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        logger.severe("Unexpected error!: " + exception.getMessage());
        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("error", "Internal Server Error");
        error.put("message", "Unexpected error, try again later.");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
}}
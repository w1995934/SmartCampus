package com.mycompany.smartcampus.config;

import com.mycompany.smartcampus.filter.LoggingFilter;
import com.mycompany.smartcampus.mapper.GeneralExceptionMapper;
import com.mycompany.smartcampus.resource.DiscoveryResource;
import com.mycompany.smartcampus.resource.RoomResource;
import com.mycompany.smartcampus.resource.SensorResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(DiscoveryResource.class);
        register(RoomResource.class);
        register(SensorResource.class);
        register(GeneralExceptionMapper.class);
        register(LoggingFilter.class);
    }
}
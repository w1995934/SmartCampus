package com.mycompany.smartcampus.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        //resourcess
        classes.add(com.mycompany.smartcampus.resource.DiscoveryResource.class);
        classes.add(com.mycompany.smartcampus.resource.RoomResource.class);
        classes.add(com.mycompany.smartcampus.resource.SensorResource.class);
        //exception mappers
        classes.add(com.mycompany.smartcampus.mapper.RoomNotEmptyExceptionMapper.class);
        classes.add(com.mycompany.smartcampus.mapper.LinkedResourceNotFoundExceptionMapper.class);
        classes.add(com.mycompany.smartcampus.mapper.SensorUnavailableExceptionMapper.class);
        classes.add(com.mycompany.smartcampus.mapper.GeneralExceptionMapper.class);

        //filters
        classes.add(com.mycompany.smartcampus.filter.LoggingFilter.class);

        return classes;
    }
}
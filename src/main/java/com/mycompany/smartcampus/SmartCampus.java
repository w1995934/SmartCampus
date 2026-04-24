package com.mycompany.smartcampus;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.eclipse.jetty.server.Server;
import com.mycompany.smartcampus.config.ApplicationConfig;

import java.net.URI;
import java.util.logging.Logger;

public class SmartCampus {

    private static final Logger LOGGER = Logger.getLogger(SmartCampus.class.getName());
    private static final String BASE_URI = "http://0.0.0.0:8080/";

    public static void main(String[] args) throws Exception {
        ResourceConfig config = ResourceConfig.forApplicationClass(ApplicationConfig.class);

        Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LOGGER.info("Shutting down SmartCampus server...");
                server.stop();
            } catch (Exception e) {
                LOGGER.severe("Error during shutdown: " + e.getMessage());
            }
        }));

        LOGGER.info("SmartCampus API running at http://localhost:8080/api/v1");
        server.join();
    }
}
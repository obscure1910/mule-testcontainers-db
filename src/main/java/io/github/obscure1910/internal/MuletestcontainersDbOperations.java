package io.github.obscure1910.internal;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.jdbc.ContainerDatabaseDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MuletestcontainersDbOperations {

    private final static Logger LOGGER = LoggerFactory.getLogger(MuletestcontainersDbOperations.class);

    private final static Map<String, Connection> connections = new HashMap<>();

    public void startTestContainers(@Config MuletestcontainersDbConfiguration configuration) {
        LOGGER.info("Init connection for: " + configuration.getJdbcUrl());
        synchronized (connections) {
            try {
                connections.putIfAbsent(configuration.getJdbcUrl(), new ContainerDatabaseDriver().connect(configuration.getJdbcUrl(), new Properties()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopTestContainers(@Config MuletestcontainersDbConfiguration configuration) {
        LOGGER.info("Invalidate connection for: " + configuration.getJdbcUrl());
        synchronized (connections) {
            connections.computeIfPresent(configuration.getJdbcUrl(), (key, connection) -> {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        return connection;
                    }
            );
            connections.remove(configuration.getJdbcUrl());
        }

        ContainerDatabaseDriver.killContainer(configuration.getJdbcUrl());
    }


}

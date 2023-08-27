package io.github.obscure1910.internal;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.jdbc.ContainerDatabaseDriver;

import java.sql.SQLException;
import java.util.Properties;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MuletestcontainersDbOperations {

    private final Logger LOGGER = LoggerFactory.getLogger(MuletestcontainersDbOperations.class);

    public void startTestContainers(@Config MuletestcontainersDbConfiguration configuration) {
        try {
            LOGGER.info("Init connection for: " + configuration.getJdbcUrl());
            new ContainerDatabaseDriver().connect(configuration.getJdbcUrl(), new Properties());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopTestContainers(@Config MuletestcontainersDbConfiguration configuration) {
        LOGGER.info("Invalidate connection for: " + configuration.getJdbcUrl());
        ContainerDatabaseDriver.killContainer(configuration.getJdbcUrl());
    }


}

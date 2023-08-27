package io.github.obscure1910.internal;


import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

@Operations(MuletestcontainersDbOperations.class)
public class MuletestcontainersDbConfiguration {

    @Parameter
    @DisplayName("JDBC URL")
    private String jdbcUrl;

    public String getJdbcUrl() {
        return jdbcUrl;
    }
}

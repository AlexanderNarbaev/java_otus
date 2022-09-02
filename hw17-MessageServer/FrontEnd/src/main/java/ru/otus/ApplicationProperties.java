package ru.otus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "frontend.app")
@Validated
public class ApplicationProperties {
    private String grpcHost;
    private String grpcPort;

    public String getGrpcHost() {
        return grpcHost;
    }

    public void setGrpcHost(String grpcHost) {
        this.grpcHost = grpcHost;
    }

    public String getGrpcPort() {
        return grpcPort;
    }

    public void setGrpcPort(String grpcPort) {
        this.grpcPort = grpcPort;
    }
}
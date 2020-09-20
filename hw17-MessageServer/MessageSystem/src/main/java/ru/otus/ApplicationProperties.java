package ru.otus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "messagesystem.app")
@Validated
public class ApplicationProperties {
    private String grpcClientHost;
    private String grpcClientPort;
    private String grpcServerPort;



    public String getGrpcClientHost() {
        return grpcClientHost;
    }

    public void setGrpcClientHost(String grpcClientHost) {
        this.grpcClientHost = grpcClientHost;
    }

    public String getGrpcClientPort() {
        return grpcClientPort;
    }

    public void setGrpcClientPort(String grpcClientPort) {
        this.grpcClientPort = grpcClientPort;
    }

    public String getGrpcServerPort() {
        return grpcServerPort;
    }

    public void setGrpcServerPort(String grpcServerPort) {
        this.grpcServerPort = grpcServerPort;
    }
}

package ru.otus;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.grpc.server.DBUserServiceImpl;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    static Logger logger = LoggerFactory.getLogger(GrpcServerConfig.class);

    @Autowired
    private DBUserServiceImpl dbUserService;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public void startGrpcServer() throws IOException, InterruptedException {
        logger.error("!!!!!!!!!!!!!!!!!!" + applicationProperties.getServerPort() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        BindableService bindableService;
        Server server = ServerBuilder.forPort(
                Integer.parseInt(
                        applicationProperties.getServerPort()))
                .addService(dbUserService).build();
        server.start();
        server.awaitTermination();
    }
}

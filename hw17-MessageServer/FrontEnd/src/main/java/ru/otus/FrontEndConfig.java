package ru.otus;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.protobuf.generated.UserServiceGrpc;
import ru.otus.service.FrontendService;
import ru.otus.service.FrontendServiceImpl;

@Configuration
public class FrontEndConfig {

    @Autowired
    private ApplicationProperties applicationProperties;


    @Bean
    public FrontendService getFrontendService() {
        return new FrontendServiceImpl(createClient());
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub createClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(applicationProperties.getGrpcHost(), Integer.parseInt(applicationProperties.getGrpcPort()))
                .usePlaintext()
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        return stub;
    }
}
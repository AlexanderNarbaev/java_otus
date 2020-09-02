package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@PropertySource("classpath:application.properties")
public class MassageSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(MassageSystemApplication.class, args);
    }
}

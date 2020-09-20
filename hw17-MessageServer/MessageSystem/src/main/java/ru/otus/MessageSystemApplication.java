package ru.otus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@PropertySource("classpath:application.properties")
public class MessageSystemApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MessageSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.currentThread().join();
    }
}

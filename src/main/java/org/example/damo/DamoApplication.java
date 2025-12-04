package org.example.damo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DamoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamoApplication.class, args);
    }
}

package com.sudokuprod.cardirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class CarDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarDirectoryApplication.class, args);
    }
}

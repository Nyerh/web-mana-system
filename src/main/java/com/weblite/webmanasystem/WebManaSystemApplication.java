package com.weblite.webmanasystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.weblite.webmanasystem.mapper","com.weblite.webmanasystem.config"})
public class WebManaSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebManaSystemApplication.class, args);
    }

}

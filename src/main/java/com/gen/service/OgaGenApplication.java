package com.gen.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.gen.service.common", "com.gen.service.module"})
public class OgaGenApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(OgaGenApplication.class, args);
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(0, JsonConverter.fastConverter());
//    }
}


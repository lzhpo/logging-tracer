package com.lzhpo.logging.trace.sample.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lzhpo
 */
@SpringBootApplication
@EnableFeignClients
public class LoggingTraceFeignSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(LoggingTraceFeignSampleApplication.class, args);
  }
}

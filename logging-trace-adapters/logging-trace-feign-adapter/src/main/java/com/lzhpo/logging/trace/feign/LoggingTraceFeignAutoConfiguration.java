package com.lzhpo.logging.trace.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
public class LoggingTraceFeignAutoConfiguration {

  @Bean
  public LoggingTraceFeignRequestInterceptor loggingTraceFeignRequestInterceptor() {
    return new LoggingTraceFeignRequestInterceptor();
  }
}

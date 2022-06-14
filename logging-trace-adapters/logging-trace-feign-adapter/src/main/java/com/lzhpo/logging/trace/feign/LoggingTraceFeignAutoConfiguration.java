package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.context.LoggingTraceContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
public class LoggingTraceFeignAutoConfiguration {

  @Bean
  public LoggingTraceFeignRequestInterceptor loggingTraceFeignRequestInterceptor(
      LoggingTraceContextFactory loggingTraceContextFactory) {
    return new LoggingTraceFeignRequestInterceptor(loggingTraceContextFactory);
  }
}

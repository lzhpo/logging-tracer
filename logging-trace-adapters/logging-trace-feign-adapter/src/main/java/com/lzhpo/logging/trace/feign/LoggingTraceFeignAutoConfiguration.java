package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
public class LoggingTraceFeignAutoConfiguration {

  @Bean
  public LoggingTraceFeignRequestInterceptor loggingTraceFeignRequestInterceptor(
      LoggingTraceContextHandler traceContextHandler) {
    return new LoggingTraceFeignRequestInterceptor(traceContextHandler);
  }
}

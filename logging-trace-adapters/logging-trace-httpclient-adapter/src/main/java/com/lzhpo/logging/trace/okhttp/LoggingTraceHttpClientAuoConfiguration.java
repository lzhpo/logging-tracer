package com.lzhpo.logging.trace.okhttp;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
public class LoggingTraceHttpClientAuoConfiguration {

  @Bean
  public LoggingTraceHttpClientInterceptor loggingTraceHttpClientInterceptor(
      LoggingTraceContextHandler traceContextHandler) {
    return new LoggingTraceHttpClientInterceptor(traceContextHandler);
  }
}

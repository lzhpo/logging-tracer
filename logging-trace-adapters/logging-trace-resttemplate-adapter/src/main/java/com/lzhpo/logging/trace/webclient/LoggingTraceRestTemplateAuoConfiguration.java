package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
public class LoggingTraceRestTemplateAuoConfiguration {

  private final LoggingTraceContextHandler traceContextHandler;

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(loggingTraceRestTemplateInterceptor());
    return restTemplate;
  }

  @Bean
  public LoggingTraceRestTemplateInterceptor loggingTraceRestTemplateInterceptor() {
    return new LoggingTraceRestTemplateInterceptor(traceContextHandler);
  }
}

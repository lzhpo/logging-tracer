package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceRestTemplateAuoConfiguration {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Bean
  @ConditionalOnMissingBean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public LoggingTraceRestTemplateInterceptor loggingTraceRestTemplateInterceptor() {
    return new LoggingTraceRestTemplateInterceptor(traceHeaderProxy);
  }
}

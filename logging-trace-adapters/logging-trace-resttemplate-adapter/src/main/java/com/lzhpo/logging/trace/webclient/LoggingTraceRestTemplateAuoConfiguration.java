package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lzhpo
 */
@Configuration
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceRestTemplateAuoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public LoggingTraceRestTemplateInterceptor loggingTraceRestTemplateInterceptor(
      LoggingTraceHeaderProxy traceHeaderProxy) {
    return new LoggingTraceRestTemplateInterceptor(traceHeaderProxy);
  }
}

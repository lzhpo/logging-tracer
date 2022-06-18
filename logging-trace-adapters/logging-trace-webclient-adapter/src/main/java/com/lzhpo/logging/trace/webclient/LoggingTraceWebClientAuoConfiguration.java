package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author lzhpo
 */
@Configuration
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceWebClientAuoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public WebClient webClient() {
    return WebClient.builder().build();
  }

  @Bean
  public LoggingTraceWebClientBeanPostProcessor loggingTraceWebClientBeanPostProcessor(
      LoggingTraceHeaderProxy traceHeaderProxy) {
    return new LoggingTraceWebClientBeanPostProcessor(traceHeaderProxy);
  }
}

package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceWebClientAuoConfiguration {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Bean
  @ConditionalOnMissingBean
  public WebClient webClient() {
    return WebClient.builder().build();
  }

  @Bean
  public LoggingTraceWebClientBeanPostProcessor loggingTraceWebClientBeanPostProcessor() {
    return new LoggingTraceWebClientBeanPostProcessor(loggingTraceExchangeFilterFunction());
  }

  @Bean
  public LoggingTraceExchangeFilterFunction loggingTraceExchangeFilterFunction() {
    return new LoggingTraceExchangeFilterFunction(traceHeaderProxy);
  }
}

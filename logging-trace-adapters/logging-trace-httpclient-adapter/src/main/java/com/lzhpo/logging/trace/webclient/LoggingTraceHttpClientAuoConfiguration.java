package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceHttpClientAuoConfiguration {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Bean
  @ConditionalOnMissingBean
  public CloseableHttpClient httpClient() {
    return HttpClients.custom()
        .addRequestInterceptorFirst(loggingTraceHttpClientInterceptor())
        .build();
  }

  @Bean
  public LoggingTraceHttpClientInterceptor loggingTraceHttpClientInterceptor() {
    return new LoggingTraceHttpClientInterceptor(traceHeaderProxy);
  }
}

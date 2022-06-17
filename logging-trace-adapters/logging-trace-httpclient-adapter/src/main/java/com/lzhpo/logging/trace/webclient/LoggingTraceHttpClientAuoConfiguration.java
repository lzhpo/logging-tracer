package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
public class LoggingTraceHttpClientAuoConfiguration {

  private final LoggingTraceContextHandler traceContextHandler;

  @Bean
  public CloseableHttpClient httpClient() {
    return HttpClients.custom()
        .addRequestInterceptorFirst(loggingTraceHttpClientInterceptor())
        .build();
  }

  @Bean
  public LoggingTraceHttpClientInterceptor loggingTraceHttpClientInterceptor() {
    return new LoggingTraceHttpClientInterceptor(traceContextHandler);
  }
}

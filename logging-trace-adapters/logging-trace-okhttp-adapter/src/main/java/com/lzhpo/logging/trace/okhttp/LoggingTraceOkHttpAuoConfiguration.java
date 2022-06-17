package com.lzhpo.logging.trace.okhttp;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
public class LoggingTraceOkHttpAuoConfiguration {

  private final LoggingTraceContextHandler traceContextHandler;

  @Bean
  @ConditionalOnMissingBean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient().newBuilder().build();
  }

  @Bean
  public LoggingTraceOkHttpInterceptor loggingTraceOkHttpInterceptor() {
    return new LoggingTraceOkHttpInterceptor(traceContextHandler);
  }

  @Bean
  public LoggingTraceOkHttpBeanPostProcessor loggingTraceOkHttpBeanPostProcessor() {
    return new LoggingTraceOkHttpBeanPostProcessor(loggingTraceOkHttpInterceptor());
  }
}

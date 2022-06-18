package com.lzhpo.logging.trace.okhttp;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
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
public class LoggingTraceOkHttpAuoConfiguration {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Bean
  @ConditionalOnMissingBean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient().newBuilder().addInterceptor(loggingTraceOkHttpInterceptor()).build();
  }

  @Bean
  public LoggingTraceOkHttpInterceptor loggingTraceOkHttpInterceptor() {
    return new LoggingTraceOkHttpInterceptor(traceHeaderProxy);
  }
}

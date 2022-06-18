package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceFeignAutoConfiguration {

  @Bean
  public LoggingTraceFeignRequestInterceptor loggingTraceFeignRequestInterceptor(
      LoggingTraceHeaderProxy traceHeaderProxy) {
    return new LoggingTraceFeignRequestInterceptor(traceHeaderProxy);
  }
}

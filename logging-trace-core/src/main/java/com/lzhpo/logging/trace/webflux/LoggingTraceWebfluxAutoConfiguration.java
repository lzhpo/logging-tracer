package com.lzhpo.logging.trace.webflux;

import com.lzhpo.logging.trace.LoggingTraceCondition;
import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@Conditional({LoggingTraceCondition.class})
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class LoggingTraceWebfluxAutoConfiguration {

  @Bean
  public LoggingTraceWebfluxFilter loggingTraceWebfluxFilter(
      LoggingTraceHeaderProxy traceHeaderProxy) {
    return new LoggingTraceWebfluxFilter(traceHeaderProxy);
  }
}

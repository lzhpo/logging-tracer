package com.lzhpo.logging.trace.servlet;

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
@ConditionalOnWebApplication(type = Type.SERVLET)
public class LoggingTraceServletAutoConfiguration {

  @Bean
  public LoggingTraceServletMvcConfigurer loggingTraceMvcConfigurer(
      LoggingTraceHeaderProxy traceHeaderProxy) {
    return new LoggingTraceServletMvcConfigurer(traceHeaderProxy);
  }
}

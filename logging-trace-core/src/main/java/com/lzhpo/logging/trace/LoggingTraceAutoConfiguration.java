package com.lzhpo.logging.trace;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Trace auto configure
 *
 * @author lzhpo
 */
@Configuration
@Conditional({LoggingTraceCondition.class})
@EnableConfigurationProperties({LoggingTraceProperties.class})
public class LoggingTraceAutoConfiguration {

  @Bean
  public LoggingTraceHeaderProxy loggingTraceHeaderProxy(LoggingTraceProperties traceProperties) {
    return new LoggingTraceHeaderProxy(traceProperties);
  }
}

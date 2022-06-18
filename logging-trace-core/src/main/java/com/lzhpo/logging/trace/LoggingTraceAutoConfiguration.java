package com.lzhpo.logging.trace;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Trace auto configure
 *
 * @author lzhpo
 */
@Configuration
@ConditionalOnProperty(
    prefix = "logging.trace",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Import({LoggingTraceWebMvcConfig.class})
@EnableConfigurationProperties({LoggingTraceProperties.class})
public class LoggingTraceAutoConfiguration {

  @Bean
  public LoggingTraceHeaderProxy loggingTraceHeaderProxy(LoggingTraceProperties traceProperties) {
    return new LoggingTraceHeaderProxy(traceProperties);
  }
}

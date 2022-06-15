package com.lzhpo.logging.trace;

import com.lzhpo.logging.trace.handler.DefaultLoggingTraceContextHandler;
import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@Import({LoggingTraceWebMvcConfigurer.class})
@EnableConfigurationProperties({LoggingTraceProperties.class})
public class LoggingTraceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean({LoggingTraceContextHandler.class})
  public LoggingTraceContextHandler loggingTraceContextHandler() {
    return new DefaultLoggingTraceContextHandler();
  }
}

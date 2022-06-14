package com.lzhpo.logging.trace;

import com.lzhpo.logging.trace.content.DefaultLoggingTraceContextCustomizer;
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
  public DefaultLoggingTraceContextCustomizer defaultLoggingTraceContextCustomizer() {
    return new DefaultLoggingTraceContextCustomizer();
  }
}

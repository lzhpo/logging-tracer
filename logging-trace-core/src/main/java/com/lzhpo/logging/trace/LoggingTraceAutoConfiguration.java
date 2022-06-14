package com.lzhpo.logging.trace;

import com.lzhpo.logging.trace.context.DefaultLoggingTraceContextHandler;
import com.lzhpo.logging.trace.context.LoggingTraceContextFactory;
import com.lzhpo.logging.trace.context.LoggingTraceContextHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@Import({LoggingTraceWebMvcConfigurer.class, DefaultLoggingTraceContextHandler.class})
@EnableConfigurationProperties({LoggingTraceProperties.class})
public class LoggingTraceAutoConfiguration {

  private List<LoggingTraceContextHandler> loggingTraceContentHandlers;

  @Autowired(required = false)
  public void setLoggingTraceContentHandlers(
      List<LoggingTraceContextHandler> loggingTraceContentHandlers) {
    this.loggingTraceContentHandlers = loggingTraceContentHandlers;
  }

  @Bean
  public LoggingTraceContextFactory loggingTraceContextFactory() {
    LoggingTraceContextFactory loggingTraceContextFactory = new LoggingTraceContextFactory();
    loggingTraceContextFactory.setLoggingTraceContentHandlers(loggingTraceContentHandlers);
    return loggingTraceContextFactory;
  }
}

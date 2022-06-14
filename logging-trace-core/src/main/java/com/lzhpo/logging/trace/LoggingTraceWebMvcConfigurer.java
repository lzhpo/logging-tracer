package com.lzhpo.logging.trace;

import com.lzhpo.logging.trace.context.LoggingTraceContextHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
public class LoggingTraceWebMvcConfigurer implements WebMvcConfigurer {

  private final LoggingTraceProperties loggingTraceProperties;
  private List<LoggingTraceContextHandler> loggingTraceContentHandlers;

  @Autowired(required = false)
  public void setLoggingTraceContextCustomizers(
      List<LoggingTraceContextHandler> loggingTraceContentHandlers) {
    this.loggingTraceContentHandlers = loggingTraceContentHandlers;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(
        new LoggingTraceHandlerInterceptor(loggingTraceProperties, loggingTraceContentHandlers));
  }
}

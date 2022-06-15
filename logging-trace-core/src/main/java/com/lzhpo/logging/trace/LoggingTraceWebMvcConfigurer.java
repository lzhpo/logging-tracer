package com.lzhpo.logging.trace;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
public class LoggingTraceWebMvcConfigurer implements WebMvcConfigurer {

  private final LoggingTraceProperties traceProperties;
  private final LoggingTraceContextHandler traceContextHandler;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(
        new LoggingTraceHandlerInterceptor(traceProperties, traceContextHandler));
  }
}

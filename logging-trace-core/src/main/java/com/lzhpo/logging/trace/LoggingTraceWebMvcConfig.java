package com.lzhpo.logging.trace;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
public class LoggingTraceWebMvcConfig implements WebMvcConfigurer {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoggingTraceHandlerInterceptor(traceHeaderProxy));
  }
}

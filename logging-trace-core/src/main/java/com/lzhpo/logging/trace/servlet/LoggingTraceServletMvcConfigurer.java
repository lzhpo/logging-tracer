package com.lzhpo.logging.trace.servlet;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceServletMvcConfigurer implements WebMvcConfigurer {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoggingTraceServletInterceptor(traceHeaderProxy));
  }
}

package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.context.LoggingTraceContextFactory;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class LoggingTraceFeignRequestInterceptor implements RequestInterceptor {

  private final LoggingTraceContextFactory loggingTraceContextFactory;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Map<String, String> loggingTraceContextMap =
        loggingTraceContextFactory.buildAdapterContextMap();
    loggingTraceContextMap.forEach(requestTemplate::header);
  }
}

package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class LoggingTraceFeignRequestInterceptor implements RequestInterceptor {

  private final LoggingTraceContextHandler traceContextHandler;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Map<String, String> contextMap = traceContextHandler.buildAdapterContextMap();
    if (!ObjectUtils.isEmpty(contextMap)) {
      contextMap.forEach(requestTemplate::header);
    }
  }
}

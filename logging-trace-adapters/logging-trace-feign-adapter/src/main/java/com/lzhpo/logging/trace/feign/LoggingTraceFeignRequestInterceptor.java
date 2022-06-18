package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
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

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Map<String, String> proxyHeaderMap = traceHeaderProxy.buildProxyHeaderMap();
    proxyHeaderMap.forEach(requestTemplate::header);
  }
}

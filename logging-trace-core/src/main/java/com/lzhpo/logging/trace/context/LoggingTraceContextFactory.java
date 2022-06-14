package com.lzhpo.logging.trace.context;

import java.util.List;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

/**
 * @author lzhpo
 */
@Slf4j
@Setter
public class LoggingTraceContextFactory {

  private List<LoggingTraceContextHandler> loggingTraceContentHandlers;

  public Map<String, String> buildAdapterContextMap() {
    Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
    if (!CollectionUtils.isEmpty(loggingTraceContentHandlers)) {
      loggingTraceContentHandlers.forEach(
          handler -> handler.beforeAdapterRequest(copyOfContextMap));
    }
    return copyOfContextMap;
  }
}

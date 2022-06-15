package com.lzhpo.logging.trace.handler;

import java.util.Map;
import org.slf4j.MDC;

/**
 * @author lzhpo
 */
public interface LoggingTraceContextHandler {

  /**
   * Custom handle contextMap when server received new request
   *
   * @param contextMap contextMap
   */
  default void whenReceivedRequest(Map<String, String> contextMap) {
    // NOP
  }

  /**
   * Custom handle contextMap when adapter server to request new service
   *
   * @param contextMap contextMap
   */
  default void beforeAdapterRequest(Map<String, String> contextMap) {
    // NOP
  }

  /**
   * Build adapter contextMap before to request new service
   *
   * @return contextMap
   */
  default Map<String, String> buildAdapterContextMap() {
    Map<String, String> contextMap = MDC.getCopyOfContextMap();
    beforeAdapterRequest(contextMap);
    return contextMap;
  }
}

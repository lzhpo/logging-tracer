package com.lzhpo.logging.trace.context;

import java.util.Map;

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
}

package com.lzhpo.logging.trace.content;

import java.util.Map;

/**
 * @author lzhpo
 */
public interface LoggingTraceContentHandler {

  void fill(Map<String, String> contentMap);
}

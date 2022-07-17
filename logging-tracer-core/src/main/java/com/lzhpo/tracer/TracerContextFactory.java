package com.lzhpo.tracer;

import java.util.Map;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * @author lzhpo
 */
public interface TracerContextFactory {

  /**
   * Fill context.
   *
   * @param headers headers
   * @return context
   */
  LinkedCaseInsensitiveMap<String> fillContext(Map<String, String> headers);

  /**
   * Build context.
   *
   * @return Ignore case proxy header
   */
  Map<String, String> getContext();
}

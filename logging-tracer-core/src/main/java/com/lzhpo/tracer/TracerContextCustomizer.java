package com.lzhpo.tracer;

import java.util.Map;

/**
 * @author lzhpo
 */
public interface TracerContextCustomizer {

  /**
   * Customize context.
   *
   * @param context context
   */
  void customize(Map<String, String> context);
}

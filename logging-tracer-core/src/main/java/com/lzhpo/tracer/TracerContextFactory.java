package com.lzhpo.tracer;

import java.util.Map;

/**
 * @author lzhpo
 */
public interface TracerContextFactory {

  /**
   * Fill context.
   *
   * @param context context
   */
  void fillContext(Map<String, String> context);

  /**
   * Build context.
   *
   * @return Ignore case proxy header
   */
  Map<String, String> buildContext();
}

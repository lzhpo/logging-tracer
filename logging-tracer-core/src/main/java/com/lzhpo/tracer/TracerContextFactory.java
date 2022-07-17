package com.lzhpo.tracer;

import java.util.Map;

/**
 * @author lzhpo
 */
public interface TracerContextFactory {

  /**
   * Set tracer context.
   *
   * @param context tracer context
   */
  void setContext(Map<String, String> context);

  /**
   * Get tracer context.
   *
   * @return tracer context
   */
  Map<String, String> getContext();

  /** Clear tracer context. */
  void clearContext();
}

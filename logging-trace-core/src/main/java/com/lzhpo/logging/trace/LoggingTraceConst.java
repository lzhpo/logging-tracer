package com.lzhpo.logging.trace;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <a
 * href="https://github.com/openzipkin/b3-propagation">https://github.com/openzipkin/b3-propagation</a>
 *
 * @author lzhpo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingTraceConst {

  public static String X_B3_TRACE_ID = "X-B3-TraceId";

  public static String X_B3_SPAN_ID = "X-B3-SpanId";

  public static String X_B3_SPAN_NAME = "X-B3-SpanName";

  public static String X_B3_PARENT_SPAN_NAME = "X-B3-Parent-SpanName";
}

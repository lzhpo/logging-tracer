package com.lzhpo.logging.trace.content;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <a
 * href="https://github.com/openzipkin/b3-propagation">https://github.com/openzipkin/b3-propagation</a>
 *
 * @author lzhpo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Xb3Const {

  public static String TRACE_ID = "X-B3-TraceId";

  public static String SPAN_ID = "X-B3-SpanId";

  public static String SPAN_NAME = "X-B3-SpanName";

  public static String PARENT_SPAN_NAME = "X-B3-Parent-SpanName";
}

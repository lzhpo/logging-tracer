package com.lzhpo.logging.trace;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public final class LoggingTraceHeaderProxy {
  private final LoggingTraceProperties traceProperties;

  /**
   * Fill MDC content when received request.
   *
   * @param requestHeaderMap request header map
   */
  public void fillMdcWhenReceivedRequest(Map<String, String> requestHeaderMap) {
    MDC.put(LoggingTraceConst.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
    List<String> proxyHeaders = traceProperties.getProxyHeaders();
    proxyHeaders.forEach(proxyHeader -> MDC.put(proxyHeader, requestHeaderMap.get(proxyHeader)));
    String traceId = requestHeaderMap.get(LoggingTraceConst.X_B3_TRACE_ID);

    if (StringUtils.hasText(traceId)) {
      String spanId = requestHeaderMap.get(LoggingTraceConst.X_B3_SPAN_ID);
      String asParentSpanName = requestHeaderMap.get(LoggingTraceConst.X_B3_SPAN_NAME);

      MDC.put(LoggingTraceConst.X_B3_PARENT_SPAN_NAME, asParentSpanName);
      MDC.put(LoggingTraceConst.X_B3_TRACE_ID, traceId);
      MDC.put(LoggingTraceConst.X_B3_SPAN_ID, spanId + StrPool.DOT + 1);
    } else {
      MDC.put(LoggingTraceConst.X_B3_PARENT_SPAN_NAME, "this");
      MDC.put(LoggingTraceConst.X_B3_TRACE_ID, IdUtil.fastSimpleUUID());
      MDC.put(LoggingTraceConst.X_B3_SPAN_ID, "0");
    }
  }

  /**
   * Build proxy header.
   *
   * @return Ignore case proxy header
   */
  public Map<String, String> buildProxyHeaderMap() {
    List<String> proxyHeaders = traceProperties.getProxyHeaders();
    Map<String, String> proxyHeaderMap = new LinkedCaseInsensitiveMap<>(proxyHeaders.size() + 4);
    proxyHeaders.forEach(headerName -> proxyHeaderMap.put(headerName, MDC.get(headerName)));

    proxyHeaderMap.put(LoggingTraceConst.X_B3_TRACE_ID, MDC.get(LoggingTraceConst.X_B3_TRACE_ID));
    proxyHeaderMap.put(LoggingTraceConst.X_B3_SPAN_ID, MDC.get(LoggingTraceConst.X_B3_SPAN_ID));
    proxyHeaderMap.put(LoggingTraceConst.X_B3_SPAN_NAME, MDC.get(LoggingTraceConst.X_B3_SPAN_NAME));
    proxyHeaderMap.put(
        LoggingTraceConst.X_B3_PARENT_SPAN_NAME, MDC.get(LoggingTraceConst.X_B3_PARENT_SPAN_NAME));
    return proxyHeaderMap;
  }
}

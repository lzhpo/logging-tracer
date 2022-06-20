package com.lzhpo.logging.trace.servlet;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceServletInterceptor implements HandlerInterceptor {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String, String> requestHeaderMap = new LinkedCaseInsensitiveMap<>(16);
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      requestHeaderMap.put(headerName, request.getHeader(headerName));
    }
    traceHeaderProxy.fillMdcWhenReceivedRequest(requestHeaderMap);
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    MDC.clear();
  }
}

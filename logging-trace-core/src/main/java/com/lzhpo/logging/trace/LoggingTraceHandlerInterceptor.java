package com.lzhpo.logging.trace;

import com.lzhpo.logging.trace.context.LoggingTraceContextHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceHandlerInterceptor implements HandlerInterceptor {

  private final LoggingTraceProperties loggingTraceProperties;
  private final List<LoggingTraceContextHandler> loggingTraceContentHandlers;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    List<String> headers = loggingTraceProperties.getHeaders();
    if (!CollectionUtils.isEmpty(headers)) {
      Map<String, String> contextMap = new HashMap<>(headers.size());
      headers.forEach(header -> contextMap.put(header, request.getHeader(header)));

      if (!CollectionUtils.isEmpty(loggingTraceContentHandlers)) {
        loggingTraceContentHandlers.forEach(
            contextHandler -> contextHandler.whenReceivedRequest(contextMap));
      }

      contextMap.forEach(MDC::put);
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {}

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    MDC.clear();
  }
}

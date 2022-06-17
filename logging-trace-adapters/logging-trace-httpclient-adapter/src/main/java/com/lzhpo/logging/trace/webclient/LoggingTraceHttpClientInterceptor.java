package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.util.ObjectUtils;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceHttpClientInterceptor implements HttpRequestInterceptor {

  private final LoggingTraceContextHandler traceContextHandler;

  @Override
  public void process(
      HttpRequest httpRequest, EntityDetails entityDetails, HttpContext httpContext) {
    Map<String, String> contextMap = traceContextHandler.buildAdapterContextMap();
    if (!ObjectUtils.isEmpty(contextMap)) {
      contextMap.forEach(httpRequest::addHeader);
    }
  }
}

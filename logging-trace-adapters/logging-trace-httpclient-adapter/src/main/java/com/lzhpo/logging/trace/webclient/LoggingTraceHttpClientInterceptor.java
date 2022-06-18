package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceHttpClientInterceptor implements HttpRequestInterceptor {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public void process(
      HttpRequest httpRequest, EntityDetails entityDetails, HttpContext httpContext) {
    Map<String, String> proxyHeaderMap = traceHeaderProxy.buildProxyHeaderMap();
    proxyHeaderMap.forEach(httpRequest::addHeader);
  }
}

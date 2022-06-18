package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceRestTemplateInterceptor implements ClientHttpRequestInterceptor {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    HttpHeaders requestHeaders = request.getHeaders();
    Map<String, String> proxyHeaderMap = traceHeaderProxy.buildProxyHeaderMap();
    proxyHeaderMap.forEach(requestHeaders::add);
    return execution.execute(request, body);
  }
}

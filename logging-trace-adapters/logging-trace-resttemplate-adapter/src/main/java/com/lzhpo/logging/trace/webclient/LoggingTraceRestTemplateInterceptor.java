package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.ObjectUtils;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceRestTemplateInterceptor implements ClientHttpRequestInterceptor {

  private final LoggingTraceContextHandler traceContextHandler;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    HttpHeaders requestHeaders = request.getHeaders();
    Map<String, String> contextMap = traceContextHandler.buildAdapterContextMap();
    if (!ObjectUtils.isEmpty(contextMap)) {
      contextMap.forEach(requestHeaders::add);
    }
    return execution.execute(request, body);
  }
}

package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceExchangeFilterFunction implements ExchangeFilterFunction {

  private final LoggingTraceContextHandler traceContextHandler;

  @Override
  public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
    Map<String, String> contextMap = traceContextHandler.buildAdapterContextMap();
    HttpHeaders headers = request.headers();
    if (!ObjectUtils.isEmpty(contextMap)) {
      contextMap.forEach(headers::add);
    }
    return next.exchange(request);
  }
}

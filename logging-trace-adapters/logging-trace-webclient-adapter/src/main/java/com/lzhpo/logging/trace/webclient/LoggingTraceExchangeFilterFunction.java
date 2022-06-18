package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
    Map<String, String> proxyHeaderMap = traceHeaderProxy.buildProxyHeaderMap();
    HttpHeaders headers = request.headers();
    proxyHeaderMap.forEach(headers::add);
    return next.exchange(request);
  }
}

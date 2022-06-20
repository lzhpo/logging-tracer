package com.lzhpo.logging.trace.webflux;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceWebfluxFilter implements WebFilter {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    HttpHeaders headers = request.getHeaders();
    Map<String, String> requestHeaderMap = headers.toSingleValueMap();
    traceHeaderProxy.fillMdcWhenReceivedRequest(requestHeaderMap);
    return chain.filter(exchange).doFinally(x -> MDC.clear());
  }
}

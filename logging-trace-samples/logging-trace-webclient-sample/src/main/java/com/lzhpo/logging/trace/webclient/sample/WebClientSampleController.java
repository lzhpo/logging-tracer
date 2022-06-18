package com.lzhpo.logging.trace.webclient.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author lzhpo
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class WebClientSampleController {

  private final WebClient webClient;

  @GetMapping("/callHello")
  public Mono<String> callHello(ServerHttpRequest request) {
    log.info("Received new request for callHello api.");

    HttpHeaders headers = request.getHeaders();
    headers.forEach((k, v) -> log.info("Request header with [{}: {}]", k, v));

    return webClient
        .get()
        .uri("http://127.0.0.1:9002/callHello")
        .retrieve()
        .bodyToMono(String.class);
  }
}

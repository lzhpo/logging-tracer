/*
 * Copyright 2022 lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzhpo.tracer.webclient.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/** @author lzhpo */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class WebClientSampleController {

  private final WebClient webClient;

  @GetMapping("/hello")
  public Mono<String> hello(ServerHttpRequest request) {
    log.info("Received new request for hello api.");
    HttpHeaders headers = request.getHeaders();
    headers.forEach((k, v) -> log.info("Request header with [{}: {}]", k, v));
    return webClient.get().uri("http://127.0.0.1:9000/hello").retrieve().bodyToMono(String.class);
  }
}

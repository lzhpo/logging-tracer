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

package com.lzhpo.tracer.sample.feign;

import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author lzhpo
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RestTemplateSampleController {

  private final RestTemplate restTemplate;
  private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @GetMapping("/hello")
  public String hello(HttpServletRequest request) {
    log.info("Received new request for hello api.");

    // Test multiThread environment
    async();
    threadPool();
    multiThread();

    LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>(16);
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = request.getHeader(headerName);
      log.info("Request header with [{}: {}]", headerName, headerValue);
      headers.put(headerName, Collections.singletonList(headerValue));
    }

    URI uri = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8004/hello").build().toUri();
    HttpEntity<Object> entity = new HttpEntity<>(null, headers);
    ResponseEntity<String> response =
        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    return response.getBody();
  }

  @Async
  public void async() {
    log.info("[async] Hello, I'm async.");
  }

  private void threadPool() {
    threadPoolTaskExecutor.execute(() -> log.info("[threadPool] Hello, I'm threadPool."));
  }

  private void multiThread() {
    Map<String, String> context = MDC.getCopyOfContextMap();
    Thread thread =
        new Thread(
            () -> {
              MDC.setContextMap(context);
              log.info("[multiThread] Hello, I'm multiThread.");
            });
    thread.start();
  }
}

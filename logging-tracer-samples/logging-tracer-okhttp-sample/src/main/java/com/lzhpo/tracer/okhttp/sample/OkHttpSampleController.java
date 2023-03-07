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

package com.lzhpo.tracer.okhttp.sample;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author lzhpo */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OkHttpSampleController {

  private final OkHttpClient.Builder okHttpClientBuilder;

  @GetMapping("/hello")
  public String hello(HttpServletRequest request) throws IOException {
    log.info("Received new request for hello api.");

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      log.info("Request header with [{}: {}]", headerName, request.getHeader(headerName));
    }

    Request okHttpRequest = new Request.Builder().url("http://127.0.0.1:8003/hello").get().build();
    OkHttpClient okHttpClient = okHttpClientBuilder.build();
    try (Response response = okHttpClient.newCall(okHttpRequest).execute()) {
      return Objects.requireNonNull(response.body()).string();
    }
  }
}

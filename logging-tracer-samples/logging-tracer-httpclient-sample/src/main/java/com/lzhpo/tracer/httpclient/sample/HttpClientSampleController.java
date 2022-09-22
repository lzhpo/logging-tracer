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

package com.lzhpo.tracer.httpclient.sample;

import com.lzhpo.tracer.httpclient.TracerHttpClients;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author lzhpo */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HttpClientSampleController {

  private final TracerHttpClients tracerHttpClients;

  @GetMapping("/hello")
  public String hello(HttpServletRequest request) throws IOException, ParseException {
    log.info("Received new request for hello api.");

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      log.info("Request header with [{}: {}]", headerName, request.getHeader(headerName));
    }

    HttpGet httpGet = new HttpGet("http://127.0.0.1:8002/hello");
    CloseableHttpResponse response;
    try (CloseableHttpClient closeableHttpClient = tracerHttpClients.createDefault()) {
      response = closeableHttpClient.execute(httpGet);
    }
    return EntityUtils.toString(response.getEntity());
  }
}

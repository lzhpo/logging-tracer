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

package com.lzhpo.tracer.httpclient;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.classic.MinimalHttpClient;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;

/**
 * @see HttpClients
 * @author lzhpo
 */
@RequiredArgsConstructor
public class TracerHttpClients {

  private final TracerHttpClientInterceptor tracerInterceptor;

  /**
   * Creates a builder object for construction of custom.
   *
   * @return {@link HttpClientBuilder}
   */
  public HttpClientBuilder custom() {
    return HttpClientBuilder.create().addRequestInterceptorFirst(tracerInterceptor);
  }

  /**
   * Creates {@link CloseableHttpClient} instance with default configuration.
   *
   * @return {@link CloseableHttpClient}
   */
  public CloseableHttpClient createDefault() {
    return HttpClientBuilder.create().addRequestInterceptorFirst(tracerInterceptor).build();
  }

  /**
   * Creates {@link CloseableHttpClient} instance with default configuration based on system
   * properties.
   *
   * @return {@link CloseableHttpClient}
   */
  public CloseableHttpClient createSystem() {
    return HttpClientBuilder.create()
        .addRequestInterceptorFirst(tracerInterceptor)
        .useSystemProperties()
        .build();
  }

  /**
   * Creates {@link CloseableHttpClient} instance that implements the most basic HTTP protocol
   * support.
   *
   * @return proxy {@link MinimalHttpClient}
   */
  public ProxyMinimalHttpClient createMinimal() {
    MinimalHttpClient minimalHttpClient = HttpClients.createMinimal();
    return new ProxyMinimalHttpClient(
        tracerInterceptor.getTracerContextFactory(), minimalHttpClient);
  }

  /**
   * Creates {@link CloseableHttpClient} instance that implements the most basic HTTP protocol
   * support.
   *
   * @return proxy {@link MinimalHttpClient}
   */
  public ProxyMinimalHttpClient createMinimal(final HttpClientConnectionManager connManager) {
    MinimalHttpClient minimalHttpClient = HttpClients.createMinimal(connManager);
    return new ProxyMinimalHttpClient(
        tracerInterceptor.getTracerContextFactory(), minimalHttpClient);
  }
}

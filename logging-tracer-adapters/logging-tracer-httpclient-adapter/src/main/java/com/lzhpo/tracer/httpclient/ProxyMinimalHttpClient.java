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

import com.lzhpo.tracer.TracerContextFactory;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.MinimalHttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.io.CloseMode;

/** @author lzhpo */
@RequiredArgsConstructor
public class ProxyMinimalHttpClient extends CloseableHttpClient implements InjectTracerContext {

  private final TracerContextFactory tracerContextFactory;
  private final MinimalHttpClient minimalHttpClient;

  @Override
  protected CloseableHttpResponse doExecute(
      HttpHost target, ClassicHttpRequest request, HttpContext context) throws IOException {
    inject(tracerContextFactory, request);
    return minimalHttpClient.execute(target, request, context);
  }

  @Override
  public void close(CloseMode closeMode) {
    minimalHttpClient.close(closeMode);
  }

  @Override
  public void close() throws IOException {
    minimalHttpClient.close();
  }
}

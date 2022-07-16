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

package com.lzhpo.logging.trace.okhttp;

import com.lzhpo.logging.trace.TracerHeaderProxy;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class TracerOkHttpInterceptor implements Interceptor {

  private final TracerHeaderProxy traceHeaderProxy;

  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {
    Builder builder = chain.request().newBuilder();
    Map<String, String> proxyHeaderMap = traceHeaderProxy.buildProxyHeaderMap();
    proxyHeaderMap.forEach(builder::addHeader);
    Request newRequest = builder.build();
    return chain.proceed(newRequest);
  }
}

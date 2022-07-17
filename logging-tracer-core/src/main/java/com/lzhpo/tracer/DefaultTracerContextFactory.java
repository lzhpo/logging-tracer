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

package com.lzhpo.tracer;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public final class DefaultTracerContextFactory implements TracerContextFactory {

  private final TracerProperties traceProperties;

  @Override
  public LinkedCaseInsensitiveMap<String> fillContext(Map<String, String> headers) {
    LinkedCaseInsensitiveMap<String> context = new LinkedCaseInsensitiveMap<>();

    context.put(TracerConstants.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
    List<String> proxyHeaders = traceProperties.getProxyHeaders();
    proxyHeaders.forEach(proxyHeader -> context.put(proxyHeader, headers.get(proxyHeader)));
    String traceId = headers.get(TracerConstants.X_B3_TRACE_ID);

    if (StringUtils.hasText(traceId)) {
      String spanId = headers.get(TracerConstants.X_B3_SPAN_ID);
      String asParentSpanName = headers.get(TracerConstants.X_B3_SPAN_NAME);
      context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, asParentSpanName);
      context.put(TracerConstants.X_B3_TRACE_ID, traceId);
      context.put(TracerConstants.X_B3_SPAN_ID, spanId + StrPool.DOT + 1);
    } else {
      context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, "N/A");
      context.put(TracerConstants.X_B3_TRACE_ID, IdUtil.fastSimpleUUID());
      context.put(TracerConstants.X_B3_SPAN_ID, "0");
    }

    return context;
  }

  @Override
  public Map<String, String> getContext() {
    List<String> proxyHeaders = traceProperties.getProxyHeaders();
    Map<String, String> context = new LinkedCaseInsensitiveMap<>(proxyHeaders.size() + 4);
    proxyHeaders.forEach(headerName -> context.put(headerName, MDC.get(headerName)));

    context.put(TracerConstants.X_B3_TRACE_ID, MDC.get(TracerConstants.X_B3_TRACE_ID));
    context.put(TracerConstants.X_B3_SPAN_ID, MDC.get(TracerConstants.X_B3_SPAN_ID));
    context.put(TracerConstants.X_B3_SPAN_NAME, MDC.get(TracerConstants.X_B3_SPAN_NAME));
    context.put(
        TracerConstants.X_B3_PARENT_SPAN_NAME, MDC.get(TracerConstants.X_B3_PARENT_SPAN_NAME));
    return context;
  }
}

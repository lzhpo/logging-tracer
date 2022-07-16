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

package com.lzhpo.logging.trace;

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
public final class LoggingTraceHeaderProxy {

  private final TracerProperties traceProperties;

  /**
   * Fill MDC content when received request.
   *
   * @param requestHeaderMap request header map
   */
  public void fillMdcContext(Map<String, String> requestHeaderMap) {
    MDC.put(TracerConstants.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
    List<String> proxyHeaders = traceProperties.getProxyHeaders();
    proxyHeaders.forEach(proxyHeader -> MDC.put(proxyHeader, requestHeaderMap.get(proxyHeader)));
    String traceId = requestHeaderMap.get(TracerConstants.X_B3_TRACE_ID);

    if (StringUtils.hasText(traceId)) {
      String spanId = requestHeaderMap.get(TracerConstants.X_B3_SPAN_ID);
      String asParentSpanName = requestHeaderMap.get(TracerConstants.X_B3_SPAN_NAME);

      MDC.put(TracerConstants.X_B3_PARENT_SPAN_NAME, asParentSpanName);
      MDC.put(TracerConstants.X_B3_TRACE_ID, traceId);
      MDC.put(TracerConstants.X_B3_SPAN_ID, spanId + StrPool.DOT + 1);
    } else {
      MDC.put(TracerConstants.X_B3_PARENT_SPAN_NAME, "N/A");
      MDC.put(TracerConstants.X_B3_TRACE_ID, IdUtil.fastSimpleUUID());
      MDC.put(TracerConstants.X_B3_SPAN_ID, "0");
    }
  }

  /**
   * Build proxy header.
   *
   * @return Ignore case proxy header
   */
  public Map<String, String> buildProxyHeaderMap() {
    List<String> proxyHeaders = traceProperties.getProxyHeaders();
    Map<String, String> proxyHeaderMap = new LinkedCaseInsensitiveMap<>(proxyHeaders.size() + 4);
    proxyHeaders.forEach(headerName -> proxyHeaderMap.put(headerName, MDC.get(headerName)));

    proxyHeaderMap.put(TracerConstants.X_B3_TRACE_ID, MDC.get(TracerConstants.X_B3_TRACE_ID));
    proxyHeaderMap.put(TracerConstants.X_B3_SPAN_ID, MDC.get(TracerConstants.X_B3_SPAN_ID));
    proxyHeaderMap.put(TracerConstants.X_B3_SPAN_NAME, MDC.get(TracerConstants.X_B3_SPAN_NAME));
    proxyHeaderMap.put(
        TracerConstants.X_B3_PARENT_SPAN_NAME, MDC.get(TracerConstants.X_B3_PARENT_SPAN_NAME));
    return proxyHeaderMap;
  }
}

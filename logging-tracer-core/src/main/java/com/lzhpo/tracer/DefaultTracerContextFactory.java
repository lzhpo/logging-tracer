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

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/** @author lzhpo */
@Slf4j
@RequiredArgsConstructor
public class DefaultTracerContextFactory implements TracerContextFactory {

  private final List<TracerContextCustomizer> tracerContextCustomizers;

  @Override
  public void setContext(Map<String, String> context) {
    String traceId = context.get(TracerConstants.X_B3_TRACE_ID);

    if (StringUtils.hasText(traceId)) {
      String spanId = context.get(TracerConstants.X_B3_SPAN_ID);
      String asParentSpanName =
          context.getOrDefault(TracerConstants.X_B3_SPAN_NAME, TracerConstants.N_A);
      context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, asParentSpanName);
      context.put(TracerConstants.X_B3_TRACE_ID, traceId);
      context.put(TracerConstants.X_B3_SPAN_ID, spanId + StrPool.DOT + 1);
    } else {
      context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, TracerConstants.N_A);
      context.put(TracerConstants.X_B3_TRACE_ID, IdUtil.fastSimpleUUID());
      context.put(TracerConstants.X_B3_SPAN_ID, "0");
    }

    context.put(TracerConstants.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
    if (!ObjectUtils.isEmpty(tracerContextCustomizers)) {
      tracerContextCustomizers.forEach(customizer -> customizer.customize(context));
    }

    context.forEach(MDC::put);
  }

  @Override
  public Map<String, String> getContext() {
    Map<String, String> context =
        ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(), MapUtil.newHashMap());
    if (isEmptyContext(context)) {
      Map<String, String> tracerContext = createContext();
      context.putAll(tracerContext);
      MDC.setContextMap(context);
    }
    return context;
  }

  @Override
  public void clearContext() {
    MDC.clear();
  }
}

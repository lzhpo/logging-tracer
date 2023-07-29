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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultTracerContextFactory implements TracerContextFactory {

    private final TracerProperties tracerProperties;
    private final List<TracerContextCustomizer> contextCustomizers;

    @Override
    public void setContext(Map<String, String> context) {
        String traceId = context.get(TracerConstants.X_B3_TRACE_ID);
        if (StringUtils.hasText(traceId)) {
            String spanId = context.get(TracerConstants.X_B3_SPAN_ID);
            String asParentSpanName = context.getOrDefault(TracerConstants.X_B3_SPAN_NAME, TracerConstants.N_A);
            context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, asParentSpanName);
            context.put(TracerConstants.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
            context.put(TracerConstants.X_B3_TRACE_ID, traceId);
            context.put(TracerConstants.X_B3_SPAN_ID, spanId + StrPool.DOT + 1);
        } else {
            Map<String, String> defaultContext = createContext();
            context.putAll(defaultContext);
        }

        depositMDC(context, contextCustomizers);
        log.debug("Built logging tracer context: {}", context);
    }

    @Override
    public Map<String, String> getContext() {
        return ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(), () -> {
            Map<String, String> context = createContext();
            depositMDC(context, contextCustomizers);
            log.info("MDC context is empty, created default context {} in MDC.", context);
            return context;
        });
    }

    @Override
    public void clearContext() {
        MDC.remove(TracerConstants.X_B3_PARENT_SPAN_NAME);
        MDC.remove(TracerConstants.X_B3_SPAN_NAME);
        MDC.remove(TracerConstants.X_B3_TRACE_ID);
        MDC.remove(TracerConstants.X_B3_SPAN_ID);
        tracerProperties.getProxyHeaders().forEach(MDC::remove);
    }
}

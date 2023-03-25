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

package com.lzhpo.tracer.servlet;

import com.lzhpo.tracer.TracerContextFactory;
import com.lzhpo.tracer.TracerProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/** @author lzhpo */
@Slf4j
@RequiredArgsConstructor
public class TracerServletInterceptor implements HandlerInterceptor {

    private final TracerProperties tracerProperties;
    private final TracerContextFactory tracerContextFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        List<String> proxyHeaders = tracerProperties.getProxyHeaders();
        Map<String, String> context = new HashMap<>(proxyHeaders.size());
        proxyHeaders.forEach(headerName -> context.put(headerName, request.getHeader(headerName)));
        tracerContextFactory.setContext(context);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        tracerContextFactory.clearContext();
    }
}

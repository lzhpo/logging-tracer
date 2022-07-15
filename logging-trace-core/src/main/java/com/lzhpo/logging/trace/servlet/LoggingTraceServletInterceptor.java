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

package com.lzhpo.logging.trace.servlet;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceServletInterceptor implements HandlerInterceptor {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String, String> requestHeaderMap = new LinkedCaseInsensitiveMap<>(16);
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      requestHeaderMap.put(headerName, request.getHeader(headerName));
    }
    traceHeaderProxy.fillMdcWhenReceivedRequest(requestHeaderMap);
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    MDC.clear();
  }
}

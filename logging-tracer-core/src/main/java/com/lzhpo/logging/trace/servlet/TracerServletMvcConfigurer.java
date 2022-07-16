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

import com.lzhpo.logging.trace.TracerHeaderProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class TracerServletMvcConfigurer implements WebMvcConfigurer {

  private final TracerHeaderProxy traceHeaderProxy;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new TracerServletInterceptor(traceHeaderProxy));
  }
}

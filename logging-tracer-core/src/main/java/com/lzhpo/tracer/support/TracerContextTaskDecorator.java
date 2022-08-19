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

package com.lzhpo.tracer.support;

import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

/**
 * Maintain {@link MDC}'s context of child and parent thread task decorator.
 *
 * <p>If you want customize {@link TaskDecorator}, you must extends {@link
 * TracerContextTaskDecorator}
 *
 * @author lzhpo
 */
public class TracerContextTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(@NonNull Runnable runnable) {
    Map<String, String> context = MDC.getCopyOfContextMap();
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    return () -> {
      try {
        MDC.setContextMap(context);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        runnable.run();
      } finally {
        MDC.clear();
        RequestContextHolder.resetRequestAttributes();
      }
    };
  }
}

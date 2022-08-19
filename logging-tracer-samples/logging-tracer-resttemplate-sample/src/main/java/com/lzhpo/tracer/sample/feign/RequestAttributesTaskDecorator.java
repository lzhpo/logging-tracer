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

package com.lzhpo.tracer.sample.feign;

import lombok.NonNull;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Just in order to test {@link com.lzhpo.tracer.support.TracerTaskDecoratorDelegate}
 *
 * @author lzhpo
 */
@Component
public class RequestAttributesTaskDecorator implements TaskDecorator {

  @NonNull
  @Override
  public Runnable decorate(@NonNull Runnable runnable) {
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    return () -> {
      try {
        RequestContextHolder.setRequestAttributes(requestAttributes);
        runnable.run();
      } finally {
        RequestContextHolder.resetRequestAttributes();
      }
    };
  }
}

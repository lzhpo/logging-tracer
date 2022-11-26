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

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.ObjectUtils;

/** @author lzhpo */
public interface TracerContextFactory {

  /**
   * Set tracer context.
   *
   * @param context tracer context
   */
  void setContext(Map<String, String> context);

  /**
   * Get tracer context.
   *
   * @return tracer context
   */
  Map<String, String> getContext();

  /** Clear tracer context. */
  void clearContext();

  /**
   * Create initialized tracer context.
   *
   * @return context
   */
  default Map<String, String> createContext() {
    Map<String, String> context = new HashMap<>(4);
    context.put(TracerConstants.X_B3_TRACE_ID, IdUtil.fastSimpleUUID());
    context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, TracerConstants.N_A);
    context.put(TracerConstants.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
    context.put(TracerConstants.X_B3_SPAN_ID, "0");
    return context;
  }

  /**
   * Whether is an empty tracer context, that means not has any {@link TracerConstants} for this
   * {@code context}.
   *
   * @param context context
   * @return is an empty tracer context
   */
  default boolean isEmptyContext(Map<String, String> context) {
    if (ObjectUtils.isEmpty(context)) {
      return true;
    }
    return !context.containsKey(TracerConstants.X_B3_TRACE_ID)
        && !context.containsKey(TracerConstants.X_B3_SPAN_ID)
        && !context.containsKey(TracerConstants.X_B3_SPAN_NAME)
        && !context.containsKey(TracerConstants.X_B3_PARENT_SPAN_NAME);
  }
}

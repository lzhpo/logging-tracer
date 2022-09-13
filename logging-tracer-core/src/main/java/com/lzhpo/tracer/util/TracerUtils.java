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

package com.lzhpo.tracer.util;

import com.lzhpo.tracer.TracerConstants;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

/**
 * @author lzhpo
 */
@UtilityClass
public class TracerUtils {

  public static String getTraceId() {
    return MDC.get(TracerConstants.X_B3_TRACE_ID);
  }

  public static String getSpanId() {
    return MDC.get(TracerConstants.X_B3_SPAN_ID);
  }

  public static String getSpanName() {
    return MDC.get(TracerConstants.X_B3_SPAN_NAME);
  }

  public static String getParentSpanName() {
    return MDC.get(TracerConstants.X_B3_PARENT_SPAN_NAME);
  }
}

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

import cn.hutool.core.text.StrFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <a
 * href="https://github.com/openzipkin/b3-propagation">https://github.com/openzipkin/b3-propagation</a>
 *
 * @author lzhpo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TracerConstants {

    public static final String N_A = "N/A";

    public static final String ZERO = "0";

    public static final String X_B3_TRACE_ID = "X-B3-TraceId";

    public static final String X_B3_SPAN_ID = "X-B3-SpanId";

    public static final String X_B3_SPAN_NAME = "X-B3-SpanName";

    public static final String X_B3_PARENT_SPAN_NAME = "X-B3-Parent-SpanName";

    public static final String DEFAULT_PATTERN = StrFormatter.formatWith(
            "%5p [${spring.application.name:},%X{#},%X{#},%X{#}]",
            "#", X_B3_PARENT_SPAN_NAME, X_B3_TRACE_ID, X_B3_SPAN_ID);
}

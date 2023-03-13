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

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** @author lzhpo */
@Data
@ConfigurationProperties(prefix = "logging.tracer")
public class TracerProperties implements InitializingBean {

    /** Whether enable logging trace */
    private boolean enabled = true;

    /**
     * Console log pattern, also can configure "logging.pattern.level".
     *
     * <p>priority: "logging.tracer.pattern" > "logging.pattern.level"
     */
    private String pattern = TracerConstants.DEFAULT_PATTERN;

    /** Will proxy headers, ignore the case */
    private List<String> proxyHeaders = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        proxyHeaders.add(TracerConstants.X_B3_SPAN_NAME);
        proxyHeaders.add(TracerConstants.X_B3_PARENT_SPAN_NAME);
        proxyHeaders.add(TracerConstants.X_B3_TRACE_ID);
        proxyHeaders.add(TracerConstants.X_B3_SPAN_ID);
    }
}

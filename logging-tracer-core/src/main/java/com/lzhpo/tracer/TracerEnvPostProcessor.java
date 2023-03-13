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

import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

/**
 * Set custom log trace format style.
 *
 * <p>Prefer logging.pattern.level in the environment.
 *
 * <pre>
 * {@link org.springframework.boot.context.config.ConfigFileApplicationListener} deprecated.
 * since 2.4.0 for removal in 3.0.0 in favor of {@link ConfigDataEnvironmentPostProcessor}
 * </pre>
 *
 * @author lzhpo
 */
@Slf4j
public class TracerEnvPostProcessor implements EnvironmentPostProcessor, Ordered {

    /** Default value: "logging.pattern.level=%5p" */
    private static final String LEVEL_KEY = "logging.pattern.level";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        Boolean enabled = environment.getProperty("logging.tracer.enabled", Boolean.class, true);
        if (Boolean.FALSE.equals(enabled) || StringUtils.hasText(System.getProperty(LEVEL_KEY))) {
            return;
        }

        String pattern = environment.getProperty("logging.tracer.pattern", String.class);
        if (!StringUtils.hasText(pattern)) {
            pattern = environment.getProperty(LEVEL_KEY, TracerConstants.DEFAULT_PATTERN);
        }

        System.setProperty(LEVEL_KEY, pattern);
        Console.log("[logging-tracer] {} updated new value: {}", LEVEL_KEY, pattern);
    }

    @Override
    public int getOrder() {
        return ConfigDataEnvironmentPostProcessor.ORDER + 1;
    }
}

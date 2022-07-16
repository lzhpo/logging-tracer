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

package com.lzhpo.logging.trace;

import cn.hutool.core.lang.Console;
import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
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
 * {@link ConfigFileApplicationListener} deprecated.
 * since 2.4.0 for removal in 3.0.0 in favor of {@link ConfigDataEnvironmentPostProcessor}
 * </pre>
 *
 * @author lzhpo
 */
@Slf4j
public class TracerEnvPostProcessor implements EnvironmentPostProcessor, Ordered {

  private static final String LEVEL_KEY = "logging.pattern.level";

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {

    String enabled =
        environment.getProperty(TracerConstants.ENABLED_TRACE_KEY, Boolean.TRUE.toString());
    if (Boolean.parseBoolean(enabled)) {
      Console.log("Enabled logging trace.");

      String originalValue = environment.getProperty(LEVEL_KEY);
      Console.log("{} original value: {}", LEVEL_KEY, originalValue);

      // Prefer logging.pattern.level in the environment
      if (!StringUtils.hasText(originalValue)) {
        // Default configuration: logging.pattern.level=%5p
        String levelValue =
            StrFormatter.formatWith(
                "%5p [${spring.application.name},%X{#},%X{#},%X{#}]",
                "#",
                TracerConstants.X_B3_PARENT_SPAN_NAME,
                TracerConstants.X_B3_TRACE_ID,
                TracerConstants.X_B3_SPAN_ID);
        System.setProperty(LEVEL_KEY, levelValue);
        Console.log("{} updated value: {}", LEVEL_KEY, levelValue);
      }
    }
  }

  @Override
  public int getOrder() {
    return ConfigDataEnvironmentPostProcessor.ORDER + 1;
  }
}

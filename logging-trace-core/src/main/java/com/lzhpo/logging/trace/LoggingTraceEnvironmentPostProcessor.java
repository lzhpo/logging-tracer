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
 * <pre>
 * {@link ConfigFileApplicationListener} deprecated.
 * since 2.4.0 for removal in 3.0.0 in favor of {@link ConfigDataEnvironmentPostProcessor}
 * </pre>
 *
 * @author lzhpo
 */
@Slf4j
public class LoggingTraceEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

  private static final String LEVEL_KEY = "logging.pattern.level";

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    String originalValue = environment.getProperty(LEVEL_KEY);
    Console.log("{} original value: {}", LEVEL_KEY, originalValue);
    if (!StringUtils.hasText(originalValue)) {
      // Default configuration: logging.pattern.level=%5p
      String levelValue =
          StrFormatter.formatWith(
              "%5p [%X{#},%X{#},%X{#},%X{#}]",
              "#",
              Xb3Const.SPAN_NAME,
              Xb3Const.PARENT_SPAN_NAME,
              Xb3Const.TRACE_ID,
              Xb3Const.SPAN_ID);
      System.setProperty(LEVEL_KEY, levelValue);
      Console.log("{} updated value: {}", LEVEL_KEY, levelValue);
    }
  }

  @Override
  public int getOrder() {
    return ConfigDataEnvironmentPostProcessor.ORDER + 1;
  }
}

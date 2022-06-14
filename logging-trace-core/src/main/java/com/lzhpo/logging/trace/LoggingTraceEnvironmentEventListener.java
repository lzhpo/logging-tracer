package com.lzhpo.logging.trace;

import cn.hutool.core.text.StrFormatter;
import com.lzhpo.logging.trace.content.Xb3Const;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * @author lzhpo
 */
@Slf4j
@Configuration
public class LoggingTraceEnvironmentEventListener
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static final String LOGGING_TRACE_PROPERTIES = "loggingTraceProperties";
  private static final String PATTERN_LEVEL_KEY = "logging.pattern.level";

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    // Default configuration: logging.pattern.level=%5p
    String patternLevelValue =
        StrFormatter.formatWith(
            "%5p [${#},%X{#},%X{#},%X{#}]",
            "#",
            Xb3Const.PARENT_SPAN_NAME,
            Xb3Const.SPAN_NAME,
            Xb3Const.TRACE_ID,
            Xb3Const.SPAN_ID);

    Properties properties = new Properties();
    properties.put(PATTERN_LEVEL_KEY, patternLevelValue);

    ConfigurableEnvironment environment = event.getEnvironment();
    MutablePropertySources propertySources = environment.getPropertySources();
    propertySources.addFirst(new PropertiesPropertySource(LOGGING_TRACE_PROPERTIES, properties));
  }
}

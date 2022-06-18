package com.lzhpo.logging.trace;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author lzhpo
 */
public class LoggingTraceCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Environment environment = context.getEnvironment();
    String enabled =
        environment.getProperty(LoggingTraceConst.ENABLED_TRACE_KEY, Boolean.TRUE.toString());
    return Boolean.parseBoolean(enabled);
  }
}

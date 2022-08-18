package com.lzhpo.tracer.support;

import java.util.Map;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Maintain {@link MDC}'s context of child and parent thread task decorator.
 *
 * <p>If you want customize {@link TaskDecorator}, you must extends {@link
 * TracerContextTaskDecorator}
 *
 * @author lzhpo
 */
public class TracerContextTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(@NonNull Runnable runnable) {
    Map<String, String> context = MDC.getCopyOfContextMap();
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    return () -> {
      try {
        MDC.setContextMap(context);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        runnable.run();
      } finally {
        MDC.clear();
        RequestContextHolder.resetRequestAttributes();
      }
    };
  }
}

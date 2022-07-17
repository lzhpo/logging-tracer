package com.lzhpo.tracer;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultTracerContextFactory implements TracerContextFactory {

  private final List<TracerContextCustomizer> tracerContextCustomizers;

  @Override
  public void setContext(Map<String, String> context) {
    String traceId = context.get(TracerConstants.X_B3_TRACE_ID);

    if (StringUtils.hasText(traceId)) {
      String spanId = context.get(TracerConstants.X_B3_SPAN_ID);
      String asParentSpanName =
          context.getOrDefault(TracerConstants.X_B3_SPAN_NAME, TracerConstants.N_A);
      context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, asParentSpanName);
      context.put(TracerConstants.X_B3_TRACE_ID, traceId);
      context.put(TracerConstants.X_B3_SPAN_ID, spanId + StrPool.DOT + 1);
    } else {
      context.put(TracerConstants.X_B3_PARENT_SPAN_NAME, TracerConstants.N_A);
      context.put(TracerConstants.X_B3_TRACE_ID, IdUtil.fastSimpleUUID());
      context.put(TracerConstants.X_B3_SPAN_ID, "0");
    }

    context.put(TracerConstants.X_B3_SPAN_NAME, SpringUtil.getApplicationName());
    if (!ObjectUtils.isEmpty(tracerContextCustomizers)) {
      tracerContextCustomizers.forEach(customizer -> customizer.customize(context));
    }

    context.forEach(MDC::put);
  }

  @Override
  public Map<String, String> getContext() {
    return MDC.getCopyOfContextMap();
  }

  @Override
  public void clearContext() {
    MDC.clear();
  }
}

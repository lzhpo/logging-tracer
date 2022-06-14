package com.lzhpo.logging.trace.context;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author lzhpo
 */
@Component
public class DefaultLoggingTraceContextHandler implements LoggingTraceContextHandler {

  @Override
  public void whenReceivedRequest(Map<String, String> contextMap) {
    String traceId = contextMap.get(Xb3Const.TRACE_ID);
    if (StringUtils.hasText(traceId)) {
      String spanName = contextMap.get(Xb3Const.SPAN_NAME);
      contextMap.put(Xb3Const.SPAN_NAME, SpringUtil.getApplicationName());
      contextMap.put(Xb3Const.PARENT_SPAN_NAME, spanName);
    } else {
      contextMap.put(Xb3Const.TRACE_ID, IdUtil.fastSimpleUUID());
      contextMap.put(Xb3Const.SPAN_NAME, SpringUtil.getApplicationName());
      contextMap.put(Xb3Const.PARENT_SPAN_NAME, "this");
    }
  }

  @Override
  public void beforeAdapterRequest(Map<String, String> contextMap) {
    contextMap.put(Xb3Const.PARENT_SPAN_NAME, SpringUtil.getApplicationName());
  }
}

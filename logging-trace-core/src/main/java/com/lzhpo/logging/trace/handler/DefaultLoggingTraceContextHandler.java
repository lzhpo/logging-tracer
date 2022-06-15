package com.lzhpo.logging.trace.handler;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.logging.trace.Xb3Const;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 * @author lzhpo
 */
public class DefaultLoggingTraceContextHandler implements LoggingTraceContextHandler {

  @Override
  public void whenReceivedRequest(Map<String, String> contextMap) {
    String traceId = contextMap.get(Xb3Const.TRACE_ID);
    String spanId = contextMap.get(Xb3Const.SPAN_ID);

    if (StringUtils.hasText(traceId)) {
      String spanName = contextMap.get(Xb3Const.SPAN_NAME);
      contextMap.put(Xb3Const.SPAN_NAME, SpringUtil.getApplicationName());
      contextMap.put(Xb3Const.PARENT_SPAN_NAME, spanName);
      contextMap.put(Xb3Const.SPAN_ID, spanId + StrPool.DOT + 1);
    } else {
      contextMap.put(Xb3Const.TRACE_ID, IdUtil.fastSimpleUUID());
      contextMap.put(Xb3Const.SPAN_NAME, SpringUtil.getApplicationName());
      contextMap.put(Xb3Const.PARENT_SPAN_NAME, "this");
      contextMap.put(Xb3Const.SPAN_ID, "0");
    }
  }

  @Override
  public void beforeAdapterRequest(Map<String, String> contextMap) {
    contextMap.put(Xb3Const.PARENT_SPAN_NAME, SpringUtil.getApplicationName());
  }
}

package com.lzhpo.logging.trace.content;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.Map;

/**
 * @author lzhpo
 */
public class DefaultLoggingTraceContextCustomizer implements LoggingTraceContentHandler {

  @Override
  public void fill(Map<String, String> contentMap) {
    contentMap.putIfAbsent(Xb3Const.TRACE_ID, IdUtil.fastSimpleUUID());
    contentMap.putIfAbsent(Xb3Const.SPAN_NAME, SpringUtil.getApplicationName());
  }
}

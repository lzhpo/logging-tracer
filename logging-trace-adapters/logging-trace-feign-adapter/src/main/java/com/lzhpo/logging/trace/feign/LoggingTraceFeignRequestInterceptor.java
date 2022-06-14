package com.lzhpo.logging.trace.feign;

import com.lzhpo.logging.trace.content.LoggingTraceContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Map;

/**
 * @author lzhpo
 */
public class LoggingTraceFeignRequestInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Map<String, String> loggingTraceContextMap = LoggingTraceContextUtil.buildContextMap();
    loggingTraceContextMap.forEach(requestTemplate::header);
  }
}

package com.lzhpo.logging.trace.content;

import cn.hutool.extra.spring.SpringUtil;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

/**
 * @author lzhpo
 */
@UtilityClass
public class LoggingTraceContextUtil {

  public static Map<String, String> buildContextMap() {
    Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
    copyOfContextMap.put(Xb3Const.PARENT_SPAN_NAME, SpringUtil.getApplicationName());
    return copyOfContextMap;
  }
}

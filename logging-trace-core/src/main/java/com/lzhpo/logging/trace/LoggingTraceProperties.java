package com.lzhpo.logging.trace;

import cn.hutool.core.collection.ListUtil;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lzhpo
 */
@Data
@ConfigurationProperties(prefix = "logging.trace")
public class LoggingTraceProperties {

  private List<String> headers =
      ListUtil.of(
          Xb3Const.SPAN_NAME, Xb3Const.PARENT_SPAN_NAME, Xb3Const.TRACE_ID, Xb3Const.SPAN_ID);
}

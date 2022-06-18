package com.lzhpo.logging.trace;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lzhpo
 */
@Data
@ConfigurationProperties(prefix = "logging.trace")
public class LoggingTraceProperties {

  /** Whether enable logging trace */
  private boolean enabled = true;

  /** Will proxy headers, ignore case. */
  private List<String> proxyHeaders = new ArrayList<>();
}

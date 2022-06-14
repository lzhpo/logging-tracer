package com.lzhpo.logging.trace;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lzhpo
 */
@Data
@ConfigurationProperties(prefix = "logging.trace")
public class LoggingTraceProperties {

  private List<String> headers;
}

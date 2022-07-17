package com.lzhpo.tracer.webclient;

import com.lzhpo.tracer.TracerContextFactory;
import java.util.Map;
import org.apache.hc.core5.http.HttpRequest;

/**
 * @author lzhpo
 */
public interface InjectTracerContext {

  /**
   * Inject tracer logic
   *
   * @param tracerContextFactory {@link TracerContextFactory}
   * @param httpRequest {@link HttpRequest}
   */
  default void inject(TracerContextFactory tracerContextFactory, HttpRequest httpRequest) {
    Map<String, String> context = tracerContextFactory.buildContext();
    context.forEach(httpRequest::addHeader);
  }
}

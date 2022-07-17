package com.lzhpo.tracer.webclient;

import com.lzhpo.tracer.TracerContextFactory;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.MinimalHttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.io.CloseMode;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class ProxyMinimalHttpClient extends CloseableHttpClient implements InjectTracerContext {

  private final TracerContextFactory tracerContextFactory;
  private final MinimalHttpClient minimalHttpClient;

  @Override
  protected CloseableHttpResponse doExecute(
      HttpHost target, ClassicHttpRequest request, HttpContext context) throws IOException {
    inject(tracerContextFactory, request);
    return minimalHttpClient.execute(target, request, context);
  }

  @Override
  public void close(CloseMode closeMode) {
    minimalHttpClient.close(closeMode);
  }

  @Override
  public void close() throws IOException {
    minimalHttpClient.close();
  }
}

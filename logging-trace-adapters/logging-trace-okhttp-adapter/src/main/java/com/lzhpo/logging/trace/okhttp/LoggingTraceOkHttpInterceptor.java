package com.lzhpo.logging.trace.okhttp;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceOkHttpInterceptor implements Interceptor {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {
    Builder builder = chain.request().newBuilder();
    Map<String, String> proxyHeaderMap = traceHeaderProxy.buildProxyHeaderMap();
    proxyHeaderMap.forEach(builder::addHeader);
    Request newRequest = builder.build();
    return chain.proceed(newRequest);
  }
}

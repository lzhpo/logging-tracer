package com.lzhpo.logging.trace.okhttp;

import com.lzhpo.logging.trace.handler.LoggingTraceContextHandler;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceOkHttpInterceptor implements Interceptor {

  private final LoggingTraceContextHandler traceContextHandler;

  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {
    Builder builder = chain.request().newBuilder();
    Map<String, String> contextMap = traceContextHandler.buildAdapterContextMap();
    if (!ObjectUtils.isEmpty(contextMap)) {
      contextMap.forEach(builder::addHeader);
    }

    Request newRequest = builder.build();
    return chain.proceed(newRequest);
  }
}

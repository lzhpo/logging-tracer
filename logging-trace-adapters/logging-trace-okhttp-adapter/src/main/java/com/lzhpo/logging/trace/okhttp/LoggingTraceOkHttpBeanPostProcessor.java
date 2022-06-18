package com.lzhpo.logging.trace.okhttp;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceOkHttpBeanPostProcessor implements BeanPostProcessor {

  private final LoggingTraceOkHttpInterceptor traceOkHttpInterceptor;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Optional<OkHttpClient> okHttpClientOptional =
        Optional.of(bean)
            .filter(OkHttpClient.class::isInstance)
            .map(OkHttpClient.class::cast)
            .map(client -> client.newBuilder().addInterceptor(traceOkHttpInterceptor).build());
    return okHttpClientOptional.isPresent() ? okHttpClientOptional.get() : bean;
  }
}

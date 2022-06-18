package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceWebClientBeanPostProcessor implements BeanPostProcessor {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Optional<WebClient> webClientOptional =
        Optional.of(bean)
            .filter(WebClient.class::isInstance)
            .map(WebClient.class::cast)
            .map(
                webClient ->
                    webClient
                        .mutate()
                        .defaultRequest(
                            requestHeadersSpec ->
                                traceHeaderProxy
                                    .buildProxyHeaderMap()
                                    .forEach(requestHeadersSpec::header))
                        .build());
    return webClientOptional.isPresent() ? webClientOptional.get() : bean;
  }
}

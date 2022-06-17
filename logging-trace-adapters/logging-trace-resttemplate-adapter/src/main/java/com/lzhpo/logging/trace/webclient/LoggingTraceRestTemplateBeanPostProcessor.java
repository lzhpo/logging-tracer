package com.lzhpo.logging.trace.webclient;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.client.RestTemplate;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class LoggingTraceRestTemplateBeanPostProcessor implements BeanPostProcessor {

  private final LoggingTraceRestTemplateInterceptor traceRestTemplateInterceptor;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Optional<RestTemplate> restTemplateOptional =
        Optional.of(bean)
            .filter(RestTemplate.class::isInstance)
            .map(RestTemplate.class::cast)
            .map(
                restTemplate -> {
                  restTemplate.getInterceptors().add(traceRestTemplateInterceptor);
                  return restTemplate;
                });
    return restTemplateOptional.isPresent() ? restTemplateOptional.get() : bean;
  }
}

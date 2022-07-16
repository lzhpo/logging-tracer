/*
 * Copyright 2022 lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzhpo.logging.trace.webclient;

import com.lzhpo.logging.trace.TracerHeaderProxy;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author lzhpo
 */
@RequiredArgsConstructor
public class TracerWebClientBeanPostProcessor implements BeanPostProcessor {

  private final TracerHeaderProxy traceHeaderProxy;

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

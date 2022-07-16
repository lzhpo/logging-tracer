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
public class TracerOkHttpBeanPostProcessor implements BeanPostProcessor {

  private final TracerOkHttpInterceptor traceOkHttpInterceptor;

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

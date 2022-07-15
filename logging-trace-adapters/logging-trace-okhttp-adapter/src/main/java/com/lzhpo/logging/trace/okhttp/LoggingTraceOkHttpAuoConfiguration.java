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

import com.lzhpo.logging.trace.LoggingTraceHeaderProxy;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean({LoggingTraceHeaderProxy.class})
public class LoggingTraceOkHttpAuoConfiguration {

  private final LoggingTraceHeaderProxy traceHeaderProxy;

  @Bean
  @ConditionalOnMissingBean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }

  @Bean
  public LoggingTraceOkHttpBeanPostProcessor loggingTraceOkHttpBeanPostProcessor() {
    return new LoggingTraceOkHttpBeanPostProcessor(loggingTraceOkHttpInterceptor());
  }

  @Bean
  public LoggingTraceOkHttpInterceptor loggingTraceOkHttpInterceptor() {
    return new LoggingTraceOkHttpInterceptor(traceHeaderProxy);
  }
}

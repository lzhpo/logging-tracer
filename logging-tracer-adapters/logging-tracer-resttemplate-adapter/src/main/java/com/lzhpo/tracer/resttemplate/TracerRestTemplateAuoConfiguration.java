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

package com.lzhpo.tracer.resttemplate;

import com.lzhpo.tracer.TracerContextFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lzhpo
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass({RestTemplate.class})
@ConditionalOnBean({TracerContextFactory.class})
public class TracerRestTemplateAuoConfiguration {

  private final TracerContextFactory tracerContextFactory;

  @Bean
  @ConditionalOnMissingBean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public TracerRestTemplateInterceptor tracerRestTemplateInterceptor() {
    return new TracerRestTemplateInterceptor(tracerContextFactory);
  }

  @Bean
  public TracerRestTemplateBeanPostProcessor tracerRestTemplateBeanPostProcessor() {
    return new TracerRestTemplateBeanPostProcessor(tracerRestTemplateInterceptor());
  }
}
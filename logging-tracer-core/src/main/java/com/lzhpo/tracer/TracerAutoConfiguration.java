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

package com.lzhpo.tracer;

import com.lzhpo.tracer.support.ThreadPoolTaskExecutorBeanPostProcessor;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;

/**
 * Trace auto configure
 *
 * @author lzhpo
 */
@Configuration
@ConditionalOnProperty(prefix = "logging.tracer", value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({TracerProperties.class})
public class TracerAutoConfiguration {

    @Bean
    public TracerContextFactory tracerContextFactory(
            ObjectProvider<TracerContextCustomizer> contextCustomizerProvider) {
        List<TracerContextCustomizer> contextCustomizers =
                contextCustomizerProvider.stream().collect(Collectors.toList());
        return new DefaultTracerContextFactory(contextCustomizers);
    }

    @Bean
    public ThreadPoolTaskExecutorBeanPostProcessor threadPoolTaskExecutorBeanPostProcessor(
            ObjectProvider<TaskDecorator> taskDecoratorProvider) {
        return new ThreadPoolTaskExecutorBeanPostProcessor(taskDecoratorProvider.getIfAvailable());
    }
}

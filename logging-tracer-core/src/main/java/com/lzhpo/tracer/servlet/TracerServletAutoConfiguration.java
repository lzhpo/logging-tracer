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

package com.lzhpo.tracer.servlet;

import com.lzhpo.tracer.TracerAutoConfiguration;
import com.lzhpo.tracer.TracerContextFactory;
import com.lzhpo.tracer.TracerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author lzhpo */
@Configuration
@ConditionalOnBean({TracerContextFactory.class})
@ConditionalOnWebApplication(type = Type.SERVLET)
@AutoConfigureAfter({TracerAutoConfiguration.class})
public class TracerServletAutoConfiguration {

    @Bean
    public TracerServletMvcConfigurer tracerServletMvcConfigurer(
            TracerProperties tracerProperties, TracerContextFactory tracerContextFactory) {
        return new TracerServletMvcConfigurer(tracerProperties, tracerContextFactory);
    }
}

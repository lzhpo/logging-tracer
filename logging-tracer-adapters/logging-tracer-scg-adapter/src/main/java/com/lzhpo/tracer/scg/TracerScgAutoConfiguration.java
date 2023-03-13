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

package com.lzhpo.tracer.scg;

import com.lzhpo.tracer.TracerContextFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyRoutingFilter;
import org.springframework.context.annotation.Bean;

/** @author lzhpo */
@AutoConfiguration
@ConditionalOnClass({GlobalFilter.class})
@ConditionalOnWebApplication(type = Type.REACTIVE)
@ConditionalOnBean({NettyRoutingFilter.class, NettyRoutingFilter.class})
public class TracerScgAutoConfiguration {

    @Bean
    public TracerScgGlobalFilter tracerScgGlobalFilter(TracerContextFactory tracerContextFactory) {
        return new TracerScgGlobalFilter(tracerContextFactory);
    }
}
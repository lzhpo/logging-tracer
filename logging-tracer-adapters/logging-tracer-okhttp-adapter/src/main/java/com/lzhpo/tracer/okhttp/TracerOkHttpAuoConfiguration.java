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

package com.lzhpo.tracer.okhttp;

import com.lzhpo.tracer.TracerContextFactory;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author lzhpo */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass({OkHttpClient.class})
@ConditionalOnBean({TracerContextFactory.class})
public class TracerOkHttpAuoConfiguration {

    private final TracerContextFactory tracerContextFactory;

    @Bean
    public OkHttpClient.Builder okHttpClientBuilder(TracerOkHttpInterceptor tracerOkHttpInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(tracerOkHttpInterceptor);
    }

    @Bean
    public TracerOkHttpInterceptor tracerOkHttpInterceptor() {
        return new TracerOkHttpInterceptor(tracerContextFactory);
    }
}

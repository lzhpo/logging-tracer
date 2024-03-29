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

package com.lzhpo.tracer.reactor.netty;

import com.lzhpo.tracer.TracerContextFactory;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import reactor.netty.http.client.HttpClient;

/** @author lzhpo */
@RequiredArgsConstructor
public class ReactorNettyBeanPostProcessor implements BeanPostProcessor {

    private final TracerContextFactory tracerContextFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HttpClient) {
            HttpClient httpClient = (HttpClient) bean;
            return httpClient.doOnRequest((request, connection) -> {
                Map<String, String> context = tracerContextFactory.getContext();
                context.forEach(request::header);
            });
        }
        return bean;
    }
}

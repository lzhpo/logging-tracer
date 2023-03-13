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

package com.lzhpo.tracer.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Inject {@link TracerTaskDecoratorDelegate} into {@link ThreadPoolTaskExecutor}
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
 */
@Slf4j
@RequiredArgsConstructor
public class ThreadPoolTaskExecutorBeanPostProcessor implements BeanPostProcessor {

    private final TaskDecorator taskDecorator;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ThreadPoolTaskExecutor) {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) bean;
            TracerTaskDecoratorDelegate delegateTaskDecorator = new TracerTaskDecoratorDelegate(taskDecorator);
            threadPoolTaskExecutor.setTaskDecorator(delegateTaskDecorator);
            log.info("Injected [{}] in {}[{}].", delegateTaskDecorator, beanName, bean);
            return threadPoolTaskExecutor;
        }
        return bean;
    }
}

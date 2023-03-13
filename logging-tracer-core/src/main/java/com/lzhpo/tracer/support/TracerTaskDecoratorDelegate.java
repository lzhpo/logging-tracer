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

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * {@link TaskDecorator} delegate, inject maintain {@link MDC}'s context logic in child and parent
 * thread.
 *
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class TracerTaskDecoratorDelegate implements TaskDecorator {

    private final TaskDecorator delegate;

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        Runnable finalRunnable = Objects.nonNull(delegate) ? delegate.decorate(runnable) : runnable;
        return () -> {
            try {
                MDC.setContextMap(context);
                finalRunnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}

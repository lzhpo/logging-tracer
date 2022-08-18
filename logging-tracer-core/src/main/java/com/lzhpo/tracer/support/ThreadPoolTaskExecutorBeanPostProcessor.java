package com.lzhpo.tracer.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Inject {@link TaskDecorator} to {@link ThreadPoolTaskExecutor}
 *
 * @see org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class ThreadPoolTaskExecutorBeanPostProcessor implements BeanPostProcessor {

  private final TaskDecorator taskDecorator;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof ThreadPoolTaskExecutor) {
      ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) bean;
      threadPoolTaskExecutor.setTaskDecorator(taskDecorator);
      log.info("Injected taskDecorator[{}] in {}[{}].", taskDecorator, beanName, bean);
      return threadPoolTaskExecutor;
    }
    return bean;
  }
}

package com.lzhpo.logging.trace.sample.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lzhpo
 */
@FeignClient(name = "logging-trace-service-sample", url = "http://127.0.0.1:8000")
public interface ServiceSampleClient {

  @GetMapping("/service/hello")
  String hello();
}

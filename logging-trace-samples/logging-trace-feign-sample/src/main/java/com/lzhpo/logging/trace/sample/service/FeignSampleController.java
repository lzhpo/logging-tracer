package com.lzhpo.logging.trace.sample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@Slf4j
@RestController
@RequestMapping("/feign")
@RequiredArgsConstructor
public class FeignSampleController {

  private final ServiceSampleClient serviceSampleClient;

  @GetMapping("/callHello")
  public String callHello() {
    log.info("[callHello] Received new request for callHello api.");
    return serviceSampleClient.hello();
  }
}

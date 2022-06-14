package com.lzhpo.logging.trace.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@Slf4j
@RestController
@RequestMapping("/service")
public class ServiceSampleController {

  @GetMapping("/hello")
  public String hello() {
    log.info("[hello] Received new request for hello api.");
    return "Hello";
  }
}

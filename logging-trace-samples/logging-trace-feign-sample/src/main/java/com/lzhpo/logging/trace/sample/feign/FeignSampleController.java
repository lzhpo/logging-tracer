package com.lzhpo.logging.trace.sample.feign;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/")
@RequiredArgsConstructor
public class FeignSampleController {

  private final ServiceSampleClient serviceSampleClient;

  @GetMapping("/callHello")
  public String callHello(HttpServletRequest request) {
    log.info("Received new request for callHello api.");

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      log.info("Request header with [{}: {}]", headerName, request.getHeader(headerName));
    }

    return serviceSampleClient.callHello();
  }
}

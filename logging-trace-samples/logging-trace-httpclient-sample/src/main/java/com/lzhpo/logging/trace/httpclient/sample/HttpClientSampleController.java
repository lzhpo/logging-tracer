package com.lzhpo.logging.trace.httpclient.sample;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
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
public class HttpClientSampleController {

  private final CloseableHttpClient httpClient;

  @GetMapping("/callHello")
  public String callHello(HttpServletRequest request) throws IOException, ParseException {
    log.info("Received new request for callHello api.");

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      log.info("Request header with [{}: {}]", headerName, request.getHeader(headerName));
    }

    HttpGet httpGet = new HttpGet("http://127.0.0.1:8001/hello");
    CloseableHttpResponse response = httpClient.execute(httpGet);
    return EntityUtils.toString(response.getEntity());
  }
}

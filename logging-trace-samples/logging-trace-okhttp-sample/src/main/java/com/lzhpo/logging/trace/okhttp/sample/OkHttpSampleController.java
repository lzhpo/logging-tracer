package com.lzhpo.logging.trace.okhttp.sample;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
public class OkHttpSampleController {

  private final OkHttpClient okHttpClient;

  @GetMapping("/callHello")
  public String callHello(HttpServletRequest request) throws IOException {
    log.info("Received new request for callHello api.");

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      log.info("Request header with [{}: {}]", headerName, request.getHeader(headerName));
    }

    Request okHttpRequest =
        new Request.Builder().url("http://127.0.0.1:9002/callHello").get().build();
    //    try (Response response = okHttpClient.newCall(okHttpRequest).execute()) {
    //      return Objects.requireNonNull(response.body()).string();
    //    }

    Response response = okHttpClient.newCall(okHttpRequest).execute();
    return Objects.requireNonNull(response.body()).string();
  }
}

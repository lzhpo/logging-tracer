/*
 * Copyright 2023 lzhpo
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

package com.lzhpo.tracer.sample.dubbo;

import cn.hutool.extra.spring.SpringUtil;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@Slf4j
@RestController
@RequestMapping("/")
public class DemoServiceController {

    @DubboReference
    private DemoService demoService;

    @GetMapping("sayHello")
    public ResponseEntity<String> sayHello() {
        log.info("GET /hello");
        String sayHello = demoService.sayHello(SpringUtil.getApplicationName());
        return ResponseEntity.ok(sayHello);
    }

    @GetMapping("sayHelloAsync")
    public ResponseEntity<String> sayHelloAsync() throws ExecutionException, InterruptedException {
        log.info("GET /sayHelloAsync");
        CompletableFuture<String> sayHelloAsync = demoService.sayHelloAsync(SpringUtil.getApplicationName());
        return ResponseEntity.ok(sayHelloAsync.get());
    }
}

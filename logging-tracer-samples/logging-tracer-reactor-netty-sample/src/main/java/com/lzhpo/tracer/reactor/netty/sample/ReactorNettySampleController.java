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

package com.lzhpo.tracer.reactor.netty.sample;

import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * @author lzhpo
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ReactorNettySampleController {

    private final HttpClient httpClient;

    @GetMapping("/hello")
    public Mono<String> hello() {
        return httpClient
                .request(HttpMethod.GET)
                .uri("http://127.0.0.1:9000/hello")
                .responseContent()
                .aggregate()
                .asString();
    }
}

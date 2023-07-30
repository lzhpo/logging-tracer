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

package com.lzhpo.tracer.webflux;

import com.lzhpo.tracer.TracerContextFactory;
import com.lzhpo.tracer.TracerProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class TracerWebfluxFilter implements WebFilter, Ordered {

    private final TracerProperties tracerProperties;
    private final TracerContextFactory tracerContextFactory;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        List<String> proxyHeaders = tracerProperties.getProxyHeaders();
        Map<String, String> context = new HashMap<>(proxyHeaders.size());
        proxyHeaders.forEach(headerName -> context.put(headerName, headers.getFirst(headerName)));
        tracerContextFactory.setContext(context);
        log.debug("Current request URI: {}", request.getURI().getPath());

        return chain.filter(exchange).doFinally(x -> tracerContextFactory.clearContext());
    }
}

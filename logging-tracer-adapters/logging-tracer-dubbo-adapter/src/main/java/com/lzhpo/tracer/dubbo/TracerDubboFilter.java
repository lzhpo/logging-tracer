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

package com.lzhpo.tracer.dubbo;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.tracer.TracerContextFactory;
import com.lzhpo.tracer.TracerProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author lzhpo
 */
@Slf4j
@Activate(group = {PROVIDER, CONSUMER})
public class TracerDubboFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        TracerContextFactory tracerContextFactory = SpringUtil.getBean(TracerContextFactory.class);

        if (RpcContext.getServiceContext().isConsumerSide()) {
            Map<String, String> context = tracerContextFactory.getContext();
            context.forEach(invocation::setAttachment);
        } else {
            TracerProperties tracerProperties = SpringUtil.getBean(TracerProperties.class);
            List<String> proxyHeaders = tracerProperties.getProxyHeaders();
            Map<String, String> attachments = invocation.getAttachments();

            Map<String, String> context = new HashMap<>(proxyHeaders.size());
            proxyHeaders.forEach(headerName -> context.put(headerName, attachments.get(headerName)));
            tracerContextFactory.setContext(context);
        }

        return invoker.invoke(invocation);
    }
}

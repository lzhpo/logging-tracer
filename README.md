模拟多链路调用服务，按如下顺序逐层call：
1. logging-tracer-feign-sample: 8000
2. logging-tracer-httpclient-sample: 8001
3. logging-tracer-okhttp-sample: 8002
4. logging-tracer-resttemplate-sample: 8003
5. logging-tracer-webclient-sample: 8004
6. logging-tracer-service-sample: 9000
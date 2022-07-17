![](https://img.shields.io/badge/JDK-1.8+-success.svg)
![](https://maven-badges.herokuapp.com/maven-central/com.lzhpo/logging-tracer-spring-boot-starter/badge.svg?color=blueviolet)
![](https://img.shields.io/:license-Apache2-orange.svg)
[![Style check](https://github.com/lzhpo/logging-tracer-spring-boot-starter/actions/workflows/style-check.yml/badge.svg)](https://github.com/lzhpo/logging-tracer-spring-boot-starter/actions/workflows/style-check.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/51f27097abaf4fb891f11d7eb06241fe)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=lzhpo/logging-tracer-spring-boot-starter&amp;utm_campaign=Badge_Grade)

## 快速使用

### 导入依赖

> 依赖已发布至Maven中央仓库，可直接引入依赖。

- Maven：
  ```xml
  <dependency>
    <groupId>com.lzhpo</groupId>
    <artifactId>logging-tracer-spring-boot-starter</artifactId>
    <version>${latest-version}</version>
  </dependency>
  ```
- Gradle:
  ```groovy
  implementation 'com.lzhpo:logging-tracer-spring-boot-starter:${latest-version}'
  ```

### 使用示例

在这模拟多链路调用服务，按如下顺序逐层call：

| 顺序 |      服务名称       |       描述       | 端口 |
| :--: | :-----------------: | :--------------: | :--: |
|  1   |    feign-sample     |    feign示例     | 8000 |
|  2   |  httpclient-sample  |  httpclient示例  | 8001 |
|  3   |    okhttp-sample    |    okhttp示例    | 8002 |
|  4   | resttemplate-sample | resttemplate示例 | 8003 |
|  5   |  webclient-sample   |  webclient示例   | 8004 |
|  6   |   service-sample    |  最终的服务名称  | 9000 |



#### 1.Feign

直接使用即可。

**参考示例**：logging-tracer-feign-sample

```shell
2022-07-17 18:46:00.458  INFO [feign-sample,N/A,86b65402341e4d479725cc6e92b0bf61,0] 11816 --- [nio-8000-exec-2] c.l.t.s.feign.FeignSampleController      : Received new request for hello api.
```

#### 2.HttpClient

和使用`HttpClients`一样，不同的是在这使用的`TracerHttpClients` 的Bean，方法和`HttpClients`一样。

**参考示例**：logging-tracer-httpclient-sample

```shell
2022-07-17 18:46:00.581  INFO [httpclient-sample,feign-sample,86b65402341e4d479725cc6e92b0bf61,0.1] 19904 --- [nio-8001-exec-2] c.l.t.h.s.HttpClientSampleController     : Received new request for hello api.
```

#### 3.Okhttp

- **之前**：

  ```java
  OkHttpClient okHttpClient = new OkHttpClient();
  ```

- **现在**：注入`OkHttpClient.Builder`Bean直接使用，就不是直接new了。

  ```java
  OkHttpClient okHttpClient = okHttpClientBuilder.build();
  ```

**参考示例**：logging-tracer-okhttp-sample

```shell
2022-07-17 18:46:00.894  INFO [okhttp-sample,httpclient-sample,86b65402341e4d479725cc6e92b0bf61,0.1.1] 7996 --- [nio-8002-exec-1] c.l.t.o.sample.OkHttpSampleController    : Received new request for hello api.
```

#### 4.RestTemplate

直接注入`RestTemplate`Bean使用即可。

**参考示例**：logging-tracer-resttemplate-sample

```shell
2022-07-17 18:46:01.162  INFO [resttemplate-sample,okhttp-sample,86b65402341e4d479725cc6e92b0bf61,0.1.1.1] 15084 --- [nio-8003-exec-2] c.l.t.s.f.RestTemplateSampleController   : Received new request for hello api.
```

#### 5.Webclient

直接注入`Webclient`Bean使用即可。

**参考示例**：logging-tracer-webclient-sample

```shell
2022-07-17 18:46:01.375  INFO [webclient-sample,okhttp-sample,86b65402341e4d479725cc6e92b0bf61,0.1.1.1] 2252 --- [ctor-http-nio-2] c.l.t.w.s.WebClientSampleController      : Request header with [Accept: [text/plain, application/json, application/*+json, */*]]
```
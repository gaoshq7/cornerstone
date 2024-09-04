<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">主机管理</h1>
<h4 align="center">基于主从模式的集群主机管理框架，主从节点之间保持长链接。</h4>

---

## 特性

- **长链接**：基于 Netty 实现一个主节点和若干个从节点之间保持TCP长链接。
- **心跳包**：使用 protobuf 序列化方案实现心跳包内容定制化。
- **失联通知**：提供从节点失联通知接口供使用者实现相应的业务逻辑。
- **断线重连**：因其它原因导致的主从节点断开链接支持断线重连。
- **链接认证**：提供接口供使用者根据业务逻辑实现从节点加入集群的认证。

## 使用

## 引入依赖
```java
<dependency>
    <groupId>io.github.gaoshq7</groupId>
    <artifactId>host-manager-spring-boot-starter</artifactId>
    <version>1.0.0-Alpha</version>
</dependency>
```

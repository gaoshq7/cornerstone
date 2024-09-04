<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Cornerstone</h1>
<h4 align="center">一套分布式应用的基础套件，让你的集群更加稳定、高效！</h4>
<p align="center">
	<a href="https://github.com/gaoshq7/cornerstone/blob/main/LICENSE"><img src="http://img.shields.io/badge/license-apache%202-brightgreen.svg"></a>
</p>

---

## 简介

`Cornerstone`是一套针对于 Spring boot 研发环境下分布式应用系统的基础套件，包括集群长链接框架、RPC框架等基础模块，提供简单便捷的接口给开发者使用，让开发者把更多的精力集中在业务逻辑的开发上。

## 特性

- **插拔式**：模块采用 Spring boot starter 结构进行集成，使用者可以根据自己的需要进行模块选择，插入即用。
- **易用性**：模块提供各自相应的功能接口给使用者调用，同时开放重要业务接口供使用者进行定制化实现。

## 组件

- [主机管理](https://github.com/gaoshq7/cornerstone/blob/main/host-manager-spring-boot-starter/README.md) - 分布式集群基础组件，主从之间保持长链接，定时发送心跳确保节点存活。
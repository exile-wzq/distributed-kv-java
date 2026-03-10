# distributed-kv-java
基于 Java 实现的分布式 KV 存储系统

##  项目介绍
* **纯 Java 实现**：轻量级分布式键值存储原型。
* **数据一致性**：基于 Raft 协议保证多节点间的数据同步。
* **模块化架构**：解耦存储、网络与共识逻辑，便于学习与扩展。

---

##  项目结构
```text
com.exile.kv
├── storage    // 存储层：实现内存 KV 与持久化逻辑
├── server     // 服务层：处理客户端请求与节点生命周期
├── network    // 网络层：基于 TCP 的自定义 RPC 通信
└── raft       // 共识层：Raft 选举与日志复制核心算法
```
## 技术栈
- 语言：Java (JDK 17)
- 构建：Maven
- 网络：TCP 网络编程 (Bio/Nio)
- 算法：分布式共识 Raft
- 存储：In-memory Key-Value Store

## 快速开始
1. 克隆项目
git clone https://github.com/Exile3927/distributed-kv-java.git
 

2.  打开 IDEA 导入 Maven 项目
3.  运行 KVServer 启动服务
4.  支持 put / get / delete 操作
 
## 开发进度
- **第一阶段**：单机核心
- [x] 项目初始化与Git环境搭建
- [x] 基础包结构划分
- [ ] 基于内存的单机 KV 存储实现  
  

- **第二阶段**：网络架构
- [ ] 基于TCP的服务端实现
- [ ] 节点间RPC通信协议设计
  

- **第三阶段**：共识算法
- [ ] Raft领导者选举机制
- [ ] 日志复制与一致性保障
- [ ] 节点动态加入与退出
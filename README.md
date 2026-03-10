# distributed-kv-java
```markdown
一个基于 Java 实现的轻量级 KV 存储系统原型，用于学习分布式系统、网络通信与存储引擎设计。
```
##  项目介绍
* **纯 Java 实现**：轻量级分布式键值存储原型。
* **分布式一致性（规划中）**：未来将基于 Raft 协议实现多节点间的数据同步。
* **模块化架构**：解耦存储、网络与共识逻辑，便于学习与扩展。

---

##  系统架构
```markdown
Client
   │
   ▼
TcpServer (network)
   │
   ▼
CommandParser (protocol)
   │
   ▼
Storage Interface
   │
   ▼
MemoryStorage
```
---

##  项目结构
```text
com.exile.kv
├── storage    // KV 存储接口与实现
├── server     // 服务启动入口
├── protocol   // 命令解析与协议处理
├── network    // TCP Server 与连接管理
└── raft       // 分布式共识算法（规划中）
```

---

## 技术栈
- **语言**：Java (JDK 17)
- **构建工具**：Maven
- **网络通信**：TCP 网络编程 (Bio)
- **并发模型**：ExecutorService 线程池
- **存储引擎**：In-memory Key-Value Store
- **共识算法（规划）**：Raft

---

## 快速开始
1. 克隆项目
git clone https://github.com/Exile3927/distributed-kv-java.git

2. 使用 IDEA 打开 Maven 项目
3. 运行 KVServer 启动服务
4. 使用telnet测试
```bash
telnet localhost 8888
```
```bash
示例：
PUT name exile
GET name
DEL name
 ```
---

## 开发进度
- **第一阶段**：单机核心
- [x] 项目初始化与Git环境搭建
- [x] 基础包结构划分
- [x] 基于内存的单机 KV 存储实现  
- [x] 命令协议解析 (PUT / GET / DEL)
  

- **第二阶段**：网络架构
- [x] 基于TCP的服务端实现
- [x] 多客户端并发连接（线程池）
- [x] 协议解析模块解耦（CommandParser)
- [ ] 节点间RPC通信协议设计
  

- **第三阶段**：分布式一致性
- [ ] Raft领导者选举机制
- [ ] 日志复制
- [ ] 节点动态加入与退出

---

##  未来规划

- WAL 持久化
- LSM Tree 存储引擎
- NIO 网络模型
- Raft 分布式一致性
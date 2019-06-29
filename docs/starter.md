# 概念

## 实现原则

### 核心库一定不能与数据库业务耦合

原因主要有如下几条：

1. 不好扩展，不可定制

2. 不好修改，不好修复

3. 不好测试

### 插件一定得稳定和灵活

## 其它


- DO(Data Object):此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。 -> model

- DTO(Data Transfer Object):数据传输对象，Service 或 Manager 向外传输的对象。

- BO(Business Object):业务对象，由 Service 层输出的封装业务逻辑的对象。

- AO(Application Object):应用对象，在 Web 层与 Service 层之间抽象的复用对象模型，极为贴 近展示层，复用度不高。

- VO(View Object):显示层对象，通常是 Web 向模板渲染引擎层传输的对象。

- Query:数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类
  来传输。
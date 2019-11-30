# lin-cms-java


**正在大规模重构中，请勿使用！！！**

> lin-cms 的 java 版实现基于 spring boot 和 mybatis

## 设计哲学

**简单，易用**

## 状态

**正在抓紧实现，占坑中...**

- [ ] 主键均换成 Long，数据库层面均为 bigint
- [ ] fastjson换回jackson
- [ ] 移动core的部分代码到starter
- [ ] 尝试重构异常处理部分，提供接口供 demo 重写
- [ ] 文档
- [ ] 分离demo项目到另一个仓库
- [ ] 业务测试
- [ ] 单元测试
- [ ] 核心系统
- [ ] 插件具体的提供方式，maven 的 module 还是直接存放在 app 包里面
- [x] 插件
- [x] 业务

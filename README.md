# lin-cms-java


**正在大规模重构中，业务和结构都发生了大量变化，前端还无适用版本，请勿使用！！！**

> lin-cms 的 java 版实现基于 spring boot 和 mybatis-plus

## 设计哲学

**简单，易用**

## 状态

**正在抓紧实现，占坑中...**

- [x] 业务优化，分层
- [x] 异常优化，如何定义异常类，如何处理全局异常 (异常类定义在configuration里面，异常处理在demo中)
- [x] 自动装载优化，哪些bean保留，哪些剔除
- [x] 工具类优化，项目结构优化
- [ ] 优化权限，已虚类和接口的方式抽离

## 使用

```bash
git clone https://github.com/PedroGao/lin-cms-java
```

```bash
cd lin-cms-java && mvn install
```
# lin-cms-java


** 项目已经迁移，见 [lin-cms-spring-boot](https://github.com/TaleLin/lin-cms-spring-boot) **

> lin-cms 的 java 版实现基于 spring boot 和 mybatis-plus

## 设计哲学

**简单，易用**

## 特性

- [x] 优雅的权限系统
- [x] 双令牌
- [x] 骚气的banner
- [x] 国际化
- [x] 全局异常处理
- [x] 优雅的分层结构
- [x] 行为日志
- [x] 请求日志
- [x] 校验类库
- [x] 文件上传

## 使用

在MySQL中新建数据库`lin-cms`，本地端口连接`3306`

```bash
git clone https://github.com/PedroGao/lin-cms-java
```

```bash
cd lin-cms-java && mvn install
```

```bash
cd demo && java -jar target/demo-0.0.1-SNAPSHOT.jar
```

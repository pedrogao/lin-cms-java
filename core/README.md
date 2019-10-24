# lin-cms core

> lin-cms 核心库部分

## 原则

与spring boot 解耦合，即不依赖任何 spring boot 的库，只依赖其它有用的第三方库

如 jhash，jwt 等。。。

然后提供 lin-spring-boot-starter 和 lin-spring-boot-autoconfigure 两个可用的spring boot依赖方便开发

最后提供 lin-cms-java 示例工程项目。

## core(核心库)的功能如下

- 基础异常(HttpException)

- 返回结果(Result和PageResult)

- 诸多枚举

- 诸多注解

- 其它待补充
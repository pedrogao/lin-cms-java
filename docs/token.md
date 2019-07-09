---
title: 令牌
---

# <H2Icon /> 令牌

## 为何选择 jwt?

最近几年 Restful API 及 SPA(单页面应用) 的盛行，cookie-session 的机制似乎越来越
不适合前后端分离的场景。在分布式和微服务的趋势下，不少人选择在 redis 中存储
session 来达到单点登陆的效果，这无疑增加了成本和开发难度。于是更多的人转而使用
jwt 来管理用户会话和授权，在 jwt[官网](https://jwt.io/introduction/)介绍其两大使
用场景。

- Authorization(授权)：这是 jwt 应用最为广泛的场景。jwt 将数据加密存储，分发给前
  端，前端将其放在特定的 header 字段 中（也有放在 params 和 body 中），服务器收
  到请求后，解析 jwt 判断用户身份，对用户请求进行限权。

- Information Exchange(数据交换): jwt 可以通过公钥和私钥对信息进行加密，双方通信
  后，互得数据。

关于 jwt 的使用及生态请阅读 jwt[官网](https://jwt.io/introduction/)获得更多信息
。

## access_token 和 refresh_token

在一般 jwt 应用中，*access_token*和*refresh_token*是一对相互帮助的好搭档，前面讲
到用户在前端登陆后，服务器会发送 access_token 和 refresh_token 给前端，前端在得
到这两个 token 之后必须**谨慎存储**。

- access_token ：用来用户鉴权，控制用户对接口，资源的访问。access_token 十分重要
  ，它是服务器对前端有力控制的唯一途径，故一般其生存周期十分短，一般在 2 个小时
  左右，更有甚者，其生命周期只有 15 分钟，在 Lin 中默认 1 个小时。

- refresh_token ：用户通过登陆后获得 access_token，而 access_token 的生命周期十
  分短暂，但是用户登陆太频繁会严重影响体验，因此需要一种免登陆便能获取
  access_token 的方式。refresh_token 主要用来解决该问题，refresh_token 的生命周
  期较长，一般为 30 天左右，但 refresh_token 不能被用来用户身份鉴权和获取资源，
  它只能被用来重新获取 access_token。当前端发现 access_token 过期时，便应通过
  refresh_token 重新获取 access_token。

## 项目中的使用

在 Lin 中已经默认集成了 jwt 的机制，你可以通过 HTTP 请求来进行相关的操作。

### 用户获取 access_token 和 refresh_token

`path`: /cms/user/login

`method`: post

`参数`:

| name     |  说明  |  类型  | 作用     |
| -------- | :----: | :----: | -------- |
| nickname | 用户名 | string | \*\*\*\* |
| password |  密码  | string | \*\*\*\* |

`结果`:

```json
{
  "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzU1MzMyNjMsIm5iZiI6MTUzNTUzMzI2MywianRpIjoiMTlkZWUwNzQtNzUxYi00MjBlLTk3NjAtZDRkMzc3YjdjMjUyIiwiZXhwIjoxNTM1NjE5NjYzLCJpZGVudGl0eSI6InBlZHJvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNjZXNzIn0.9sNmAV5anxY5N1S1kaXzRRpdjzVX3fX6iI0ZjxGiiVs",
  "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzU1MzMyNjMsIm5iZiI6MTUzNTUzMzI2MywianRpIjoiYjU0OWIwZGEtMTE3MS00NzJlLWE0MDMtMDFkMGRkZTRjOTYzIiwiZXhwIjoxNTM4MTI1MjYzLCJpZGVudGl0eSI6InBlZHJvIiwidHlwZSI6InJlZnJlc2gifQ.cBnqEBnome-dMFEueQ8oCJfoXX9_mzQJAGjyeq4bYh8"
}
```

`使用`:

在请求其他资源或接口时在 header 中，加入`Authorization`字段，字段值为`Bearer`加
上`access_token`，注意两个字段中间必须有一个空格，如下：

```bash
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzU1MzMyNjMsIm5iZiI6MTUzNTUzMzI2MywianRpIjoiMTlkZWUwNzQtNzUxYi00MjBlLTk3NjAtZDRkMzc3YjdjMjUyIiwiZXhwIjoxNTM1NjE5NjYzLCJpZGVudGl0eSI6InBlZHJvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNjZXNzIn0.9sNmAV5anxY5N1S1kaXzRRpdjzVX3fX6iI0ZjxGiiVs
```

服务器会解析该字段并得到用户信息，对用户进行鉴权。

### refresh_token 获取 access_token

`path`: /cms/user/refresh

`method`: get

`参数`: 无，注意在 Authorization 中加上 refresh_token。 `结果`:

```json
{
  "access_token":
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzU1MzMyNjMsIm5iZiI6MTUzNTUzMzI2MywianRpIjoiMTlkZWUwNzQtNzUxYi00MjBlLTk3NjAtZDRkMzc3YjdjMjUyIiwiZXhwIjoxNTM1NjE5NjYzLCJpZGVudGl0eSI6InBlZHJvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNjZXNzIn0.9sNmAV5anxY5N1S1kaXzRRpdjzVX3fX6iI0ZjxGiiVs"
```

`使用`：

用户获取资源时，`Authorization`字段的值为`Bearer`加上`access_token`，当通过
refresh_token 获取 access_token 时，应将`Authorization`中的 access_token 替换为
refresh_token，如：

```bash
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzU1MzMyNjMsIm5iZiI6MTUzNTUzMzI2MywianRpIjoiYjU0OWIwZGEtMTE3MS00NzJlLWE0MDMtMDFkMGRkZTRjOTYzIiwiZXhwIjoxNTM4MTI1MjYzLCJpZGVudGl0eSI6InBlZHJvIiwidHlwZSI6InJlZnJlc2gifQ.cBnqEBnome-dMFEueQ8oCJfoXX9_mzQJAGjyeq4bYh8
```

服务器会解析该字段，如 refresh_token 字段未过期则会发送新的 access_token。

:::tip

当然在 Lin 的前端框架中，已经默认实现了以上机制，你大可不必自己去实现，当然如果
你想深入了解，也可在阅读本小节后自行尝试。

:::

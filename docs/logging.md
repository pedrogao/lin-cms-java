# 日志系统

在本小节的正式内容之前，我们先来闲聊一下目前市面上的日志框架。在笔者
为`lin-cms`实现日志系统的过程中，浏览和使用了诸多的日志框架，诸
如`java`的`log4j`和`logback`，`node.js`的`log4j`和`egg-logger`以及`consola`，还
有其它很多的日志框架。

不难发现，`java`作为工业界的王者，它的日志框架最为齐全，高效和易用，因
此`node.js`的`log4j`几乎可以说是大量借鉴了`java`的`log4j`。得益于`log4j`优雅的设
计哲学，nodejs 的`log4j`目前已经是`nodejs`下的第一日志框架，可惜因为`js`本身的桎
梏，nodejs 版的 log4j 不够灵活。

`lin-cms-koa`日志系统拥有自己的标准，这个日志标准需要高度的自定制。因此，笔者确
实纠结了很长一段时间，`koa`这个框架过小，基础设施太少，因此必须得去集成其它框架
，`nodejs`的生态虽然说很不错，可惜质量跟`java`比确实显得相形见绌。

很可惜，`log4j`不能够满足我们的要求，`consola`输出的日志确实很漂亮，可以仅仅支持
终端的普通输出。因此笔者选择了`egg-logger`进行定制，笔者在实现的过程中，仿佛可以
体会到`egg.js`团队可能也不满`log4j`，所以实现了自己的`egg-logger`。

闲聊完了，接下来正式进入日志系统的内容。

## 使用

在`lin-cms-koa`中，日志系统是开箱即用的，你无需自己去集成，可以直接拿来就用。它
被挂载到了`context`的原型上，你可以通过如下的方式主动调用。

```js
ctx.logger.info();
ctx.logger.warn();
ctx.logger.debug();
ctx.logger.error();
```

请将重要的调试信息输出到日志中，方便开发查看。

## 日志记录

`lin-cms-koa`记录的日志会默认记录到两个地方：

- 终端：将日志记录到终端是最基础的记录方法，几乎所有的库都会向终端输出日志。

- 文件：将日志记录到文件，绝大部分情况我们需要将日志存储到磁盘以供后面查看和分析
  。lin-cms-koa 记录到文件的日志，是有一套标准的，如下：

```bash
logs
└── 2019-06
  ├── 2019-06-12.log
  ├── 2019-06-14.log
  └── 2019-06-17.log
```

日志文件默认存储到`当前工作目录下的logs目录`，且每一个月都是一个子目录，每一个子
目录下皆有每一天的日志文件。当某一个日志文件超过一定的大小时，会被切割。

具体日志的记录信息如下：

```bash
2019-06-16 21:32:59,857 DEBUG 8110  --- [7insummer] - [POST] -> [/cms/file/] from: 127.0.0.1 costs: 39ms data:{
    "param": {},
    "body": {}
}
2019-06-16 21:33:37,748 DEBUG 8110  --- [7insummer] - [POST] -> [/cms/user/login] from: 127.0.0.1 costs: 7ms data:{
    "param": {},
    "body": {
        "nickname": "super",
        "password": "123456"
    }
}
```

## 日志配置

在`app/config/log.js`下存放着日志系统的配置：

```js
module.exports = {
  log: {
    level: 'DEBUG', // 日志记录的level，推荐开发环境下为DEBUG，生产环境下为INFO
    dir: 'logs', // 记录日志文件的目录，默认为logs
    sizeLimit: 1024 * 1024 * 5, // 日志文件的切割大小，默认为5M
    requestLog: true, // 记录http请求日志，默认为true，即记录，为false时则不记录
    file: true // 是否开启日志文件记录，默认为true
  }
};
```

在上面，已经明确的标明了每一项配置的作用，不过我们还是需要着重强调两个地方。

1. level，日志的 level 很重要，只要 level 足够的日志才会被输出，一般开发环境下推
   荐 debug，生产环境下推荐 info。

2. file，磁盘 IO 是耗性能的。所以日志记录到文件是有成本的，如果你追求极致的性能
   ，又对日志的记录的要求不高，确实可以考虑关闭 file。

## 自定义 Transport

`Transport`(传输)，是`egg-logger`里面一个重要的概念，你可以把它简单的理解
为`日志传输`。记住，日志是可以有多条传输的，默认`lin-cms-koa`有两条传输路
径`console`和`file`。你可以自定义一条`Transport`并加入到`logger`中，让日志传输到
你需要传输到的地方。

```js
// urllib-transport.js
const urllib = require('urllib');
const Transport = require('egg-logger').Transport;

class UrllibTransport extends Transport {
  log(level, args, meta) {
    const msg = super.log(level, args, meta);
    return urllib.request('url?msg=' + msg);
  }
}
```

```js
// app.js
app.context.logger.set(
  'remote',
  new UrllibTransport({
    level: 'DEBUG'
  })
);
logger.info('info');
```

如上，我们自定义了一个`UrllibTransport`的类，并将其加到了`logger`中，这样日志就
可以通过`urllib`发送到其它地方。

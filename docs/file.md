# 文件上传系统

## 概述

在 CMS 的绝大多数业务或者功能中，我们都是以数据流的形式来处理数据的，但有些情况下，比如我们需要存储一个图片，或者上传一段视频。
这些数据都是以文件的形式存在，它们皆以硬盘为载体。

你可以设想这样的一个业务，leader 要求你可以从前端操作界面上上传一个漂亮小姐姐的
照片到服务器，但这还不够，他要要求上传一份报表或者一份 PPT。于是你开始忙碌了起来
，开发一个上传图片的接口，上传报表的接口。

但是这两者完全耦合了，于是你想到了干脆开发一个上传文件的接口，但是这个接口的设计
颇为复杂。你得考虑文件类型的限制，文件大小的限制，文件数量的限制，甚至上传文件如
何存储，存本地了，还是存远程静态资源服务器了，等等。。。

于是 lin 出现了，像一个 hero，帮你一下子解决了这个问题，这个文件上传功能，它被默
认集成到了 lin 的基础业务中，你可以迅速地使用它。

## 使用

### 模式

我们把 lin 的文件上传接口定到了`cms/file/`这个路径上，你可以通过 post 方法上传文
件。lin 默认的文件上传实现是`LocalUploader`这个类，意思是前端上传的文件会保存到
服务器本地，但更多的时候我们需要将文件保存到云服务器上，如阿里云或腾讯云，lin 并
没有局限在此，你可以自己实现存储文件的逻辑，将文件保存到你希望的地方。

对于文件上传(`file`)这种可以有多种实现方式的功能，我们把其诸多实现归入
到`app/extensions/file`这个目录下。如`local-uploader.js`这个文件实现了上传到本地
的`LocalUploader`类，`config.js`文件是与其相关的配置文件。

```bash
app/extensions/
└── file
    ├── config.js  # 配置文件
    └── local-uploader.js # LocalUploader类实现文件
```

默认的情况下在，`cms/file/`这个接口使用`LocalUploader`这个文件上传实现类。

```js
file.post('/', async ctx => {
  // 解析上传文件，得到所有符合条件的文件流
  const files = await ctx.multipart();
  if (files.length < 1) {
    throw new ParametersException({ msg: '未找到符合条件的文件资源' });
  }
  // 传入本地文件的存储目录，默认为当前工作目录下的 app/assets
  const uploader = new LocalUploader('app/assets');
  const arr = await uploader.upload(files);
  ctx.json(arr);
});
```

在这段代码中， 我们首先调用了`multipart`这个方法，它会为我们自动解析客户端传输过
来的文件，并按照配置进行筛选，如果不满足配置要求，会自动抛出异常反馈给前端。调用
了方法之后得到所有上传文件的流`files`，然后实例化了`LocalUploader`类，
把`files`当作参数传给了`uploader.upload`方法。

记住这一步，它是固定的，任何继承自`Uploader`这个基类的实现类必须重写`upload`方法
，如`LocalUploader`中：

```js
class LocalUploader extends Uploader {
  /**
   * 处理文件流
   * @param {object[]} files 文件流数组
   */
  async upload(files) {}
}
```

当需要实现其它的上传类时，如上传到阿里云 OSS，我们只需要重新定义一个`Aliyun`类，
在类中重新实现这个`upload`方法，而后在视图函数中，更
换`LocalUploader`为`Aliyun`即可快速切换。

### 实操

在具体的实践之前，我们需要了解一下`file`的具体配置。

```js
'use strict';

module.exports = {
  file: {
    storeDir: 'app/assets', // 文件的存储路径，你也可以在LocalUploader的构造函数中传入
    singleLimit: 1024 * 1024 * 2, // 单个文件的大小限制，默认2M
    totalLimit: 1024 * 1024 * 20, // 所有文件的大小限制，默认20M
    nums: 10, // 文件数量限制，默认10
    exclude: [] // 文件后缀名的排除项，默认排除[]，即允许所有类型的文件上传
    // include:[] // 文件后缀名的包括项
  }
};
```

在单个配置的后面，笔者已经以注释的方式解释了每项的作用。当然还需要着重解释一
下`exclude`和`include`这两项，默认情况下，只会读取它们中的一项，读取其中不
为`undefined`的一项，如果两项皆为`undefined`的话，会通过所有文件类型，如果二者都
有则取`include`为有效配置，总而言之，系统只会支持使用一项，二者都为 undefined 的
情况下，则通过所有文件类型。

注意了！！！在`app/extensions/`下的配置文件，系统不会帮我们做自动加载，不同
于`app/config`。因此，你必须`starter.js`文件中，显示的加载该配置文件，如:

```js
// 1. 必须最开始加载配置，因为其他很多扩展依赖于配置
function applyConfig() {
  const cwd = process.cwd();
  const files = fs.readdirSync(`${cwd}/app/config`);
  for (const file of files) {
    config.getConfigFromFile(`app/config/${file}`);
  }
  // 此处，加载其它配置文件
  config.getConfigFromFile('app/extensions/file/config.js');
}
```

当做好了这些后，我们使用 postman 测试一下文件上传。

<img-wrapper>
  <img src="http://imglf4.nosdn0.126.net/img/Qk5LWkJVWkF3NmlvOHFlZzFHSk95OGhiL0lSSFo3OFNPSGc1WEFnc0JRVERUb2JSU0cvSUlnPT0.png?imageView&thumbnail=2090y1120&type=png&quality=96&stripmeta=0">
</img-wrapper>

上传成功后，我们会得到如下的结果：

```js
[
  {
    key: 'one',
    id: 13,
    url:
      'http://localhost:5000/assets/2019/05/19/3428c02f-dfb5-47a0-82cf-2d6c05285473.png'
  },
  {
    key: 'two',
    id: 14,
    url:
      'http://localhost:5000/assets/2019/05/19/bfcff6f4-8dd7-4dd8-af9d-660781d0e076.jpg'
  }
];
```

由于上传了两个文件，因此我们得到了两个元素的数组，它们的结构如下：

| name |         说明          |  类型  |
| ---- | :-------------------: | :----: |
| key  |    文件上传的 key     | string |
| id   | 文件存储到数据库的 id | string |
| url  |     可访问的 url      | string |

:::tip

系统会自动帮助我们上传的文件做 md5，因此你大可不必担心文件重复上传，如果你上传了
重复的文件，服务器会返回已传文件的数据。

:::

<RightMenu />

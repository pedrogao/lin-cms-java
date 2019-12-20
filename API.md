# api 文档

## admin部分

### getAllPermissions 查询所有可分配的权限

url: /cms/admin/permission
method: get
result:

```json
{
    "用户": [
        {
            "id": 6,
            "name": "查询自己拥有的权限",
            "module": "用户"
        },
        {
            "id": 7,
            "name": "查询自己信息",
            "module": "用户"
        }
    ],
    "图书": [
        {
            "id": 8,
            "name": "删除图书",
            "module": "图书"
        }
    ],
    "信息": [
        {
            "id": 4,
            "name": "测试日志记录",
            "module": "信息"
        },
        {
            "id": 5,
            "name": "查看lin的信息",
            "module": "信息"
        }
    ],
    "日志": [
        {
            "id": 1,
            "name": "查询所有日志",
            "module": "日志"
        },
        {
            "id": 2,
            "name": "搜索日志",
            "module": "日志"
        },
        {
            "id": 3,
            "name": "查询日志记录的用户",
            "module": "日志"
        }
    ]
}
```

### getUsers 查询所有用户

url: /cms/admin/users
method: get
param: 

|  name     | required  |  default |  含义                        ｜
| --------- |:---------:| --------:|: ---------------------------:|
| group_id  | false     |    无    | 分组id，传入后获得该分组的用户  ｜
| count     | false     |   10     |          分页数              ｜
| page      | false     |    0     |          分页值              ｜


result:

```json
{
    "total": 2,
    "items": [
        {
            "id": 1,
            "username": "root",
            "nickname": "root",
            "avatar": null,
            "email": null
        },
        {
            "id": 2,
            "username": "pedro",
            "nickname": null,
            "avatar": null,
            "email": null
        }
    ],
    "page": 0,
    "count": 10
}
```

### changeUserPassword 修改用户密码

url: /cms/admin/user/{id}/password
method: put
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| new_password     | true      |    无    |     新密码               ｜
| confirm_password | true      |    无    |     确认密码              ｜

```json
{
	"new_password": "147258",
	"confirm_password": "147258"
}
```
result:

```json
{
    "error_code": 2,
    "msg": "密码修改成功",
    "url": "/cms/admin/user/2/password"
}
```

### deleteUser 删除用户

url: /cms/admin/user/{id}
method: delete

result:

```json
{
    "error_code": 3,
    "msg": "删除用户成功",
    "url": "/cms/admin/user/2"
}
```


### updateUser 管理员更新用户信息

url: /cms/admin/user/{id}
method: put
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| group_ids        | false     |    无    |    分字id，数组，如 [1,2,3]   ｜

```json
{
	"group_ids": [2]
}
```
result:

```json
{
    "error_code": 4,
    "msg": "更新用户成功",
    "url": "/cms/admin/user/2"
}
```

### getGroups 查询所有权限组及其权限

url: /cms/admin/group
method: get
param: 

|  name     | required  |  default |  含义                        ｜
| --------- |:---------:| --------:|: ---------------------------:|
| count     | false     |   10     |          分页数              ｜
| page      | false     |    0     |          分页值              ｜


result:

```json
{
    "total": 2,
    "items": [
        {
            "id": 1,
            "name": "root",
            "info": "超级用户组"
        },
        {
            "id": 2,
            "name": "guest",
            "info": "游客组"
        }
    ],
    "page": 0,
    "count": 10
}
```

### getAllGroup 查询所有权限组

url: /cms/admin/group/all
method: get

result:

```json
[
    {
        "id": 1,
        "name": "root",
        "info": "超级用户组"
    },
    {
        "id": 2,
        "name": "guest",
        "info": "游客组"
    }
]
```


### getGroup 查询一个权限组及其权限

url: /cms/admin/group/{id}
method: get

result:

```json
{
    "id": 1,
    "name": "root",
    "info": "超级用户组",
    "permissions": []
}
```

### createGroup 新建权限组

url: /cms/admin/group
method: post
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| name             | true      |    无    |   分组名称              ｜
| info             | true      |    无    |   分组信息              ｜
| permission_ids   | false     |    无    |   权限id，例如[1,2,3]   ｜

```json
{
	"name": "武庚纪",
	"info": "玄机科技",
	"permission_ids": [1, 2]
}
```
result:

```json
{
    "error_code": 13,
    "msg": "新建分组成功",
    "url": "/cms/admin/group"
}
```

### updateGroup 更新一个权限组

url: /cms/admin/group/{id}
method: put
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| name             | false     |    无    |   分组名称              ｜
| info             | false     |    无    |   分组信息              ｜

```json
{
	"name": "天行九歌",
	"info": "玄机科技"
}
```
result:

```json
{
    "error_code": 5,
    "msg": "更新分组成功",
    "url": "/cms/admin/group/5"
}
```


### deleteGroup 删除一个权限组

url: /cms/admin/group/{id}
method: delete

result:

```json
{
    "error_code": 6,
    "msg": "删除分组成功",
    "url": "/cms/admin/group/5"
}
```


### dispatchPermission 分配单个权限

url: /cms/admin/permission/dispatch
method: post
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| group_id         | true      |    无    |   分组id                ｜
| permission_id    | true      |    无    |   权限id                ｜

```json
{
	"group_id": 5,
	"permission_id": 3
}
```
result:

```json
{
    "error_code": 7,
    "msg": "添加权限成功",
    "url": "/cms/admin/permission/dispatch"
}
```

### dispatchPermissions 分配多个权限

url: /cms/admin/permission/dispatch/batch
method: post
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| group_id          | true      |    无    |   分组id                ｜
| permission_ids    | false     |    无    |   权限id                ｜

```json
{
	"group_id": 5,
	"permission_ids": [4,5]
}
```
result:

```json
{
    "error_code": 7,
    "msg": "添加权限成功",
    "url": "/cms/admin/permission/dispatch/batch"
}
```

### removePermissions 删除多个权限

url: /cms/admin/permission/dispatch/remove
method: post
body: 

|  name            | required  |  default |  含义                        ｜
| -----------------|:---------:| --------:|: ---------------------------:|
| group_id          | true      |    无    |   分组id                ｜
| permission_ids    | false     |    无    |   权限id                ｜

```json
{
	"group_id": 5,
	"permission_ids": [4,5]
}
```
result:

```json
{
    "error_code": 8,
    "msg": "删除权限成功",
    "url": "/cms/admin/permission/remove"
}
```




```json
{
    "用户": {
        "查询自己拥有的权限": [
            "com.lin.cms.demo.controller.cms.UserController$$EnhancerBySpringCGLIB$$9d0819dc#getPermissions",
            "com.lin.cms.demo.controller.cms.UserController#getPermissions"
        ],
        "查询自己信息": [
            "com.lin.cms.demo.controller.cms.UserController$$EnhancerBySpringCGLIB$$9d0819dc#getInformation",
            "com.lin.cms.demo.controller.cms.UserController#getInformation"
        ]
    },
    "图书": {
        "删除图书": [
            "com.lin.cms.demo.controller.v1.BookController#deleteBook",
            "com.lin.cms.demo.controller.v1.BookController$$EnhancerBySpringCGLIB$$5fe179c#deleteBook"
        ]
    },
    "信息": {
        "查看lin的信息": [
            "com.lin.cms.demo.controller.cms.TestController$$EnhancerBySpringCGLIB$$57413a51#getTestInfo",
            "com.lin.cms.demo.controller.cms.TestController#getTestInfo"
        ],
        "测试日志记录": [
            "com.lin.cms.demo.controller.cms.TestController#getTestMsg",
            "com.lin.cms.demo.controller.cms.TestController$$EnhancerBySpringCGLIB$$57413a51#getTestMsg"
        ]
    },
    "日志": {
        "查询所有日志": [
            "com.lin.cms.demo.controller.cms.LogController$$EnhancerBySpringCGLIB$$da618565#getLogs",
            "com.lin.cms.demo.controller.cms.LogController#getLogs"
        ],
        "查询日志记录的用户": [
            "com.lin.cms.demo.controller.cms.LogController#getUsers",
            "com.lin.cms.demo.controller.cms.LogController$$EnhancerBySpringCGLIB$$da618565#getUsers"
        ],
        "搜索日志": [
            "com.lin.cms.demo.controller.cms.LogController#searchLogs",
            "com.lin.cms.demo.controller.cms.LogController$$EnhancerBySpringCGLIB$$da618565#searchLogs"
        ]
    }
}
```
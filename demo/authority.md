# 权限v2总览

## 模式


```sh

--------------------------------------------
|                                          |
|             |--------|                   |
|  user  -->  |  role  |  -->              |
|    \        |--------|                   |
|     \                        permission  |
|      \ ->   role-admin  -->              |
|                                          |
--------------------------------------------
```

- 用户不能直接与权限(permission)关联，必须通过`role`这个桥梁间接的拥有权限
- 超级管理员(super/root)同样是一个角色，一个凌驾于所有的角色
- 当一个用户被创建时，默认拥有`guest(游客)`角色，该角色拥有基本的访问和登陆
- role(角色)是权限的集合，是一个抽象的概念
- 每个`role`可以存在一个`role-admin`的附属角色，`role-admin`不参与角色树的构建，它只是`role`的附庸
- `role-admin`单独被另外一个表管理
- 记住：但一个用户(user)被分配为某一个`role`时，例如`roleA`，如果该用户想要成为`roleA`的管理员时，必须先成为`roleA`
- `role`有层级(level)之分，上级的管理员(admin)，普通的角色无法管理其它角色，可以管理下级的角色
- 因为角色存在越级管理，如第一级的超级管理员可以直接给第三级的管理员分配比第二级管理员更多的权限，但2级管理员仍然比3级管理员的等级高，2级可以管理3级，但3级不能管理2级。角色的等级不由权限的多寡决定。
- 每个角色被创建时(除super和guest)，会选择性的创建该角色的附属admin角色，都会在`permission`表中创建该角色的操作权
- 操作权包括：下级角色创建权(create)、下级角色更新权(update)、下级角色删除权(delete)、权限分配权(grant)，权限回收权(recycle)。
- admin在分配，回收权限的时候只能以自己的权限为基础，如果自己都没有的权限不能分配给下级和同级，不能回收自己都没有的权限
- 上级拥有下级的绝对管理权
- 上级在创建下级角色时，下级的管理员角色时可选
- 角色的管理员角色，即角色的附属节点，可以有多个

## 数据库设计

见`schema.sql`

## 具体细节

#### 验证用户是否具有API的访问权限

根据url得到name和module;根据jwt得到user_id -> 通过user_id得到user -> 查询user所拥有的roles -> 根据roles查询是否存在module和name的权限

#### 新建role

判断user是否拥有新建role的权限 -> 是否新建admin角色 -> 新建role，admin-role，并且在permission中新建关于role默认的权限
且role的上级所有admin-role立即拥有role的默认权限???

#### 关于role-admin操作role

如果role-admin是role的附属节点，即可操作role里面的所有角色，也可操作role下级的角色

#### role-admin如何分配权限

role-admin可分配的权限仅仅只能是自己拥有的权限，不可分配没有的权限


#### 关于角色如何鉴权？

lin-user-role,lin-role-permission,lin-admin-role-permission

先得到用户 -> 判断是不是超级管理员 -> 拿到用户的角色  -> 判断是否有管理员角色  -> 无，判断角色中是否有相关联的权限
                                                -> 有，直接通过
                                            
select p.name from permission where 
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 文件表
-- ----------------------------
DROP TABLE IF EXISTS `lin_file`;
CREATE TABLE `lin_file`
(
    `id`        int(10) unsigned NOT NULL AUTO_INCREMENT,
    `path`      varchar(500)     NOT NULL,
    `type`      tinyint(4)       NOT NULL DEFAULT '1' COMMENT '1 local，其他表示其他地方',
    `name`      varchar(100)     NOT NULL,
    `extension` varchar(50)               DEFAULT NULL,
    `size`      int(11)                   DEFAULT NULL,
    `md5`       varchar(40)               DEFAULT NULL COMMENT 'md5值，防止上传重复文件',
    PRIMARY KEY (`id`),
    UNIQUE KEY `md5` (`md5`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 日志表
-- ----------------------------
DROP TABLE IF EXISTS `lin_log`;
CREATE TABLE `lin_log`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `message`     varchar(450)              DEFAULT NULL,
    `user_id`     int(10) unsigned NOT NULL,
    `username`    varchar(24)               DEFAULT NULL,
    `status_code` int(11)                   DEFAULT NULL,
    `method`      varchar(20)               DEFAULT NULL,
    `path`        varchar(50)               DEFAULT NULL,
    `permission`  varchar(100)              DEFAULT NULL,
    `time`        datetime(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 权限表
-- ----------------------------
DROP TABLE IF EXISTS `lin_permission`;
CREATE TABLE `lin_permission`
(
    `id`     int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`   varchar(60)      NOT NULL COMMENT '权限名称，例如：访问首页',
    `module` varchar(50)      NOT NULL COMMENT '权限所属模块，例如：人员管理',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `lin_role`;
CREATE TABLE `lin_role`
(
    `id`    int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`  varchar(60)      NOT NULL COMMENT '角色名称，例如：搬砖者',
    `path`  varchar(255)     NOT NULL COMMENT '角色路径，例如：/root/boy',
    `level` int(11)          NOT NULL COMMENT '角色登记，例如：2，root用户默认为1级，其它的依次向下递增',
    `info`  varchar(255) DEFAULT NULL COMMENT '角色信息：例如：搬砖的人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 角色-权限表
-- ----------------------------
DROP TABLE IF EXISTS `lin_role_permission`;
CREATE TABLE `lin_role_permission`
(
    `id`            int(10) unsigned NOT NULL AUTO_INCREMENT,
    `role_id`       int(10) unsigned NOT NULL COMMENT '角色id',
    `permission_id` int(10) unsigned NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`),
    KEY `role_id_permission_id` (`role_id`, `permission_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `lin_user`;
CREATE TABLE `lin_user`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `username`    varchar(24)      NOT NULL COMMENT '用户名，唯一',
    `nickname`    varchar(24)      NOT NULL,
    `avatar`      varchar(500)              DEFAULT NULL COMMENT '头像url',
    `email`       varchar(100)              DEFAULT NULL,
    `password`    varchar(100)              DEFAULT NULL,
    `create_time` datetime(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)               DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- 插入超级管理员
-- 插入root角色
-- ----------------------------
BEGIN;
INSERT INTO `lin_user`
VALUES (1, 'super', 'super', NULL, NULL,
        'pbkdf2sha256:64000:18:24:n:yUnDokcNRbwILZllmUOItIyo9MnI00QW:6ZcPf+sfzyoygOU8h/GSoirF',
        '2019-06-10 15:04:35.000', '2019-07-07 10:22:15.000', NULL);

INSERT INTO `lin_role`
VALUES (1, 'root', '/root', 1, 'root用户');
COMMIT;

-- ----------------------------
-- 用户-角色表
-- ----------------------------
DROP TABLE IF EXISTS `lin_user_role`;
CREATE TABLE `lin_user_role`
(
    `id`      int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
    `role_id` int(10) unsigned NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`),
    KEY `user_id_role_id` (`user_id`, `role_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- MOCK 数据
-- ----------------------------
BEGIN;

INSERT INTO `lin_user`
VALUES (2, 'pedro', 'pedro', NULL, '1312342604@qq.com',
        'pbkdf2sha256:64000:18:24:n:yUnDokcNRbwILZllmUOItIyo9MnI00QW:6ZcPf+sfzyoygOU8h/GSoirF',
        '2019-06-10 15:04:35.000', '2019-07-07 10:22:15.000', NULL);

INSERT INTO `lin_role`
VALUES (2, 'guest', '/root/guest', 2, '游客');

INSERT INTO `lin_user_role`
VALUES (1, 2, 2);

INSERT INTO `lin_permission`
VALUES (1, '访问首页', '访问');

INSERT INTO `lin_role_permission`
VALUES (1, 2, 1);

ROLLBACK;

-- ----------------------------
-- 测试用户是否有无权限
-- ----------------------------
BEGIN;
--
-- 从角色找
--
SELECT *
from `lin_role_permission`
WHERE role_id in (SELECT role_id FROM `lin_user_role` WHERE user_id = 1);

COMMIT;

-- ----------------------------
-- 测试用户登记
-- /root /root/admin
-- ----------------------------

BEGIN;

SELECT *
FROM lin_role
WHERE path REGEXP '^/root'
  AND level > 1;

COMMIT;
/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : lin-cms

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 24/10/2019 21:50:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lin_file
-- ----------------------------
DROP TABLE IF EXISTS `lin_file`;
CREATE TABLE `lin_file`
(
    `id`        int(11) unsigned NOT NULL AUTO_INCREMENT,
    `path`      varchar(500)     NOT NULL,
    `type`      tinyint(4)       NOT NULL DEFAULT '1' COMMENT '1 local，其他表示其他地方',
    `name`      varchar(100)     NOT NULL,
    `extension` varchar(50)               DEFAULT NULL,
    `size`      int(11)                   DEFAULT NULL,
    `md5`       varchar(40)               DEFAULT NULL COMMENT 'md5值，防止上传重复文件',
    PRIMARY KEY (`id`),
    UNIQUE KEY `md5` (`md5`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lin_log
-- ----------------------------
DROP TABLE IF EXISTS `lin_log`;
CREATE TABLE `lin_log`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT,
    `message`     varchar(450)              DEFAULT NULL,
    `user_id`     int(11) unsigned NOT NULL,
    `user_name`   varchar(20)               DEFAULT NULL,
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
-- Table structure for lin_permission
-- ----------------------------
DROP TABLE IF EXISTS `lin_permission`;
CREATE TABLE `lin_permission`
(
    `id`     int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`   varchar(60)      NOT NULL,
    `module` varchar(50)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lin_role
-- ----------------------------
DROP TABLE IF EXISTS `lin_role`;
CREATE TABLE `lin_role`
(
    `id`    int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`  varchar(60)      NOT NULL,
    `path`  varchar(255)     NOT NULL,
    `level` int(11)          NOT NULL COMMENT 'root用户默认为1级，其它的依次向下递增',
    `info`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lin_role_admin
-- ----------------------------
DROP TABLE IF EXISTS `lin_role_admin`;
CREATE TABLE `lin_role_admin`
(
    `id`    int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`  varchar(60)      NOT NULL,
    `path`  varchar(255)     NOT NULL,
    `level` int(11)          NOT NULL COMMENT 'root用户默认为1级，其它的依次向下递增',
    `info`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lin_role_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `lin_role_admin_permission`;
CREATE TABLE `lin_role_admin_permission`
(
    `id`            int(10) unsigned NOT NULL AUTO_INCREMENT,
    `role_id`       int(10) unsigned NOT NULL,
    `permission_id` int(10) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `role_id_permission_id` (`role_id`, `permission_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `lin_role_permission`;
CREATE TABLE `lin_role_permission`
(
    `id`            int(10) unsigned NOT NULL AUTO_INCREMENT,
    `role_id`       int(10) unsigned NOT NULL,
    `permission_id` int(10) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `role_id_permission_id` (`role_id`, `permission_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lin_user
-- ----------------------------
DROP TABLE IF EXISTS `lin_user`;
CREATE TABLE `lin_user`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `nickname`    varchar(24)      NOT NULL,
    `avatar`      varchar(500)              DEFAULT NULL COMMENT '头像url',
    `email`       varchar(100)              DEFAULT NULL,
    `password`    varchar(100)              DEFAULT NULL,
    `create_time` datetime(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)               DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `nickname` (`nickname`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Records of lin_user
-- ----------------------------
BEGIN;
INSERT INTO `lin_user`
VALUES (1, 'super', NULL, '1312342604@qq.com',
        'pbkdf2sha256:64000:18:24:n:yUnDokcNRbwILZllmUOItIyo9MnI00QW:6ZcPf+sfzyoygOU8h/GSoirF',
        '2019-06-10 15:04:35.000', '2019-07-07 10:22:15.000', NULL);
COMMIT;

-- ----------------------------
-- Table structure for lin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `lin_user_role`;
CREATE TABLE `lin_user_role`
(
    `id`      int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` int(10) unsigned NOT NULL,
    `role_id` int(10) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id_role_id` (`user_id`, `role_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : sleeve

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 13/08/2019 22:41:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lin_auth
-- ----------------------------
DROP TABLE IF EXISTS `lin_auth`;
CREATE TABLE `lin_auth`
(
    `id`       int(11) unsigned NOT NULL AUTO_INCREMENT,
    `group_id` int(11) unsigned DEFAULT NULL,
    `auth`     varchar(60)      DEFAULT NULL,
    `module`   varchar(50)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4;

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
    `md5`       varchar(40)               DEFAULT NULL COMMENT '图片md5值，防止上传重复图片',
    PRIMARY KEY (`id`),
    UNIQUE KEY `md5` (`md5`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_group
-- ----------------------------
DROP TABLE IF EXISTS `lin_group`;
CREATE TABLE `lin_group`
(
    `id`   int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(60)  DEFAULT NULL,
    `info` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4;

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
    `authority`   varchar(100)              DEFAULT NULL,
    `time`        datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_user
-- ----------------------------
DROP TABLE IF EXISTS `lin_user`;
CREATE TABLE `lin_user`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT,
    `nickname`    varchar(24)      NOT NULL,
    `avatar`      varchar(500)              DEFAULT NULL COMMENT '头像url',
    `admin`       tinyint(4)       NOT NULL DEFAULT '1',
    `active`      tinyint(4)       NOT NULL DEFAULT '1',
    `email`       varchar(100)              DEFAULT NULL,
    `group_id`    int(11) unsigned          DEFAULT NULL,
    `password`    varchar(100)              DEFAULT NULL,
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `delete_time` datetime                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `nickname` (`nickname`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;


INSERT INTO `lin_user`(`id`, `nickname`, `avatar`, `admin`, `active`, `email`, `group_id`, `password`, `create_time`,
                       `update_time`, `delete_time`)
VALUES (2, 'super', NULL, 2, 1, '1312342604@qq.com', 1,
        'pbkdf2sha256:64000:18:24:n:yUnDokcNRbwILZllmUOItIyo9MnI00QW:6ZcPf+sfzyoygOU8h/GSoirF', '2019-06-10 15:04:35',
        '2019-07-07 10:22:15', NULL);

SET FOREIGN_KEY_CHECKS = 1;

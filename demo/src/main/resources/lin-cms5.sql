SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `title`       varchar(50) NOT NULL,
    `author`      varchar(30)   DEFAULT NULL,
    `summary`     varchar(1000) DEFAULT NULL,
    `image`       varchar(100)  DEFAULT NULL,
    `create_time` datetime(3) NOT NULL,
    `update_time` datetime(3) NOT NULL,
    `delete_time` datetime(3)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_auth
-- ----------------------------
DROP TABLE IF EXISTS `lin_auth`;
CREATE TABLE `lin_auth`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `group_id` int(11)     DEFAULT NULL,
    `auth`     varchar(60) DEFAULT NULL,
    `module`   varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_file
-- ----------------------------
DROP TABLE IF EXISTS `lin_file`;
CREATE TABLE `lin_file`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT,
    `path`      varchar(500) NOT NULL,
    `type`      tinyint(4)   NOT NULL DEFAULT '1' COMMENT '1 local，其他表示其他地方',
    `name`      varchar(100) NOT NULL,
    `extension` varchar(50)           DEFAULT NULL,
    `size`      int(11)               DEFAULT NULL,
    `md5`       varchar(40)           DEFAULT NULL COMMENT '图片md5值，防止上传重复图片',
    PRIMARY KEY (`id`),
    UNIQUE KEY `md5` (`md5`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_group
-- ----------------------------
DROP TABLE IF EXISTS `lin_group`;
CREATE TABLE `lin_group`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(60)  DEFAULT NULL,
    `info` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_log
-- ----------------------------
DROP TABLE IF EXISTS `lin_log`;
CREATE TABLE `lin_log`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `message`     varchar(450) DEFAULT NULL,
    `user_id`     int(11)     NOT NULL,
    `user_name`   varchar(20)  DEFAULT NULL,
    `status_code` int(11)      DEFAULT NULL,
    `method`      varchar(20)  DEFAULT NULL,
    `path`        varchar(50)  DEFAULT NULL,
    `authority`   varchar(100) DEFAULT NULL,
    `time`        datetime(3) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for lin_user
-- ----------------------------
DROP TABLE IF EXISTS `lin_user`;
CREATE TABLE `lin_user`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `nickname`    varchar(24) NOT NULL,
    `avatar`      varchar(500)         DEFAULT NULL COMMENT '头像url',
    `admin`       tinyint(4)  NOT NULL DEFAULT '1',
    `active`      tinyint(4)  NOT NULL DEFAULT '1',
    `email`       varchar(100)         DEFAULT NULL,
    `group_id`    int(11)              DEFAULT NULL,
    `password`    varchar(100)         DEFAULT NULL,
    `create_time` datetime(3) NOT NULL,
    `update_time` datetime(3) NOT NULL,
    `delete_time` datetime(3)          DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `nickname` (`nickname`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

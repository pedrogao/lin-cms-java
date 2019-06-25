/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : lin-cms2

 Target Server Type    : MySQL
 Target Server Version : 50724
 FilePO Encoding         : 65001

 Date: 19/06/2019 09:31:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for poem
-- ----------------------------
DROP TABLE IF EXISTS `poem`;
CREATE TABLE `poem`
(
  `id`          int(11)     NOT NULL AUTO_INCREMENT,
  `title`       varchar(50) NOT NULL,
  `author`      varchar(50)          DEFAULT '未名',
  `dynasty`     varchar(50)          DEFAULT '未知',
  `content`     text        NOT NULL,
  `image`       varchar(255)         DEFAULT '',
  `delete_time` datetime             DEFAULT NULL,
  `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of poem
-- ----------------------------
BEGIN;
INSERT INTO `poem`
VALUES (1, '生查子·元夕', '欧阳修', '宋代', '去年元夜时/花市灯如昼/月上柳梢头/人约黄昏后|今年元夜时/月与灯依旧/不见去年人/泪湿春衫袖',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
INSERT INTO `poem`
VALUES (2, '临江仙·送钱穆父', '苏轼', '宋代', '一别都门三改火/天涯踏尽红尘/依然一笑作春温/无波真古井/有节是秋筠|惆怅孤帆连夜发/送行淡月微云/尊前不用翠眉颦/人生如逆旅/我亦是行人',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
INSERT INTO `poem`
VALUES (3, '春望词四首', '薛涛', '唐代',
        '花开不同赏/花落不同悲/欲问相思处/花开花落时/揽草结同心/将以遗知音/春愁正断绝/春鸟复哀吟/风花日将老/佳期犹渺渺/不结同心人/空结同心草/那堪花满枝/翻作两相思/玉箸垂朝镜/春风知不知',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
INSERT INTO `poem`
VALUES (4, '长相思', '纳兰性德', '清代', '山一程/水一程/身向榆关那畔行/夜深千帐灯|风一更/雪一更/聒碎乡心梦不成/故园无此声',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
INSERT INTO `poem`
VALUES (5, '离思五首·其四', '元稹', '唐代', '曾经沧海难为水/除却巫山不是云/取次花丛懒回顾/半缘修道半缘君',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
INSERT INTO `poem`
VALUES (6, '浣溪沙', '晏殊', '宋代', '一曲新词酒一杯/去年天气旧亭台/夕阳西下几时回|无可奈何花落去/似曾相识燕归来/小园香径独徘徊',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
INSERT INTO `poem`
VALUES (7, '浣溪沙', '纳兰性德', '清代', '残雪凝辉冷画屏/落梅横笛已三更/更无人处月胧明|我是人间惆怅客/知君何事泪纵横/断肠声里忆平生',
        'http://yanlan.oss-cn-shenzhen.aliyuncs.com/gqmgbmu06yO2zHD.png', NULL, '2019-04-04 12:25:54',
        '2019-04-04 12:25:54');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

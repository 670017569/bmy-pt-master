/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 172.17.0.1:3306
 Source Schema         : bmy

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 20/01/2021 16:57:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic` (
  `id` bigint(20) NOT NULL COMMENT '动态id',
  `uid` bigint(20) NOT NULL COMMENT 'FK用户id',
  `region` int(11) DEFAULT '0' COMMENT '位置信息',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `praises` int(11) DEFAULT '0' COMMENT '点赞数',
  `clicks` int(11) DEFAULT '0' COMMENT '点击数',
  `comments` int(11) DEFAULT '0' COMMENT '评论数',
  `publish_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dynamic_pic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_pic`;
CREATE TABLE `dynamic_pic` (
  `id` bigint(20) NOT NULL COMMENT 'PK主键',
  `dyn_id` bigint(20) NOT NULL COMMENT '用户id',
  `url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dynamic_praise
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_praise`;
CREATE TABLE `dynamic_praise` (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) DEFAULT NULL,
  `dyn_id` bigint(20) DEFAULT NULL,
  `praise_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `canceled` tinyint(1) DEFAULT '0',
  `is_read` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_dynamic_praise_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` bigint(20) NOT NULL COMMENT '关注信息主键',
  `uid` bigint(20) DEFAULT NULL COMMENT 'FK用户uid',
  `fuid` bigint(20) DEFAULT NULL COMMENT '被关注的用户id',
  `canceled` tinyint(1) DEFAULT '0' COMMENT '是否取消了关注',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '关注时间',
  `is_read` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for official_news
-- ----------------------------
DROP TABLE IF EXISTS `official_news`;
CREATE TABLE `official_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `thumb_url` varchar(300) DEFAULT NULL COMMENT '缩略图',
  `url` varchar(300) DEFAULT NULL COMMENT '链接url',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `media_id` varchar(50) NOT NULL,
  `digest` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `official_news_media_id_uindex` (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=378 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限',
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `url` varchar(255) NOT NULL COMMENT '授权路径',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0',
  `code` varchar(10) NOT NULL,
  `name` varchar(191) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '等级',
  `hot` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否热门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3295 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父角色',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rid` bigint(20) NOT NULL COMMENT '角色 ID',
  `pid` bigint(20) NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

-- ----------------------------
-- Table structure for standard
-- ----------------------------
DROP TABLE IF EXISTS `standard`;
CREATE TABLE `standard` (
  `id` bigint(20) NOT NULL COMMENT 'PK',
  `thumb` mediumtext COMMENT '缩略图',
  `url` varchar(300) DEFAULT NULL COMMENT '链接url',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `subtitle` varchar(100) DEFAULT NULL COMMENT '简介/小标题',
  `publish_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `region_id` int(11) DEFAULT NULL COMMENT 'id',
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(24) DEFAULT NULL COMMENT '用户名',
  `wx_openid` varchar(50) DEFAULT NULL COMMENT '微信openid',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '账号状态 0：正常 1：冻结',
  `password` varchar(80) DEFAULT NULL COMMENT '密码，加密存储，默认为空',
  `email` varchar(25) DEFAULT NULL COMMENT '注册邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '注册手机号',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `wxopenid` (`wx_openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户 ID',
  `rid` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1349176107730993153 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL,
  `username` varchar(24) COLLATE utf8_bin NOT NULL,
  `intro` varchar(64) COLLATE utf8_bin DEFAULT '这个人很懒，啥都没留下' COMMENT '用户简介',
  `follows` int(5) unsigned zerofill DEFAULT '00000' COMMENT '关注人数',
  `fans` int(7) unsigned zerofill NOT NULL DEFAULT '0000000' COMMENT '粉丝数',
  `praised` int(8) unsigned zerofill NOT NULL DEFAULT '00000000' COMMENT '总获赞数',
  `dynamics` int(5) unsigned zerofill NOT NULL DEFAULT '00000' COMMENT '动态发布数',
  `topics` int(5) unsigned zerofill NOT NULL DEFAULT '00000' COMMENT '发布话题数',
  `brick` int(8) unsigned zerofill NOT NULL DEFAULT '00000000' COMMENT '砖值',
  `integration` int(8) unsigned zerofill NOT NULL DEFAULT '00000000' COMMENT '积分',
  `sign_times` int(5) unsigned zerofill NOT NULL DEFAULT '00000' COMMENT '累计签到次数',
  `sign_today` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '今天是否已经签到0:未签到 1：已签到',
  `last_sign_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录系统时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userinfo_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for wx_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `wx_userinfo`;
CREATE TABLE `wx_userinfo` (
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `province` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '省份',
  `nick_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `language` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '语言',
  `gender` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `country` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '国家',
  `city` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `avatar_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像链接',
  PRIMARY KEY (`uid`),
  CONSTRAINT `USER_ID` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Event structure for event_signin
-- ----------------------------
DROP EVENT IF EXISTS `event_signin`;
delimiter ;;
CREATE EVENT `event_signin`
ON SCHEDULE
EVERY '1' DAY STARTS '2021-01-11 08:00:00'
DO update userinfo set userinfo.sign_today=0
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;

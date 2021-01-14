/*
 Navicat Premium Data Transfer

 Source Server         : bbs.bmy8.xyz
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : bbs.bmy8.xyz:3306
 Source Schema         : bmy

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/11/2020 18:19:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `acid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `title` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '标题',
  `address` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '活动地点',
  `states` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '活动状态',
  `thumb` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '缩略图',
  `content` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '内容',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '活动开始时间',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '活动结束时间',
  `start_enter_time` datetime(0) NULL DEFAULT NULL COMMENT '报名开始时间',
  `stop_enter_time` datetime(0) NULL DEFAULT NULL COMMENT '报名结束时间',
  `join_total` int(0) NULL DEFAULT NULL COMMENT '可参与总人数',
  `join_remain` int(0) NULL DEFAULT NULL COMMENT '剩余参与名额',
  PRIMARY KEY (`acid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_paticipant
-- ----------------------------
DROP TABLE IF EXISTS `activity_paticipant`;
CREATE TABLE `activity_paticipant`  (
  `acpid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `acid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `phone` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '联系电话',
  `real_name` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '真实名称',
  `states` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '参与者状态',
  `remark` varchar(1000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`acpid`) USING BTREE,
  INDEX `acid`(`acid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81907658902474753 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `addrid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `nick_name` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '联系人名称',
  `city` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '省市',
  `address` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '具体地址',
  `phone` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '联系电话',
  `is_default` int(0) NULL DEFAULT 0 COMMENT '是否为默认地址',
  PRIMARY KEY (`addrid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81900966299045889 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '区划信息id',
  `pid` int(0) NULL DEFAULT NULL COMMENT '父级挂接id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区划编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区划名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0 正常 -2 删除 -1 停用',
  `level` tinyint(1) NULL DEFAULT NULL COMMENT '级次id 0:省/自治区/直辖市 1:市级 2:县级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3221 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `chatid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `touserid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `is_read` int(0) NULL DEFAULT 0 COMMENT '是否已读',
  `text` varchar(2000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '聊天正文',
  `pic` varchar(200) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '聊天图片',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '聊天时间',
  PRIMARY KEY (`chatid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84685075823722497 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for click
-- ----------------------------
DROP TABLE IF EXISTS `click`;
CREATE TABLE `click`  (
  `clickid` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` int(0) NULL DEFAULT NULL COMMENT 'FK',
  `toid` int(0) NULL DEFAULT NULL COMMENT '对应的动态/话题等的主键',
  `type` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '浏览类型',
  `click_time` datetime(0) NULL DEFAULT NULL COMMENT '浏览时间',
  PRIMARY KEY (`clickid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS comment;
CREATE TABLE `comment`  (
  `cmid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `touserid` bigint(0) NULL DEFAULT NULL COMMENT '指向一个用户(二级评论需要的字段)',
  `tocmid` bigint(0) NULL DEFAULT NULL COMMENT '指向一条评论(二级评论需要的字段)',
  `owneruserid` bigint(0) NULL DEFAULT NULL COMMENT '话题/动态主人的userid',
  `is_read` enum('1','0') CHARACTER SET utf8mb4  NULL DEFAULT '0' COMMENT '是否已读',
  `toid` bigint(0) NULL DEFAULT NULL COMMENT '指向动态话题等主键',
  `type` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '评论分类',
  `states` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '状态',
  `content` varchar(2000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '评论内容',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`cmid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115449896957648897 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for credit
-- ----------------------------
DROP TABLE IF EXISTS `credit`;
CREATE TABLE `credit`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userid` int(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  `event` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '获取积分的原因',
  `gain_date` datetime(0) NULL DEFAULT NULL COMMENT '获取积分的时间',
  `expire_date` datetime(0) NULL DEFAULT NULL COMMENT '积分过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic`  (
  `dyid` bigint(0) NOT NULL COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `content` varchar(2000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '内容',
  `city` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '所在省市',
  `states` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '状态',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `likes` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '点赞数',
  `clicks` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '点击数',
  `cms` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '评论数',
  `pic1` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic2` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic3` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic4` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic5` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic6` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic7` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic8` mediumtext CHARACTER SET utf8mb4  NULL,
  `pic9` mediumtext CHARACTER SET utf8mb4  NULL,
  PRIMARY KEY (`dyid`) USING BTREE,
  INDEX `dynamic_ibfk_1`(`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `fvid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `contentid` bigint(0) NULL DEFAULT NULL COMMENT '话题/动态等的主键',
  `type` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '分类',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '加入收藏时间',
  PRIMARY KEY (`fvid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `foid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `touserid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  PRIMARY KEY (`foid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  INDEX `to_userid`(`touserid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115466047431385089 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `gid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `gname` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '商品名称',
  `states` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '状态',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品的市场价',
  `credit` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分需求量',
  `cms` int(0) NULL DEFAULT 0 COMMENT '评论数',
  `sale` int(0) NULL DEFAULT 0 COMMENT '销量',
  `description` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '商品描述, 图文混排',
  `thumb` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '在首页展示的缩略图',
  `pic1` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '配图1',
  `pic2` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '配图2',
  `pic3` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '配图3',
  `pic4` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '配图4',
  `pic5` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '配图5',
  `online_time` datetime(0) NULL DEFAULT NULL COMMENT '上架时间',
  `offline_time` datetime(0) NULL DEFAULT NULL COMMENT '下架时间',
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12352 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods_param
-- ----------------------------
DROP TABLE IF EXISTS `goods_param`;
CREATE TABLE `goods_param`  (
  `gpid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `gid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `stock` bigint(0) NULL DEFAULT NULL COMMENT '库存量',
  `param_group` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '参数组名称',
  `param_value` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '参数值',
  PRIMARY KEY (`gpid`) USING BTREE,
  INDEX `goods_id`(`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `likeid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `touserid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `is_read` int(0) NULL DEFAULT 0 COMMENT '是否已读',
  `like_num` int(0) NULL DEFAULT 1 COMMENT '点赞次数',
  `toid` int(0) NULL DEFAULT NULL COMMENT '对应的动态/话题等的主键',
  `type` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '点赞类型',
  `like_time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`likeid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS official_news;
CREATE TABLE `news_link`  (
      `id` bigint(0) NOT NULL COMMENT 'PK',
      `thumb` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '缩略图',
      `url` varchar(1000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '链接url',
      `title` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '标题',
      `subtitle` varchar(1000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '简介/小标题',
      `type` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '分类',
      `publish_time` datetime(0) default current_time COMMENT '发布时间',
      `update_time` datetime(0) default current_time COMMENT '更新时间',
      `remarks` varchar(300) default null,
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `file_link`;
CREATE TABLE `file_link`  (
      `id` bigint(0) NOT NULL COMMENT 'PK',
      `thumb` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '缩略图',
      `url` varchar(1000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '链接url',
      `title` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '标题',
      `subtitle` varchar(1000) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '简介/小标题',
      `type` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '分类',
      `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
      `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
      `region_id` int COMMENT 'id',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `orderid` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `addrid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `gid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `gpid` bigint(0) NULL DEFAULT NULL COMMENT 'FK',
  `buy_num` int(0) NULL DEFAULT NULL COMMENT '购买数量',
  `description` varchar(50) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '描述',
  `states` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '订单状态',
  `buy_time` datetime(0) NULL DEFAULT NULL COMMENT '下单时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '送达时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `credit` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付积分',
  `express_code` varchar(40) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '运单号',
  `express_name` varchar(20) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '快递名称',
  PRIMARY KEY (`orderid`) USING BTREE,
  INDEX `order_ibfk_1`(`userid`) USING BTREE,
  INDEX `order_ibfk_2`(`addrid`) USING BTREE,
  INDEX `goods_order_ibfk_3`(`gpid`) USING BTREE,
  INDEX `goods_order_ibfk_4`(`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90384538446077953 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `tag` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `tpid` bigint(0) NOT NULL AUTO_INCREMENT,
  `userid` bigint(0) NULL DEFAULT NULL ,
  `title` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '标题',
  `thumb` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '缩略图',
  `content` mediumtext CHARACTER SET utf8mb4  NULL COMMENT '内容',
  `type` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '类型',
  `city` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '所在城市',
  `states` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '状态',
  `format` varchar(30) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '格式',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `likes` int(0) NULL DEFAULT 0 COMMENT '点赞数',
  `clicks` int(0) NULL DEFAULT 0 COMMENT '点击数',
  `cms` int(0) NULL DEFAULT 0 COMMENT '评论数',
  PRIMARY KEY (`tpid`) USING BTREE,
  INDEX `topic_ibfk_1`(`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91181601291247617 CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;


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
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 0, 'USER_ADD', '/user/add', '新增用户', '2020-12-12 12:02:19', '2020-12-12 12:02:19');
INSERT INTO `permission` VALUES (2, 0, 'USER_DELETE', '/user/delete', '删除用户', '2020-12-12 12:02:19', '2020-12-12 12:02:19');
INSERT INTO `permission` VALUES (47, 0, 'USER_UPDATE', '/user/update', '更新用户', '2020-12-12 12:02:19', '2020-12-12 12:02:19');
INSERT INTO `permission` VALUES (48, 0, 'USER_QUERY', '/user/query', '查询用户', '2020-12-12 12:02:19', '2020-12-12 12:02:19');
COMMIT;

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `pid` int(11) NOT NULL DEFAULT '0' COMMENT '行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0',
                          `name` varchar(120) NOT NULL DEFAULT '' COMMENT '行政区域名称',
                          `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县',
                          `code` int(11) NOT NULL DEFAULT '0' COMMENT '行政区域编码',
                          PRIMARY KEY (`id`) USING BTREE,
                          KEY `parent_id` (`pid`) USING BTREE,
                          KEY `region_type` (`type`) USING BTREE,
                          KEY `agency_id` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='行政区域表';

-- ----------------------------
-- Records of region
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 0, 'ADMIN', '超级管理员', '2020-12-11 13:14:33', '2020-12-11 13:14:35');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `rid` bigint(20) NOT NULL COMMENT '角色 ID',
                                   `pid` bigint(20) NOT NULL COMMENT '权限 ID',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL COMMENT '用户id',
                        `username` varchar(18) DEFAULT NULL COMMENT '用户名',
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
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '670017569', '68432156133asdasd', 0, '123456', '1111', '15386182102', 0);
INSERT INTO `user` VALUES (1338842507810701312, NULL, '1561321535468413151', 0, NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `uid` bigint(20) NOT NULL COMMENT '用户 ID',
                             `rid` bigint(20) NOT NULL COMMENT '角色 ID',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 1);
COMMIT;

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
-- Records of wx_userinfo
-- ----------------------------
BEGIN;
INSERT INTO `wx_userinfo` VALUES (1, '湖南', '万', '汉语', '男', '中国', '城市', '4652135');
INSERT INTO `wx_userinfo` VALUES (1338842507810701312, '湖北', NULL, NULL, '男', NULL, '武汉', '11111111');
COMMIT;
-- ----------------------------
-- Event structure for event_signin
-- ----------------------------
# DROP EVENT IF EXISTS `event_signin`;
# delimiter ;;
# CREATE EVENT `event_signin`
# ON SCHEDULE
# EVERY '1' DAY STARTS '2020-02-21 00:00:00'
# DO update user set user.signin_today=0
# ;;
# delimiter ;

SET FOREIGN_KEY_CHECKS = 1;

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : liveplatform

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-08-25 11:51:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for video_platform
-- ----------------------------
DROP TABLE IF EXISTS `video_platform`;
CREATE TABLE `video_platform` (
  `video_platform_id` int(32) NOT NULL AUTO_INCREMENT,
  `video_platform` varchar(255) NOT NULL,
  `video_platform_domain` varchar(255) NOT NULL,
  `video_platform_img` varchar(255) NOT NULL,
  PRIMARY KEY (`video_platform_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_platform
-- ----------------------------
INSERT INTO `video_platform` VALUES ('1', '熊猫tv', 'http://www.panda.tv', 'icon0.png');
INSERT INTO `video_platform` VALUES ('2', '斗鱼tv', 'http://www.douyu.com', 'icon1.png');
INSERT INTO `video_platform` VALUES ('3', '战旗tv', 'http://www.zhanqi.tv', 'icon2.png');
INSERT INTO `video_platform` VALUES ('4', '虎牙直播', 'http://www.huya.com', 'icon3.png');
INSERT INTO `video_platform` VALUES ('5', '全民tv', 'http://www.quanming.tv', 'icon4.png');
INSERT INTO `video_platform` VALUES ('6', '龙珠直播', 'http://star.longzhu.com', 'icon5.png');
INSERT INTO `video_platform` VALUES ('7', 'bilibili直播', 'https://live.bilibili.com', 'icon6.png');

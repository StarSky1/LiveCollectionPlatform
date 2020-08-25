/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : liveplatform

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-08-25 11:50:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for video_care
-- ----------------------------
DROP TABLE IF EXISTS `video_care`;
CREATE TABLE `video_care` (
  `userId` int(11) NOT NULL,
  `videoId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `videoId` (`videoId`),
  KEY `userId` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `video_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `videoId` FOREIGN KEY (`videoId`) REFERENCES `video_source` (`video_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for video_category
-- ----------------------------
DROP TABLE IF EXISTS `video_category`;
CREATE TABLE `video_category` (
  `video_type_id` int(32) NOT NULL AUTO_INCREMENT,
  `video_type` varchar(255) NOT NULL,
  `video_type_img` varchar(255) NOT NULL,
  PRIMARY KEY (`video_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for video_host
-- ----------------------------
DROP TABLE IF EXISTS `video_host`;
CREATE TABLE `video_host` (
  `video_host_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `video_host_nickname` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `video_host_level` int(32) NOT NULL,
  `video_host_avatar` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `video_room_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`video_host_id`),
  KEY `video-room-id` (`video_room_id`),
  CONSTRAINT `video-room-id` FOREIGN KEY (`video_room_id`) REFERENCES `video_source` (`video_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

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
-- Table structure for video_source
-- ----------------------------
DROP TABLE IF EXISTS `video_source`;
CREATE TABLE `video_source` (
  `video_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `video_room_url` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `video_title` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `video_img` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `video_number` int(32) NOT NULL,
  `video_station_num` int(32) DEFAULT NULL,
  `video_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `video_platform` int(32) NOT NULL,
  `video_type` int(32) NOT NULL,
  `video_status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`video_id`),
  KEY `video-platform` (`video_platform`),
  KEY `video-type` (`video_type`),
  KEY `video-room-url` (`video_room_url`(191)),
  CONSTRAINT `video-platform` FOREIGN KEY (`video_platform`) REFERENCES `video_platform` (`video_platform_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `video-type` FOREIGN KEY (`video_type`) REFERENCES `video_category` (`video_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for video_user
-- ----------------------------
DROP TABLE IF EXISTS `video_user`;
CREATE TABLE `video_user` (
  `user_id` int(32) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_nickname` varchar(255) NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) NOT NULL,
  `user_authentic_name` varchar(255) DEFAULT NULL,
  `user_resume` varchar(255) NOT NULL,
  `user_sex` varchar(255) DEFAULT NULL,
  `user_birthday` varchar(255) DEFAULT NULL,
  `user_video_level` int(32) NOT NULL,
  `user_head_img` varchar(255) NOT NULL,
  `login_time` datetime DEFAULT NULL,
  `quit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for video_room
-- ----------------------------
DROP VIEW IF EXISTS `video_room`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `video_room` AS select `video_source`.`video_id` AS `video_id`,concat(convert(`video_platform`.`video_platform_domain` using utf8mb4),'/',`video_source`.`video_room_url`) AS `video_room_url`,`video_source`.`video_title` AS `video_title`,`video_source`.`video_img` AS `video_img`,`video_source`.`video_number` AS `video_number`,`video_source`.`video_station_num` AS `video_station_num`,`video_source`.`video_url` AS `video_url`,`video_source`.`video_status` AS `video_status`,`video_host`.`video_host_nickname` AS `video_host_nickname`,`video_host`.`video_host_level` AS `video_host_level`,`video_host`.`video_host_avatar` AS `video_host_avatar`,`video_platform`.`video_platform` AS `video_platform`,`video_platform`.`video_platform_domain` AS `video_platform_domain`,`video_platform`.`video_platform_img` AS `video_platform_img`,`video_category`.`video_type` AS `video_type`,`video_category`.`video_type_img` AS `video_type_img` from (((`video_source` join `video_host` on((`video_host`.`video_room_id` = `video_source`.`video_id`))) join `video_platform` on((`video_source`.`video_platform` = `video_platform`.`video_platform_id`))) join `video_category` on((`video_source`.`video_type` = `video_category`.`video_type_id`))) ;

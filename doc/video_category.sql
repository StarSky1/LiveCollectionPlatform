/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : liveplatform

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-08-25 11:50:56
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of video_category
-- ----------------------------
INSERT INTO `video_category` VALUES ('1', '英雄联盟', '8d9d3f31-8af8-45ed-80c4-4c9b4459c359.jpg');
INSERT INTO `video_category` VALUES ('2', '王者荣耀', '1031d8cf-2592-4118-9783-30514b6a0e4f.png');
INSERT INTO `video_category` VALUES ('3', '炉石传说', '6495eacf-de59-495f-a61b-07f9c0bf8971.png');
INSERT INTO `video_category` VALUES ('4', '守望先锋', '9326eadb-55d4-4263-bb58-23d33ea6ca7e.png');
INSERT INTO `video_category` VALUES ('5', 'DOTA2', '14f21c29-619d-4c2c-be5f-4f9b4ea6f023.png');
INSERT INTO `video_category` VALUES ('6', '绝地求生', '88bb8010-4229-4288-bbfd-20b4cd5de2b3.jpg');
INSERT INTO `video_category` VALUES ('7', '魔兽争霸', 'c02c3e02-f80e-4de7-a02a-d1031bcbcbde.jpg');
INSERT INTO `video_category` VALUES ('8', '穿越火线', 'f60716eb-3566-4bed-addf-d70bb93e116f.png');
INSERT INTO `video_category` VALUES ('9', 'DNF', '051941f8-0954-4562-8c3d-5d2901af649c.png');
INSERT INTO `video_category` VALUES ('10', '魔兽世界', '7654fa25-82cb-4cc0-bff8-413a7270cff5.png');
INSERT INTO `video_category` VALUES ('11', '剑网3', 'e523d046-5fab-4c90-bcae-4a271535e3ea.jpg');
INSERT INTO `video_category` VALUES ('12', '天涯明月刀', '62c61e4f-0636-4ba8-9216-fecd45a7dd59.png');
INSERT INTO `video_category` VALUES ('13', '跑跑卡丁车', '64cab153-3e70-4ade-a285-b3f1d79118a4.jpg');
INSERT INTO `video_category` VALUES ('14', '冒险岛2', 'e9f4abf5-db32-4aac-b2ae-605e048cca27.jpg');
INSERT INTO `video_category` VALUES ('16', 'CS:GO', 'c68b26ec-953a-440e-a953-7774c65a9218.png');
INSERT INTO `video_category` VALUES ('17', '星际争霸2', '8012e066-e87c-4b3e-9e97-6401a061273f.png');
INSERT INTO `video_category` VALUES ('19', 'QQ飞车', '52f09086-46c6-424c-af78-505e8a5939e9.jpg');
INSERT INTO `video_category` VALUES ('21', '火影忍者', 'b3af67ca-c67e-45c3-b309-2635b7048c9a.jpg');
INSERT INTO `video_category` VALUES ('22', '最强NBA', '6c1f9979-a100-4dc9-aab6-294880a12593.png');
INSERT INTO `video_category` VALUES ('24', '球球大作战', 'bf23a014-db15-4e1a-9ddf-9120aa416626.jpg');
INSERT INTO `video_category` VALUES ('25', '三国杀', '85c0182c-a57b-4913-a1c7-a7aedebe0d80.jpg');
INSERT INTO `video_category` VALUES ('26', '音乐', '4d30dca6-112e-4a3d-8c1e-d876969ebbe9.jpg');
INSERT INTO `video_category` VALUES ('27', '嘻哈', '80c937c1-4e3a-449a-a464-a33fcd1333a7.jpg');
INSERT INTO `video_category` VALUES ('28', '科技教育', '599e63cc-6f6b-44ec-bc6a-8bbdbad5e004.png');
INSERT INTO `video_category` VALUES ('29', '狼人杀', 'c3a1d34c-11b4-4aa1-805e-6661a65c3685.jpg');
INSERT INTO `video_category` VALUES ('30', '荒野行动', '73b83247-6315-40a4-b962-72d4f6c92dfe.jpg');
INSERT INTO `video_category` VALUES ('31', '主机游戏', 'f7d0db1c-ecd9-4d36-8800-b75420cf2884.png');
INSERT INTO `video_category` VALUES ('32', '户外直播', 'bc73ada8-6308-4fbe-825d-4e14515c810f.png');
INSERT INTO `video_category` VALUES ('33', '美食', '27b861fd-1d07-44c5-96fa-ab40572c6a29.jpg');
INSERT INTO `video_category` VALUES ('34', '二次元', '3e2333e2-57ee-4ec9-b15c-52ca444842cc.jpg');
INSERT INTO `video_category` VALUES ('35', '星颜', 'e7e1a667-947c-4e7e-bd39-aa1f5da3bf54.png');
INSERT INTO `video_category` VALUES ('36', '体育竞技', '7dce983e-0a51-41de-8962-57d001d7254a.png');
INSERT INTO `video_category` VALUES ('37', '户外', '243c6037-3b4c-4113-8e8c-a704c9970168.jpg');
INSERT INTO `video_category` VALUES ('38', '鱼教', 'cc5ffdfb-ab0e-4506-b943-bb6d75cf8021.jpg');
INSERT INTO `video_category` VALUES ('39', '颜值', '7fbd85ed-c2d4-41d5-a54d-b26503cf5998.jpg');
INSERT INTO `video_category` VALUES ('40', '阴阳师', 'c8481ddb-0b20-40f6-bc1b-5f91a841e2ae.jpg');
INSERT INTO `video_category` VALUES ('42', '剑灵', '8603d665-b80b-4411-a798-d44b2d241e8e.jpg');
INSERT INTO `video_category` VALUES ('43', '龙之谷', '04316516-d197-4415-b639-8d5b6d2ced83.jpg');

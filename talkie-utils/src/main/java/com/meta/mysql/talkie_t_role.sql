/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : talkie

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-12-01 16:00:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `talkie_t_role`
-- ----------------------------
DROP TABLE IF EXISTS `talkie_t_role`;
CREATE TABLE `talkie_t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` varchar(255) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `modify_date` varchar(255) DEFAULT NULL,
  `modify_user` bigint(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `current_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of talkie_t_role
-- ----------------------------
INSERT INTO `talkie_t_role` VALUES ('1', null, null, null, null, '总代', '0', '1', null);
INSERT INTO `talkie_t_role` VALUES ('2', null, null, null, null, '代理商', '0', '1', null);
INSERT INTO `talkie_t_role` VALUES ('3', null, null, null, null, '子代理', '0', '1', null);
INSERT INTO `talkie_t_role` VALUES ('4', null, null, null, null, '企业 ', '0', '1', null);
INSERT INTO `talkie_t_role` VALUES ('5', null, null, null, null, '代理管理', '1', '1', null);
INSERT INTO `talkie_t_role` VALUES ('6', null, null, null, null, '信息查询', '1', '1', null);
INSERT INTO `talkie_t_role` VALUES ('7', null, null, null, null, '普通用户', '2', '1', null);
INSERT INTO `talkie_t_role` VALUES ('8', null, null, null, null, '（代理）信息查询', '2', '1', null);
INSERT INTO `talkie_t_role` VALUES ('9', null, null, null, null, '(代理)企业管理', '2', '1', null);
INSERT INTO `talkie_t_role` VALUES ('10', null, null, null, null, '子代理信息查询', '3', '1', null);
INSERT INTO `talkie_t_role` VALUES ('11', null, null, null, null, '子代理企业管理', '3', '1', null);
INSERT INTO `talkie_t_role` VALUES ('12', null, null, null, null, '企业的信息查询', '4', '1', null);
INSERT INTO `talkie_t_role` VALUES ('13', null, null, null, null, '企业的用户管理', '4', '1', null);
INSERT INTO `talkie_t_role` VALUES ('14', null, null, null, null, '企业二级管理', '4', '1', null);

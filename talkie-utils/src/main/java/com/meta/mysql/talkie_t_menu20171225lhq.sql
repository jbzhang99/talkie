/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : talkie

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-12-25 17:58:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `talkie_t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `talkie_t_menu`;
CREATE TABLE `talkie_t_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` varchar(255) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `modify_date` varchar(255) DEFAULT NULL,
  `modify_user` bigint(20) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `is_hide` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `sorts` varchar(255) DEFAULT NULL,
  `url` varchar(2000) DEFAULT NULL,
  `current_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of talkie_t_menu
-- ----------------------------
INSERT INTO `talkie_t_menu` VALUES ('1', null, null, null, null, 'el-icon-menu', '1', '代理管理', '0', '1', '1', null);
INSERT INTO `talkie_t_menu` VALUES ('2', null, null, null, null, 'el-icon-star-off', '1', 'Q币管理', '0', '2', '2', null);
INSERT INTO `talkie_t_menu` VALUES ('3', null, null, null, null, 'el-icon-d-caret', '1', '企业管理', '0', '3', '3', null);
INSERT INTO `talkie_t_menu` VALUES ('4', null, null, null, null, 'el-icon-upload', '1', '群组管理', '0', '4', '4', null);
INSERT INTO `talkie_t_menu` VALUES ('5', null, null, null, null, 'el-icon-document', '1', '账号管理', '0', '5', '5', null);
INSERT INTO `talkie_t_menu` VALUES ('6', null, null, null, null, 'el-icon-edit', '1', '用户管理', '0', '6', '6', null);
INSERT INTO `talkie_t_menu` VALUES ('7', null, null, null, null, 'el-icon-upload', '1', '子代理管理', '0', '7', '7', null);
INSERT INTO `talkie_t_menu` VALUES ('8', null, null, null, null, 'el-icon-mobile-phone', '1', '终端管理', '0', '8', '8', null);
INSERT INTO `talkie_t_menu` VALUES ('9', null, null, null, null, 'el-icon-setting', '1', '系统管理', '0', '9', '9', null);
INSERT INTO `talkie_t_menu` VALUES ('10', null, null, null, null, null, '1', '代理管理', '1', '1', '/home/merchant/merchantmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('11', null, null, null, null, null, '1', 'Q币管理', '2', '1', '/home/qmanage/qmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('12', null, null, null, null, null, '1', 'Q币日志', '2', '2', '/home/qmanage/qmanagelog', null);
INSERT INTO `talkie_t_menu` VALUES ('13', null, null, null, null, null, '1', '交易记录', '2', '3', '/home/qmanage/qbusinesslog', null);
INSERT INTO `talkie_t_menu` VALUES ('14', null, null, null, null, null, '1', 'Q币分配', '2', '4', '/home/qmanage/qassign', null);
INSERT INTO `talkie_t_menu` VALUES ('15', null, null, null, null, null, '1', 'Q币管理', '2', '5', '/home/qmanage/secqmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('16', null, null, null, null, null, '1', '账号管理', '5', '1', '/home/account/accountmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('17', null, null, null, null, null, '1', '操作记录', '9', '1', '/home/system/userevent', null);
INSERT INTO `talkie_t_menu` VALUES ('18', null, null, null, null, null, '1', '操作记录', '9', '2', '/home/system/generalagentevent', null);
INSERT INTO `talkie_t_menu` VALUES ('19', null, null, null, null, null, '1', '企业管理', '3', '1', '/home/enterprise/enterprisemanage', null);
INSERT INTO `talkie_t_menu` VALUES ('20', null, null, null, null, '', '1', 'Q币日志', '2', '6', '/home/qmanage/secqmanagelog', null);
INSERT INTO `talkie_t_menu` VALUES ('21', null, null, null, null, null, '1', '交易记录', '2', '7', '/home/qmanage/secqbusinesslog', null);
INSERT INTO `talkie_t_menu` VALUES ('22', null, null, null, null, null, '1', '操作记录', '9', '3', '/home/system/merchantevent', null);
INSERT INTO `talkie_t_menu` VALUES ('23', null, null, null, null, null, '1', '用户管理', '6', '1', '/home/user/usermanage', null);
INSERT INTO `talkie_t_menu` VALUES ('24', null, null, null, null, null, '1', '子代理管理', '7', '1', '/home/secMerchant/secMerchantmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('25', null, null, null, null, null, '1', '终端管理', '6', '1', '/home/merchant/merchantuser', null);
INSERT INTO `talkie_t_menu` VALUES ('26', null, null, null, null, null, '1', '账号管理', '5', '2', '/home/account/merchantaccountmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('27', null, null, null, null, null, '1', '账号管理', '5', '3', '/home/account/secmerchantaccountmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('28', null, null, null, null, null, '1', '群组管理', '4', '1', '/home/group/groupmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('29', null, null, null, null, null, '1', '账号管理', '5', '4', '/home/account/enterpriseaccountmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('30', null, null, null, null, null, '1', '交易记录', '2', '6', '/home/qmanage/qenterprisebusinesslog', null);
INSERT INTO `talkie_t_menu` VALUES ('31', null, null, null, null, null, '1', 'Q币日志', '2', '8', '/home/qmanage/qenterprisemanagelog', null);
INSERT INTO `talkie_t_menu` VALUES ('32', null, null, null, null, null, '1', '操作记录', '9', '4', '/home/system/enterpriseevent', null);
INSERT INTO `talkie_t_menu` VALUES ('33', null, null, null, null, 'el-icon-share', '1', '二级管理', '0', '1', '10', null);
INSERT INTO `talkie_t_menu` VALUES ('34', null, null, null, null, null, '1', '二级管理', '33', '1', '/home/secenterprise/secenterprise', null);
INSERT INTO `talkie_t_menu` VALUES ('35', null, null, null, null, null, '1', '用户管理', '6', '2', '/home/secenterprise/usermanage', null);
INSERT INTO `talkie_t_menu` VALUES ('36', null, null, null, null, null, '1', '群组管理', '4', '2', '/home/group/secenterprisegroupmanage', null);
INSERT INTO `talkie_t_menu` VALUES ('37', null, null, null, null, null, '1', '群组功能', '4', '3', '/home/group/assignmentgroup', null);
INSERT INTO `talkie_t_menu` VALUES ('38', null, null, null, null, null, '1', '统计信息', '9', '2', '/home/system/generalagenttotalinfo', null);
INSERT INTO `talkie_t_menu` VALUES ('39', null, null, null, null, null, '1', '统计信息', '9', '3', '/home/system/merchanttotalinfo', null);
INSERT INTO `talkie_t_menu` VALUES ('40', null, null, null, null, null, '1', '统计信息', '9', '4', '/home/system/enterprisetotalinfo', null);
INSERT INTO `talkie_t_menu` VALUES ('41', null, null, null, null, null, '1', '导入导出', '6', '2', '/home/user/exportimportexcel', null);
INSERT INTO `talkie_t_menu` VALUES ('42', null, null, null, null, null, '1', '用户定位', '9', '5', '/home/map/userposition', null);
INSERT INTO `talkie_t_menu` VALUES ('43', null, null, '', null, 'el-icon-tickets', '1', '档案数据', '0', '10', '11', null);
INSERT INTO `talkie_t_menu` VALUES ('44', null, null, null, null, null, '1', '录音管理', '43', '1', '/home/filedata/recording', null);
INSERT INTO `talkie_t_menu` VALUES ('45', null, null, null, null, null, '1', 'Q币充值', '2', '10', '/home/qmanage/qrechargevalue', null);
INSERT INTO `talkie_t_menu` VALUES ('46', null, null, null, null, null, '1', '特殊播报', '9', '6', '/home/system/boardcast', null);
INSERT INTO `talkie_t_menu` VALUES ('47', null, null, null, null, null, '1', '新增用户', '6', '2', '/home/user/adduser', null);

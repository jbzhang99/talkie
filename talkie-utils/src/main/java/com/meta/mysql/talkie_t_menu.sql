/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : talkie

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-01-10 16:18:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for talkie_t_menu
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
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of talkie_t_menu
-- ----------------------------
INSERT INTO `talkie_t_menu` VALUES ('1', null, null, '2017-12-29 15:44:34', '0', 'el-icon-menu', '1', '代理管理', '0', '1', '1', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('2', null, null, null, null, 'el-icon-star-off', '1', 'Q币管理', '0', '2', '2', null, null);
INSERT INTO `talkie_t_menu` VALUES ('3', null, null, null, null, 'el-icon-d-caret', '1', '企业管理', '0', '3', '3', null, null);
INSERT INTO `talkie_t_menu` VALUES ('4', null, null, '2018-01-10 14:35:59', null, 'el-icon-upload', '1', '群组管理', '0', '3', '4', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('5', null, null, '2018-01-10 14:58:14', null, 'el-icon-document', '1', '账号管理', '0', '6', '5', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('6', null, null, '2018-01-10 14:35:44', null, 'el-icon-edit', '1', '用户管理', '0', '1', '6', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('7', null, null, null, null, 'el-icon-upload', '1', '子代理管理', '0', '7', '7', null, null);
INSERT INTO `talkie_t_menu` VALUES ('8', null, null, null, null, 'el-icon-mobile-phone', '1', '终端管理', '0', '8', '8', null, null);
INSERT INTO `talkie_t_menu` VALUES ('9', null, null, '2018-01-10 15:10:11', '0', 'el-icon-setting', '1', '系统管理', '0', '7', '9', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('10', null, null, null, null, null, '1', '代理管理', '1', '1', '/home/merchant/merchantmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('11', null, null, null, null, null, '1', 'Q币管理', '2', '1', '/home/qmanage/qmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('12', null, null, null, null, null, '1', 'Q币日志', '2', '2', '/home/qmanage/qmanagelog', null, null);
INSERT INTO `talkie_t_menu` VALUES ('13', null, null, null, null, null, '1', '交易记录', '2', '3', '/home/qmanage/qbusinesslog', null, null);
INSERT INTO `talkie_t_menu` VALUES ('14', null, null, null, null, null, '1', 'Q币分配', '2', '4', '/home/qmanage/qassign', null, null);
INSERT INTO `talkie_t_menu` VALUES ('15', null, null, null, null, null, '1', 'Q币管理', '2', '5', '/home/qmanage/secqmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('16', null, null, null, null, null, '1', '账号管理', '5', '1', '/home/account/accountmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('17', null, null, null, null, null, '1', '操作记录', '9', '1', '/home/system/userevent', null, null);
INSERT INTO `talkie_t_menu` VALUES ('18', null, null, null, null, null, '1', '操作记录', '9', '2', '/home/system/generalagentevent', null, null);
INSERT INTO `talkie_t_menu` VALUES ('19', null, null, null, null, null, '1', '企业管理', '3', '1', '/home/enterprise/enterprisemanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('20', null, null, null, null, '', '1', 'Q币日志', '2', '6', '/home/qmanage/secqmanagelog', null, null);
INSERT INTO `talkie_t_menu` VALUES ('21', null, null, null, null, null, '1', '交易记录', '2', '7', '/home/qmanage/secqbusinesslog', null, null);
INSERT INTO `talkie_t_menu` VALUES ('22', null, null, null, null, null, '1', '操作记录', '9', '3', '/home/system/merchantevent', null, null);
INSERT INTO `talkie_t_menu` VALUES ('23', null, null, null, null, null, '1', '用户管理', '6', '1', '/home/user/usermanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('24', null, null, null, null, null, '1', '子代理管理', '7', '1', '/home/secMerchant/secMerchantmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('25', null, null, null, null, null, '1', '用户管理', '6', '1', '/home/merchant/merchantuser', null, null);
INSERT INTO `talkie_t_menu` VALUES ('26', null, null, null, null, null, '1', '账号管理', '5', '2', '/home/account/merchantaccountmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('27', null, null, null, null, null, '1', '账号管理', '5', '3', '/home/account/secmerchantaccountmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('28', null, null, null, null, null, '1', '群组管理', '4', '1', '/home/group/groupmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('29', null, null, null, null, null, '1', '账号管理', '5', '4', '/home/account/enterpriseaccountmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('30', null, null, null, null, null, '1', '交易记录', '2', '6', '/home/qmanage/qenterprisebusinesslog', null, null);
INSERT INTO `talkie_t_menu` VALUES ('31', null, null, null, null, null, '1', 'Q币日志', '2', '8', '/home/qmanage/qenterprisemanagelog', null, null);
INSERT INTO `talkie_t_menu` VALUES ('32', null, null, null, null, null, '1', '操作记录', '9', '4', '/home/system/enterpriseevent', null, null);
INSERT INTO `talkie_t_menu` VALUES ('33', null, null, '2018-01-10 14:57:55', null, 'el-icon-share', '1', '二级管理', '0', '4', '10', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('34', null, null, null, null, null, '1', '二级管理', '33', '1', '/home/secenterprise/secenterprise', null, null);
INSERT INTO `talkie_t_menu` VALUES ('35', null, null, null, null, null, '1', '用户管理', '6', '2', '/home/secenterprise/usermanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('36', null, null, null, null, null, '1', '群组管理', '4', '2', '/home/group/secenterprisegroupmanage', null, null);
INSERT INTO `talkie_t_menu` VALUES ('37', null, null, null, null, null, '1', '群组功能', '4', '3', '/home/group/assignmentgroup', null, null);
INSERT INTO `talkie_t_menu` VALUES ('38', null, null, null, null, null, '1', '统计信息', '9', '2', '/home/system/generalagenttotalinfo', null, null);
INSERT INTO `talkie_t_menu` VALUES ('39', null, null, null, null, null, '1', '统计信息', '9', '3', '/home/system/merchanttotalinfo', null, null);
INSERT INTO `talkie_t_menu` VALUES ('40', null, null, null, null, null, '1', '统计信息', '9', '4', '/home/system/enterprisetotalinfo', null, null);
INSERT INTO `talkie_t_menu` VALUES ('41', null, null, null, null, null, '1', '导入导出', '6', '2', '/home/user/exportimportexcel', null, null);
INSERT INTO `talkie_t_menu` VALUES ('42', null, null, null, null, null, '1', '用户定位', '9', '5', '/home/map/userposition', null, null);
INSERT INTO `talkie_t_menu` VALUES ('43', null, null, '2018-01-10 14:58:04', null, 'el-icon-tickets', '1', '档案数据', '0', '5', '11', '0', null);
INSERT INTO `talkie_t_menu` VALUES ('44', null, null, null, null, null, '1', '录音管理', '43', '1', '/home/filedata/recording', null, null);
INSERT INTO `talkie_t_menu` VALUES ('45', null, null, null, null, null, '1', 'Q币充值', '2', '10', '/home/qmanage/qrechargevalue', null, null);
INSERT INTO `talkie_t_menu` VALUES ('46', null, null, null, null, null, '1', '特殊播报', '9', '6', '/home/system/boardcast', null, null);
INSERT INTO `talkie_t_menu` VALUES ('47', null, null, null, null, null, '1', '新增用户', '6', '2', '/home/user/adduser', null, null);
INSERT INTO `talkie_t_menu` VALUES ('51', '2017-12-29 17:54:21', null, '2017-12-29 17:54:21', null, null, '1', '菜单设置', '9', '1', '/home/system/menuManagement', '0', '总代-》系统-》菜单设置');

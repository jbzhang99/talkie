/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : talkie

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-12-25 17:58:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `talkie_t_menu_role`
-- ----------------------------
DROP TABLE IF EXISTS `talkie_t_menu_role`;
CREATE TABLE `talkie_t_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` varchar(255) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `modify_date` varchar(255) DEFAULT NULL,
  `modify_user` bigint(20) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `current_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjw3xqilbxx91mhbmhmsdrx5kv` (`menu_id`),
  KEY `FKip28umc6dm5j4tpsdkcfwr27k` (`role_id`),
  CONSTRAINT `FKip28umc6dm5j4tpsdkcfwr27k` FOREIGN KEY (`role_id`) REFERENCES `talkie_t_role` (`id`),
  CONSTRAINT `FKjw3xqilbxx91mhbmhmsdrx5kv` FOREIGN KEY (`menu_id`) REFERENCES `talkie_t_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of talkie_t_menu_role
-- ----------------------------
INSERT INTO `talkie_t_menu_role` VALUES ('1', null, null, null, null, '1', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('2', null, null, null, null, '10', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('3', null, null, null, null, '2', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('4', null, null, null, null, '11', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('5', null, null, null, null, '12', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('6', null, null, null, null, '13', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('7', null, null, null, null, '5', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('8', null, null, null, null, '16', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('9', null, null, null, null, '9', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('10', null, null, null, null, '18', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('11', null, null, null, null, '3', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('12', null, null, null, null, '19', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('13', null, null, null, null, '2', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('14', null, null, null, null, '15', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('15', null, null, null, null, '20', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('16', null, null, null, null, '21', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('17', null, null, null, null, '22', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('18', null, null, null, null, '9', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('19', null, null, null, null, '6', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('20', null, null, null, null, '7', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('21', null, null, null, null, '24', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('22', null, null, null, null, '25', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('23', null, null, null, null, '5', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('24', null, null, null, null, '26', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('25', null, null, null, null, '1', '6', null);
INSERT INTO `talkie_t_menu_role` VALUES ('26', null, null, null, null, '10', '6', null);
INSERT INTO `talkie_t_menu_role` VALUES ('27', null, null, null, null, '9', '6', null);
INSERT INTO `talkie_t_menu_role` VALUES ('28', null, null, null, null, '18', '6', null);
INSERT INTO `talkie_t_menu_role` VALUES ('29', null, null, null, null, '1', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('30', null, null, null, null, '10', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('31', null, null, null, null, '2', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('32', null, null, null, null, '11', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('33', null, null, null, null, '12', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('34', null, null, null, null, '13', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('35', null, null, null, null, '9', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('36', null, null, null, null, '18', '5', null);
INSERT INTO `talkie_t_menu_role` VALUES ('37', null, null, null, null, '3', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('38', null, null, null, null, '19', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('39', null, null, null, null, '2', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('40', null, null, null, null, '15', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('41', null, null, null, null, '20', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('42', null, null, null, null, '21', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('43', null, null, null, null, '9', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('44', null, null, null, null, '22', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('45', null, null, null, null, '27', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('46', null, null, null, null, '5', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('47', null, null, null, null, '3', '10', null);
INSERT INTO `talkie_t_menu_role` VALUES ('48', null, null, null, null, '19', '10', null);
INSERT INTO `talkie_t_menu_role` VALUES ('49', null, null, null, null, '9', '10', null);
INSERT INTO `talkie_t_menu_role` VALUES ('50', null, null, null, null, '22', '10', null);
INSERT INTO `talkie_t_menu_role` VALUES ('51', null, null, null, null, '3', '11', null);
INSERT INTO `talkie_t_menu_role` VALUES ('52', null, null, null, null, '19', '11', null);
INSERT INTO `talkie_t_menu_role` VALUES ('53', null, null, null, null, '9', '11', null);
INSERT INTO `talkie_t_menu_role` VALUES ('54', null, null, null, null, '22', '11', null);
INSERT INTO `talkie_t_menu_role` VALUES ('55', null, null, null, null, '6', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('56', null, null, null, null, '23', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('57', null, null, null, null, '4', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('58', null, null, null, null, '28', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('59', null, null, null, null, '5', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('60', null, null, null, null, '29', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('61', null, null, null, null, '2', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('62', null, null, null, null, '14', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('63', null, null, null, null, '30', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('64', null, null, null, null, '31', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('65', null, null, null, null, '6', '12', null);
INSERT INTO `talkie_t_menu_role` VALUES ('66', null, null, null, null, '23', '12', null);
INSERT INTO `talkie_t_menu_role` VALUES ('67', null, null, null, null, '32', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('68', null, null, null, null, '9', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('69', null, null, null, null, '9', '12', null);
INSERT INTO `talkie_t_menu_role` VALUES ('70', null, null, null, null, '32', '12', null);
INSERT INTO `talkie_t_menu_role` VALUES ('71', null, null, null, null, '6', '13', null);
INSERT INTO `talkie_t_menu_role` VALUES ('72', null, null, null, null, '23', '13', null);
INSERT INTO `talkie_t_menu_role` VALUES ('73', null, null, null, null, '4', '13', null);
INSERT INTO `talkie_t_menu_role` VALUES ('74', null, null, null, null, '28', '13', null);
INSERT INTO `talkie_t_menu_role` VALUES ('75', null, null, null, null, '9', '13', null);
INSERT INTO `talkie_t_menu_role` VALUES ('76', null, null, null, null, '32', '13', null);
INSERT INTO `talkie_t_menu_role` VALUES ('77', null, null, null, null, '33', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('78', null, null, null, null, '34', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('79', null, null, null, null, '6', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('80', null, null, null, null, '35', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('81', null, null, null, null, '4', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('82', null, null, null, null, '36', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('83', null, null, null, null, '37', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('84', null, null, null, null, '9', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('85', null, null, null, null, '32', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('86', null, null, null, null, '38', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('87', null, null, null, null, '39', '2', null);
INSERT INTO `talkie_t_menu_role` VALUES ('88', null, null, null, null, '39', '3', null);
INSERT INTO `talkie_t_menu_role` VALUES ('89', null, null, null, null, '40', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('90', null, null, null, null, '40', '14', null);
INSERT INTO `talkie_t_menu_role` VALUES ('91', null, null, null, null, '41', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('92', null, null, null, null, '42', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('93', null, null, null, null, '43', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('94', null, null, null, null, '44', '4', null);
INSERT INTO `talkie_t_menu_role` VALUES ('95', null, null, null, null, '45', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('96', null, null, null, null, '46', '1', null);
INSERT INTO `talkie_t_menu_role` VALUES ('97', null, null, null, null, '47', '4', null);





INSERT INTO `talkie_t_menu_role` VALUES ('99', null, null, null, null, '3', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('98', null, null, null, null, '19', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('120', null, null, null, null, '15','9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('100', null, null, null, null, '20', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('101', null, null, null, null, '21', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('102', null, null, null, null, '22', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('103', null, null, null, null, '9', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('104', null, null, null, null, '6', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('105', null, null, null, null, '7', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('106', null, null, null, null, '24', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('107', null, null, null, null, '39', '9', null);
INSERT INTO `talkie_t_menu_role` VALUES ('108', null, null, null, null, '2', '9', null);


INSERT INTO `talkie_t_menu_role` VALUES ('109', null, null, null, null, '3', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('110', null, null, null, null, '19', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('112', null, null, null, null, '20', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('113', null, null, null, null, '21', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('114', null, null, null, null, '22', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('115', null, null, null, null, '9', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('116', null, null, null, null, '6', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('117', null, null, null, null, '7', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('118', null, null, null, null, '24', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('119', null, null, null, null, '39', '8', null);
INSERT INTO `talkie_t_menu_role` VALUES ('121', null, null, null, null, '2', '8', null);

INSERT INTO `talkie_t_menu_role` VALUES ('122', null, null, null, null, '51', '1', null);
/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : misys

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001


*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '父菜单',
  `TITLE` varchar(50) NOT NULL COMMENT '菜单名称',
  `TITLE_FIRST_SPELL` varchar(50) DEFAULT NULL COMMENT '菜单名称拼音首字母',
  `ICON` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `SHOW_MODE` tinyint(4) DEFAULT NULL COMMENT '显示方式 {1:默认显示,2:默认隐藏}',
  `DESCN` text COMMENT '描述',
  `SORT_NUM` int(11) DEFAULT NULL COMMENT '排序',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态{1:启用,0:停用',
  `RESOURCE_ID` int(11) DEFAULT NULL COMMENT '资源ID',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', 'XTGL', '', '1', '', '1', '1', null, '2016-01-20');
INSERT INTO `sys_menu` VALUES ('2', '1', '用户管理', 'YHGL', '', '2', '', '3', '1', '1', '2016-01-20');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '父ID',
  `TITLE` varchar(50) NOT NULL COMMENT '资源名称',
  `RESTYPE` varchar(50) DEFAULT NULL COMMENT '资源类型',
  `RES_STRING` varchar(255) NOT NULL COMMENT '资源值',
  `PERMISSION_VALUE` text NOT NULL COMMENT '权限值',
  `DESCN` text COMMENT '描述',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态{0:禁用,1:启用}',
  `SORT_NUM` int(11) DEFAULT NULL COMMENT '排序值',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建时间',
  `classify` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '0', '用户管理', 'URL', '/module/user/userList.html', '/module/user/userList.html', '', '1', null, '2016-01-20', '4');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ROLE_NAME` varchar(50) NOT NULL COMMENT '角色名称',
  `DESCN` text COMMENT '描述信息',
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', null, '2016-03-31 16:35:17', '2016-12-21 14:17:59');

-- ----------------------------
-- Table structure for sys_role_resc
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resc`;
CREATE TABLE `sys_role_resc` (
  `ROLE_ID` int(11) NOT NULL,
  `RESC_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESC_ID`),
  KEY `IND_RESC_ROLE_ID` (`RESC_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色资源表';

-- ----------------------------
-- Records of sys_role_resc
-- ----------------------------
INSERT INTO `sys_role_resc` VALUES ('1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USERNAME` varchar(30) NOT NULL COMMENT '登录用户名',
  `TRUENAME` varchar(80) DEFAULT NULL COMMENT '操作员姓名',
  `PASSWORD` varchar(50) NOT NULL COMMENT '登录密码',
  `SALT` varchar(50) DEFAULT NULL COMMENT '密码加密填充值',
  `USERTYPE` tinyint(4) NOT NULL COMMENT '用户类型{1:管理员,2:操作员}',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `STATUS` tinyint(4) NOT NULL COMMENT '状态{1:启用,0:禁用}',
  `DESCN` varchar(255) DEFAULT NULL COMMENT '描述',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建时间',
  `LAST_LOGIN_DATE` date DEFAULT NULL COMMENT '最后登录时间',
  `LAST_LOGIN_IP` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  `EXPIRED_DATE` date DEFAULT NULL COMMENT '过期时间',
  `UNLOCK_DATE` date DEFAULT NULL COMMENT '解锁时间',
  `location_id` varchar(50) DEFAULT NULL,
  `DEPT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IND_USERNAME` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', '854fb49ced176aa09fcc825c686d1378c89bed9e', '3efb7d209fdaa340', '1', 'admin@admin.com', '1', '', '2016-01-20', '2017-02-24', '0:0:0:0:0:0:0:1', null, null, '9999', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  UNIQUE KEY `IND_USER_ROLE_ID` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');

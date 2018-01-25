/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-27 18:31:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_permissions_permission` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions` VALUES ('160', 'user:create', '用户模块新增', '1');
INSERT INTO `sys_permissions` VALUES ('161', 'user:update', '用户模块修改', '1');
INSERT INTO `sys_permissions` VALUES ('162', 'menu:create', '菜单模块新增', '1');

-- ----------------------------
-- Table structure for `sys_roles`
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_roles_role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES ('130', 'admin', '管理员', '1');
INSERT INTO `sys_roles` VALUES ('131', 'user', '用户', '1');
INSERT INTO `sys_roles` VALUES ('132', 'tourist', '游客', '0');

-- ----------------------------
-- Table structure for `sys_roles_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
INSERT INTO `sys_roles_permissions` VALUES ('130', '160');
INSERT INTO `sys_roles_permissions` VALUES ('130', '161');
INSERT INTO `sys_roles_permissions` VALUES ('130', '162');
INSERT INTO `sys_roles_permissions` VALUES ('131', '160');
INSERT INTO `sys_roles_permissions` VALUES ('131', '161');

-- ----------------------------
-- Table structure for `sys_users`
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `user_status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_users_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES ('210', 'zhang', '51ee0fbe4c1cbfa42fc9c4c8a8b93ed2', '5f287c9983872e52ffb4eddf3aabeb0f', '0');
INSERT INTO `sys_users` VALUES ('211', 'li', '96450577a08ad45a1a33c88220112c2c', 'f4d2375e4fa14d171d3e5022e6cc0847', '0');
INSERT INTO `sys_users` VALUES ('212', 'wu', '466bce01859d47ffd1addfec997368c4', '88b7bcbce39ac06a816d8101d913c329', '0');
INSERT INTO `sys_users` VALUES ('213', 'wang', '0054f9282b1831c34aa6832370a89ca7', '4e1019e968b714e4a21c33d853ec358d', '1');

-- ----------------------------
-- Table structure for `sys_users_roles`
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES ('210', '130');
INSERT INTO `sys_users_roles` VALUES ('210', '132');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'shiro01', '12345', null);
INSERT INTO `users` VALUES ('2', 'shiro02', '$shiro1$SHA-512$1$$NieQminDE4Ggcewn98nKl3Jhgq7Smn3dLlQ1MyLPswq7njpt8qwsIP4jQ2MR1nhWTQyNMFkwV19g4tPQSBhNeQ==', null);
INSERT INTO `users` VALUES ('3', 'shiro03', 'd95198b72b895db7a7b6b986560304b0', 'c52e6d8899dc63fa0eb572e2a9e0e471');

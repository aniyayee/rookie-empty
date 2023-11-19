/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : db_rookie

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 19/11/2023 21:15:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `notice_id`      bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `notice_title`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '公告标题',
    `notice_type`    smallint(6)                                                   NOT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '公告内容',
    `status`         smallint(6)                                                   NOT NULL DEFAULT 0 COMMENT '公告状态（1正常 0关闭）',
    `creator_id`     bigint(20)                                                    NULL     DEFAULT NULL COMMENT '创建者ID',
    `create_time`    datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `updater_id`     bigint(20)                                                    NULL     DEFAULT NULL COMMENT '更新者ID',
    `update_time`    datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `remark`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `deleted`        tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '通知公告表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice`
VALUES (1, '温馨提醒：2018-07-01 RookieBoot新版本发布啦', 2, '新版本内容~~~~~~~~~~', 1, 1, '2022-05-21 08:30:55', 1,
        '2022-08-29 20:12:37', '管理员', 0);
INSERT INTO `sys_notice`
VALUES (2, '维护通知：2018-07-01 RookieBoot系统凌晨维护', 1, '维护内容', 1, 1, '2022-05-21 08:30:55', NULL, NULL, '管理员', 0);
INSERT INTO `sys_notice`
VALUES (3, '公告修改测试', 1, '公告测试', 2, 2, '2023-11-16 14:26:36', 2, '2023-11-16 14:29:50', '暂无', 0);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`
(
    `operation_id`     bigint(20)                                                     NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `business_type`    smallint(6)                                                    NOT NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `request_method`   smallint(6)                                                    NOT NULL DEFAULT 0 COMMENT '请求方式',
    `request_module`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '请求模块',
    `request_url`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '请求URL',
    `called_method`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '调用方法',
    `operator_type`    smallint(6)                                                    NOT NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `user_id`          bigint(20)                                                     NULL     DEFAULT 0 COMMENT '用户ID',
    `username`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT '' COMMENT '操作人员',
    `operation_param`  varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '请求参数',
    `operation_result` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '返回参数',
    `status`           smallint(6)                                                    NOT NULL DEFAULT 1 COMMENT '操作状态（1正常 0异常）',
    `error_stack`      varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '错误消息',
    `operation_time`   datetime                                                       NOT NULL COMMENT '操作时间',
    `deleted`          tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`operation_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色名称',
    `role_key`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色权限字符串',
    `creator_id`  bigint(20)                                                    NULL     DEFAULT NULL COMMENT '更新者ID',
    `create_time` datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `updater_id`  bigint(20)                                                    NULL     DEFAULT NULL COMMENT '更新者ID',
    `update_time` datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `deleted`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, 'admin', 'admin', NULL, NULL, NULL, '2023-08-24 10:26:44', '超级管理员', 0);
INSERT INTO `sys_role`
VALUES (2, 'test', 'test', NULL, '2023-08-22 15:00:06', NULL, NULL, '测试', 0);
INSERT INTO `sys_role`
VALUES (3, 'normal', 'normal', NULL, '2023-08-22 15:33:33', NULL, NULL, '正常', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `role_id`     bigint(20)                                                    NULL     DEFAULT NULL COMMENT '角色ID',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户账号',
    `password`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '密码',
    `nickname`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户昵称',
    `email`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '用户邮箱',
    `phone`       varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '手机号码',
    `creator_id`  bigint(20)                                                    NULL     DEFAULT NULL COMMENT '更新者ID',
    `create_time` datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `updater_id`  bigint(20)                                                    NULL     DEFAULT NULL COMMENT '更新者ID',
    `update_time` datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `deleted`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 1, 'admin', '123456', '佛祖保佑', '123456@gmail.com', '13610242333', NULL, '2023-11-02 15:53:39', NULL,
        '2023-08-22 08:48:51', '探寻者', 0);
INSERT INTO `sys_user`
VALUES (2, 2, 'yayee', '123123', 'steve yee', 'yayee@gmail.com', '13010243210', NULL, '2023-08-22 08:51:19', NULL,
        '2023-08-25 15:57:16', '测试者', 0);
INSERT INTO `sys_user`
VALUES (3, 2, 'loyee', '123456', '洛伊', '73000@gmail.com', '13678907777', 1, '2023-11-19 15:52:45', 1,
        '2023-11-19 16:18:02', '无备注', 0);

SET FOREIGN_KEY_CHECKS = 1;

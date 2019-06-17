SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint(20)  NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) NOT NULL COMMENT '用户名',
    `password`    varchar(100) DEFAULT NULL COMMENT '密码',
    `salt`        varchar(20)  DEFAULT NULL COMMENT '盐',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(100) DEFAULT NULL COMMENT '手机号',
    `status`      tinyint(4)   DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8 COMMENT ='系统用户';
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'hulang6666@qq.com', '13612345678', '1', '1', '2016-11-11 11:11:11');
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '系统管理员', '1', '2019-06-05 15:58:43');

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     bigint(20) NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(100) DEFAULT NULL COMMENT '角色名称',
    `remark`      varchar(100) DEFAULT NULL COMMENT '备注',
    `dept_id`     bigint(20)   DEFAULT NULL COMMENT '部门ID',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`role_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8 COMMENT ='角色';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `parent_id` bigint(20)   DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
    `name`      varchar(50)  DEFAULT NULL COMMENT '菜单名称',
    `url`       varchar(200) DEFAULT NULL COMMENT '菜单URL',
    `perms`     varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
    `type`      int(11)      DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
    `icon`      varchar(50)  DEFAULT NULL COMMENT '菜单图标',
    `order_num` int(11)      DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`menu_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 57
  DEFAULT CHARSET = utf8 COMMENT ='菜单管理';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8 COMMENT ='用户与角色对应关系';


DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
    `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 146
  DEFAULT CHARSET = utf8 COMMENT ='角色与菜单对应关系';


DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`         bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `operation`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户操作',
    `method`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方法',
    `params`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数',
    `time`       bigint(20)                                              NOT NULL COMMENT '执行时长(毫秒)',
    `ip`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP地址',
    `createtime` datetime(0)                                             NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '日志记录表';

SET FOREIGN_KEY_CHECKS = 1;

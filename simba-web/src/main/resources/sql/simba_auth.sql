CREATE TABLE `simba_auth_sys_user`
(
    `id`          varchar(64)                                             NOT NULL,
    `username`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `age`         int                                                              DEFAULT NULL COMMENT '年龄',
    `gender`      int                                                              DEFAULT NULL COMMENT '性别',
    `version`     int                                                              DEFAULT NULL COMMENT '版本号',
    `deleted`     tinyint                                                 NOT NULL DEFAULT '0' COMMENT '删除状态，0表示未删除，1表示已删除',
    `create_by`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                                         DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


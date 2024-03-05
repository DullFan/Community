-- 创建articles表
DROP TABLE IF EXISTS articles;
CREATE TABLE articles
(
    id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    content         longblob         DEFAULT NULL COMMENT '内容',
    author_id       INT UNSIGNED     DEFAULT 0 COMMENT '作者ID',
    publish_time    DATETIME         DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    is_top          TINYINT UNSIGNED DEFAULT 0 COMMENT '是否置顶，0：不置顶，1：置顶',
    reviewer_id     INT UNSIGNED     DEFAULT 0 COMMENT '审核人员ID',
    reviewer_time   DATETIME         DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
    status          INT UNSIGNED     DEFAULT 2 COMMENT '状态，1是正常状态，2是审核状态，3是封禁状态',
    tag_id          INT UNSIGNED     DEFAULT 1 COMMENT '文章标签',
    browse_count    INT UNSIGNED     DEFAULT 0 COMMENT '浏览量',
    comment_count   INT UNSIGNED     DEFAULT 0 COMMENT '评论数',
    thumbs_up_count INT UNSIGNED     DEFAULT 0 COMMENT '点赞数'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章表';

-- 创建comment表
DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
    id                      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    content                 VARCHAR(200) COMMENT '内容',
    comment_user_id         INT UNSIGNED DEFAULT 0 COMMENT '评论者ID',
    comment_articles_id     INT UNSIGNED DEFAULT 0 COMMENT '评论所属文章ID',
    reply_count             INT UNSIGNED DEFAULT 0 COMMENT '回复量',
    comment_thumbs_up_count INT UNSIGNED DEFAULT 0 COMMENT '点赞量',
    comment_time            DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章评论表';

-- 创建comment_reply表
DROP TABLE IF EXISTS comment_reply;
CREATE TABLE comment_reply
(
    id                            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    content                       VARCHAR(200) COMMENT '回复内容',
    comment_reply_user_id         INT UNSIGNED DEFAULT 0 COMMENT '回复者ID',
    reply_user_id                 INT UNSIGNED DEFAULT 0 COMMENT '被回复者ID',
    comment_id                    INT UNSIGNED DEFAULT 0 COMMENT '回复所属评论ID',
    reply_time                    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
    comment_reply_thumbs_up_count INT UNSIGNED DEFAULT 0 COMMENT '点赞量'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章评论回复表';

-- 创建thumbs表
DROP TABLE IF EXISTS thumbs;
CREATE TABLE thumbs
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    quote_id    INT UNSIGNED DEFAULT 0 COMMENT '引用ID，有文章ID、评论ID、评论回复ID',
    user_id     INT UNSIGNED DEFAULT 0 COMMENT '用户ID',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    type        INT UNSIGNED DEFAULT 0 COMMENT '点赞类型，1:文章点赞，2:文章评论点赞，3:文章回复点赞'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章点赞表';

-- 创建images表
DROP TABLE IF EXISTS images;
CREATE TABLE images
(
    id        BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    quote_id  INT UNSIGNED DEFAULT 0 COMMENT '引用ID，有文章ID、评论ID、评论回复ID',
    image_url varchar(200) COMMENT '图片地址',
    type      INT UNSIGNED DEFAULT 0 COMMENT '图片类型，1:文章图片，2:评论图片，3:评论回复图片'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='图片表';

-- 创建banners表
DROP TABLE IF EXISTS banners;
CREATE TABLE banners
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    articles_id INT UNSIGNED DEFAULT 0 COMMENT '文章ID',
    is_status   INT UNSIGNED DEFAULT 0 COMMENT '状态，0：不可用，1：可用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    sort        INT UNSIGNED DEFAULT 0 COMMENT '排序字段',
    image_url   VARCHAR(100) COMMENT '图片URL'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='轮播图表';

-- 创建browse表
DROP TABLE IF EXISTS browse;
CREATE TABLE browse
(
    id                 BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    browse_user_id     INT UNSIGNED DEFAULT 0 COMMENT '浏览者ID',
    browse_articles_id INT UNSIGNED DEFAULT 0 COMMENT '浏览文章ID',
    create_time        DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章浏览表';

-- 创建articles_tag表
DROP TABLE IF EXISTS articles_tag;
CREATE TABLE articles_tag
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(30) COMMENT '标签名称',
    description VARCHAR(100) COMMENT '标签描述',
    status      INT UNSIGNED DEFAULT 1 COMMENT '状态，1是可用，2是不可用',
    create_by   INT UNSIGNED DEFAULT 0 COMMENT '创建者ID',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='文章标签表';
insert into articles_tag
values (1, "Java", "Java语言", 1, 1, sysdate());
insert into articles_tag
values (2, "Spring Boot", "后端框架", 1, 1, sysdate());
insert into articles_tag
values (3, "Spring Cloud", "分布式", 1, 1, sysdate());
insert into articles_tag
values (4, "Vue", "前端框架", 1, 1, sysdate());
insert into articles_tag
values (5, "Android", "Android平台", 1, 1, sysdate());

-- create notification table
DROP TABLE IF EXISTS notification;
CREATE TABLE `notification`
(
    `notification_id`   INT AUTO_INCREMENT PRIMARY KEY COMMENT '通知表ID',
    `notification_type` TINYINT(1) COMMENT '通知类型，1是系统通知，2是点赞，3是评论',
    `content`           VARCHAR(200) COMMENT '通知内容',
    `from_user_id`      INT COMMENT '发布者Id',
    `article_id`        INT COMMENT '文章id',
    `comment_id`        INT COMMENT '评论id',
    `comment_reply_id`  INT COMMENT '评论回复id',
    `create_time`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '通知时间'
) ENGINE = INNODB
  DEFAULT CHARSET = utf8 COMMENT '通知表';

-- create notification_user table
DROP TABLE IF EXISTS notification_user;
CREATE TABLE `notification_user`
(
    `id`              INT AUTO_INCREMENT PRIMARY KEY COMMENT '通知和用户关联表Id',
    `notification_id` INT COMMENT '通知Id',
    `to_user_id`         INT COMMENT '接收者id',
    `view_time`       TIMESTAMP COMMENT '查看时间',
    `status`          TINYINT(1) DEFAULT 0 COMMENT '阅读状态，0是未查看，1是已查看'
) ENGINE = INNODB
  DEFAULT CHARSET = utf8 COMMENT '通知和用户关联表';

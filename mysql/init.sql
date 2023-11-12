DROP DATABASE IF EXISTS springsecurityjwt;
CREATE DATABASE IF NOT EXISTS springsecurityjwt;
use springsecurityjwt;

CREATE TABLE IF NOT EXISTS `post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `content` TEXT,
    `created` DATETIME,
    `updated` DATETIME,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(70) NOT NULL,
    `email` VARCHAR(70),
    `name` VARCHAR(50),
    `bio` varchar(512),
    `roles` VARCHAR(50),
    `enabled` TINYINT,
    `created` DATETIME,
    PRIMARY KEY(`id`)
);

ALTER TABLE `post` ADD CONSTRAINT `fk_post_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

-- admin/pass
INSERT INTO user(`username`, `password`, `email`, `name`, `enabled`, `roles`, `created`) values
  ('admin', '$2y$10$CamipIbIY/eLf6VexqsMderLpX57mn1shmB1lkLeJVcp1G1b1IXJa', 'a@gmail.com', 'Alice A', 1, 'ROLE_ADMIN,ROLE_USER', NOW());

-- user/pass
INSERT INTO user(`username`, `password`, `email`, `name`, `enabled`, `roles`, `created`) values
  ('user', '$2y$10$CamipIbIY/eLf6VexqsMderLpX57mn1shmB1lkLeJVcp1G1b1IXJa', 'b@gmail.com', 'Ben B', 1, 'ROLE_USER', NOW());


INSERT INTO post(`user_id`, `title`, `content`, `created`, `updated`) values
  ( 1, 'test', 'test content', NOW(), NOW());


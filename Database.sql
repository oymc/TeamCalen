SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`  int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `email`    varchar(255) NOT NULL,
    `picture`  varchar(255) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`
(
    `schedule_id`   int(11)      NOT NULL AUTO_INCREMENT,
    `user_id`       int(11)      NOT NULL,
    `date`          date         NOT NULL,
    `start_hour`    int(11)      NOT NULL,
    `start_minute`  int(11)      NOT NULL,
    `end_hour`      int(11)      NOT NULL,
    `end_minute`    int(11)      NOT NULL,
    `schedule_text` text         NOT NULL,
    `state`         varchar(255) NOT NULL,
    `hasReminder`   tinyint(4)   NOT NULL,
    PRIMARY KEY (`schedule_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

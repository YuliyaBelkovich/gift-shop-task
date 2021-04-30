CREATE TABLE `gift_certificate`
(
    `id`               int(11) NOT NULL AUTO_INCREMENT,
    `create_date`      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `description`      varchar(1000) NOT NULL,
    `duration`         int(11) NOT NULL,
    `last_update_date` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `name`             varchar(30)   NOT NULL,
    `price`            double        NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_en7vlwcn343hrd7cfcibpw284` (`name`)
);
CREATE TABLE `tag`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_1wdpsed5kna2y38hnbgrnhi5b` (`name`)
);
CREATE TABLE `gift_certificate_tag`
(
    `gift_certificate_id` int(11) NOT NULL,
    `tag_id`              int(11) NOT NULL,
    PRIMARY KEY (`gift_certificate_id`, `tag_id`),
    KEY                   `FK5tjjbkwfbad84jkeobe07owf9` (`tag_id`),
    CONSTRAINT `FK5tjjbkwfbad84jkeobe07owf9` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`),
    CONSTRAINT `FKa9orffdp51dqmamwv59d01rf1` FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`)
);
CREATE TABLE `orders`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `cost`        double    NOT NULL,
    `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id`     int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FK9r8ud4y20lp9bkwgsy8enpgag` (`user_id`),
    CONSTRAINT `FK9r8ud4y20lp9bkwgsy8enpgag` FOREIGN KEY (`user_id`) REFERENCES `user_gift` (`id`)
);
CREATE TABLE `user_gift`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `email`    varchar(255) NOT NULL,
    `name`     varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `order_certificate`
(
    `order_id`            int(11) NOT NULL,
    `gift_certificate_id` int(11) NOT NULL,
    KEY                   `FKfuy07kfeh0fi1wxgsvo1mocab` (`gift_certificate_id`),
    KEY                   `FKrj5ul3jy6cil5g79jr6osfx5e` (`order_id`),
    CONSTRAINT `FKfuy07kfeh0fi1wxgsvo1mocab` FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`),
    CONSTRAINT `FKrj5ul3jy6cil5g79jr6osfx5e` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
);




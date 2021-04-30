gift_certificate
,
CREATE TABLE `gift_certificate`
(
    `id`               int(11) NOT NULL AUTO_INCREMENT,
    `create_date`      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `description`      varchar(1000) NOT NULL,
    `duration`         int(11) NOT NULL,
    `last_update_date` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `name`             varchar(30)   NOT NULL UNIQUE,
    `price`            double        NOT NULL,
    PRIMARY KEY (`id`)
);
tag
,
CREATE TABLE `tag`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);
gift_certificate_tag
,
CREATE TABLE `gift_certificate_tag`
(
    `gift_certificate_id` int(11) NOT NULL,
    `tag_id`              int(11) NOT NULL,
    PRIMARY KEY (`gift_certificate_id`, `tag_id`),
    FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`),
    FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`)
);
orders
,
CREATE TABLE `orders`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `cost`        double    NOT NULL,
    `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id`     int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user_gift` (`id`)
);
user_gift
,
CREATE TABLE `user_gift`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `email`    varchar(255) NOT NULL,
    `name`     varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
order_certificate
,
CREATE TABLE `order_certificate`
(
    `order_id`            int(11) NOT NULL,
    `gift_certificate_id` int(11) NOT NULL,
    PRIMARY KEY (gift_certificate_id, order_id),
    FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
);



CREATE TABLE `gift_certificate` (
                                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                                      `name` varchar(30) NOT NULL,
                                                      `description` varchar(1000) NOT NULL,
                                                      `price` double NOT NULL,
                                                      `duration` int(11) NOT NULL,
                                                      `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
                                                      `last_update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                      PRIMARY KEY (`id`)
                  );
CREATE TABLE `tag` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` varchar(30) NOT NULL,
                                PRIMARY KEY (`id`)
         );
CREATE TABLE `tag_certificate` (
                                                    `gift_certificate_id` int(11) NOT NULL,
                                                    `tag_id` int(11) NOT NULL,
                                                    PRIMARY KEY (`gift_certificate_id`,`tag_id`),
                                                   FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                   FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
                 );




# connect to mysql and run as root user
# Create Database
CREATE DATABASE productdb;

# Create user
CREATE USER 'a_user'@'localhost' IDENTIFIED BY 'pass';

# Database grants
GRANT SELECT ON productdb.* to 'a_user'@'localhost';
GRANT INSERT ON productdb.* to 'a_user'@'localhost';
GRANT DELETE ON productdb.* to 'a_user'@'localhost';
GRANT UPDATE ON productdb.* to 'a_user'@'localhost';

# Create tables
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `image`       blob,
    `name`        varchar(512) CHARACTER SET utf8      DEFAULT NULL,
    `price`       decimal(22, 2)                       DEFAULT NULL,
    `product_id`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
    `category_id` int(11),
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
    PRIMARY KEY (`id`)
);

# INSERTION
INSERT INTO product (image, name, price, product_id, category_id)
VALUES ('', 'jeans', 50, '12345', 1);

INSERT INTO category (name)
values ('clothes');
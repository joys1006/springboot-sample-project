> Spring Boot Project Sample code

## Description
Spring Boot BoilerPlate Architecture
Gradle Project Base

## Plan
Rest Api, Security Login Api, CouchBase, kafka

## Setting
Gradle Project

## Spec
- Java v11
- SpringBoot v2.5.4
- queryDsl-JPA v4.0.1
- CouchBase
- SpringSecurity
- Lombok v1.18.12
- Swagger UI v2.9.2
- ModalMapper v2.3.0
- Hystrix v2.0.1
- DataBase MySql

#Table relation
user - todo = 1:N
user - todoComment = 1:N
todo - todoComment = 1:N

#Create User Table SQL
```
CREATE TABLE `User` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_UNIQUE` (`user_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_User_created_at` (`created_at`),
  KEY `idx_User_update_at` (`update_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```
#Create Todo Table SQL
```
CREATE TABLE `TodoComment` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `todo_id` bigint(11) unsigned NOT NULL,
  `visibility_type` tinyint(1) unsigned NOT NULL,
  `author` varchar(20) NOT NULL,
  `content` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_TodoComment_user_id` (`user_id`),
  KEY `idx_TodoComment_created_at` (`created_at`),
  KEY `idx_TodoComment_updated_at` (`updated_at`),
  KEY `idx_TodoComment_todo_id` (`todo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```
#Create TodoComment Table SQL
```
CREATE TABLE `TodoComment` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `todo_id` bigint(11) unsigned NOT NULL,
  `visibility_type` tinyint(1) unsigned NOT NULL,
  `author` varchar(20) NOT NULL,
  `content` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_TodoComment_user_id` (`user_id`),
  KEY `idx_TodoComment_created_at` (`created_at`),
  KEY `idx_TodoComment_updated_at` (`updated_at`),
  KEY `idx_TodoComment_todo_id` (`todo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

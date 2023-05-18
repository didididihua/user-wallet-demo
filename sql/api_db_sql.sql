/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.25 : Database - my_project
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`my_project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `my_project`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_account` varchar(128) NOT NULL COMMENT '用户账号',
  `user_password` varchar(1024) NOT NULL COMMENT '用户密码',
  `user_name` varchar(128) DEFAULT NULL COMMENT '用户姓名',
  `user_phone` varchar(128) NOT NULL COMMENT '用户电话',
  `user_role` varchar(128) NOT NULL COMMENT '用户角色',
  `user_identify` varchar(128) NOT NULL COMMENT '用户身份证',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`user_account`,`user_password`,`user_name`,`user_phone`,`user_role`,`user_identify`,`create_time`,`update_time`,`is_delete`) values 
(1,'testtest','123456','testtest','13087736087','admin','450324200106280053','2023-05-18 16:34:31','2023-05-18 16:34:31',0);

/*Table structure for table `user_wallet` */

DROP TABLE IF EXISTS `user_wallet`;

CREATE TABLE `user_wallet` (
  `id` bigint NOT NULL COMMENT '钱包id',
  `user_id` bigint NOT NULL COMMENT '钱包所属的用户id',
  `total_amount` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_wallet` */

insert  into `user_wallet`(`id`,`user_id`,`total_amount`,`create_time`,`update_time`,`is_delete`) values 
(1,1,99950,'2023-05-18 16:34:44','2023-05-18 16:34:44',0);

/*Table structure for table `user_wallet_detail` */

DROP TABLE IF EXISTS `user_wallet_detail`;

CREATE TABLE `user_wallet_detail` (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `update_amount` decimal(10,0) NOT NULL COMMENT '更改金额',
  `update_type` tinyint NOT NULL COMMENT '更改操作类型（0.收入 1.支出）',
  `update_source` tinyint NOT NULL COMMENT '更改操作来源（0.商品购买 1.退款 2.提现 3.充值）',
  `before_amount` decimal(10,0) NOT NULL COMMENT '更改前金额',
  `after_amount` decimal(10,0) NOT NULL COMMENT '更改后金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint DEFAULT '0' COMMENT '逻辑删除',
  `user_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `order_id` bigint NOT NULL COMMENT '尝试改动的订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_wallet_detail` */

insert  into `user_wallet_detail`(`id`,`user_id`,`update_amount`,`update_type`,`update_source`,`before_amount`,`after_amount`,`create_time`,`update_time`,`is_delete`,`user_name`,`order_id`) values 
(1659165394109288450,1,-20,1,0,100000,99980,'2023-05-18 19:53:50','2023-05-18 19:53:50',0,'testtest',12345),
(1659207966307491842,1,-30,1,0,99980,99950,'2023-05-18 22:43:00','2023-05-18 22:43:00',0,'testtest',1125032);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

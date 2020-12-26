-- This is sql script for creating database and tables 

DROP DATABASE IF EXISTS `EmployeeTable`;

CREATE DATABASE `EmployeeTable`;

USE `EmployeeTable`;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `employee_account`;

CREATE TABLE `employee_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL,
  `password` char(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `employee_data`;

CREATE TABLE `employee_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `salary` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `employee_task`;

CREATE TABLE `employee_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL,
  `task` varchar(256) DEFAULT NULL,
  `time` varchar(256) DEFAULT NULL,
  `iscomplete` boolean DEFAULT NULL,
  `isfailed` boolean DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `DETAIL_id2` (`employee_id`),
  CONSTRAINT `EMPLOYEE_DT`FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;


DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `data_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `DETAIL_id0` (`account_id`),
  KEY `DETAIL_id1` (`data_id`),
  CONSTRAINT `ACCOUNT_DT` FOREIGN KEY (`account_id`) REFERENCES `employee_account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `DATA_DT` FOREIGN KEY (`data_id`) REFERENCES `employee_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: learnsql
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_category` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `is_keyword` bit(1) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_table_data` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  `category_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_category_type_id` (`category_type_id`),
  CONSTRAINT `fk_category_category_type_id` FOREIGN KEY (`category_type_id`) REFERENCES `category_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'SQL Intro','Introduction to SQL',_binary '\0',NULL,NULL,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1),(2,'SQL Syntax','SQL Syntax',_binary '\0',NULL,'Customers','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1),(3,'SQL Select','SQL SELECT Statement',_binary '',NULL,'Customers','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1),(4,'SQL Select Distinct','SQL SELECT DISTINCT Statement',_binary '',NULL,'Customers','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_type`
--

DROP TABLE IF EXISTS `category_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_category_type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_type`
--

LOCK TABLES `category_type` WRITE;
/*!40000 ALTER TABLE `category_type` DISABLE KEYS */;
INSERT INTO `category_type` VALUES (1,'SQL Tutorial','SQL is a standard language for storing, manipulating and retrieving data in databases.  Our SQL tutorial will teach you how to use SQL in: MySQL, SQL Server, MS Access, Oracle, Sybase, Informix, Postgres, and other database systems.','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(2,'SQL Database','SQL Database','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(3,'SQL References','SQL References','2019-04-01 05:31:00',1,'2019-04-24 05:31:00',1),(4,'SQL Examples','SQL Examples','2019-04-12 05:31:00',1,'2019-04-24 05:31:00',1);
/*!40000 ALTER TABLE `category_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `content` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `type_content_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_content_category_id` (`category_id`),
  KEY `fk_content_type_content_id` (`type_content_id`),
  CONSTRAINT `fk_content_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_content_type_content_id` FOREIGN KEY (`type_content_id`) REFERENCES `type_content` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
INSERT INTO `content` VALUES (1,'What is SQL?','SQL stands for Structured Query Language. SQL lets you access and manipulate databases. SQL became a standard of the American National Standards Institute (ANSI) in 1986, and of the International Organization for Standardization (ISO) in 1987','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1,1),(2,'What Can SQL do?','SQL can execute queries against a database. SQL can retrieve data from a database. SQL can insert records in a database. SQL can update records in a database','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1,1),(3,'SQL is a Standard - BUT....','Although SQL is an ANSI/ISO standard, there are different versions of the SQL language. However, to be compliant with the ANSI standard, they all support at least the major commands (such as SELECT, UPDATE, DELETE, INSERT, WHERE) in a similar manner','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1,1),(4,'Note','Most of the SQL database programs also have their own proprietary extensions in addition to the SQL standard!','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1,2),(5,'Database Tables','A database most often contains one or more tables. Each table is identified by a name (e.g. \"Customers\" or \"Orders\"). Tables contain records (rows) with data.','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,2,4),(6,'Semicolon after SQL Statements?','Some database systems require a semicolon at the end of each SQL statement.  Semicolon is the standard way to separate each SQL statement in database systems that allow more than one SQL statement to be executed in the same call to the server.','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,2,1),(7,'Some of The Most Important SQL Commands','SELECT - extracts data from a database. UPDATE - updates data in a database. DELETE - deletes data from a database','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,2,1),(8,'The SQL SELECT Statement','The SELECT statement is used to select data from a database. The data returned is stored in a result table, called the result-set.','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3,1),(9,'SELECT Syntax','SELECT column1, column2, ... FROM table_name;','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3,3),(10,'Demo Database','Below is a selection from the \"Customers\" table in the Northwind sample database:','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3,4),(11,'The SQL SELECT DISTINCT Statement','The SELECT DISTINCT statement is used to return only distinct (different) values.  Inside a table, a column often contains many duplicate values; and sometimes you only want to list the different (distinct) values.','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,4,1),(12,'SELECT DISTINCT Syntax','SELECT DISTINCT column1, column2, ... FROM table_name;','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,4,3),(13,'Demo Database','Below is a selection from the \"Customers\" table in the Northwind sample database:','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,4,4);
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `contact_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `postal_code` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AUTHOR` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `FILENAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `MD5SUM` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENTS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TAG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LIQUIBASE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONTEXTS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LABELS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2019-04-23 18:20:43',1,'EXECUTED','7:41601557597e1094de7fb3656d819edf','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111348-1','jhipster','config/liquibase/changelog/20190423111348_added_entity_CategoryType.xml','2019-04-23 18:20:43',2,'EXECUTED','7:6419172b8bde7cdf4c868aee9ee12349','createTable tableName=category_type; dropDefaultValue columnName=created_date, tableName=category_type; dropDefaultValue columnName=updated_date, tableName=category_type','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111349-1','jhipster','config/liquibase/changelog/20190423111349_added_entity_Category.xml','2019-04-23 18:20:43',3,'EXECUTED','7:1e112985ae3ec69cb6e58e226a1d2b32','createTable tableName=category; dropDefaultValue columnName=created_date, tableName=category; dropDefaultValue columnName=updated_date, tableName=category','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111350-1','jhipster','config/liquibase/changelog/20190423111350_added_entity_Content.xml','2019-04-23 18:20:44',4,'EXECUTED','7:dc4f270dee5ebdc5d21921ef8c5a34e7','createTable tableName=content; dropDefaultValue columnName=created_date, tableName=content; dropDefaultValue columnName=updated_date, tableName=content','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111351-1','jhipster','config/liquibase/changelog/20190423111351_added_entity_SQLQuery.xml','2019-04-23 18:20:44',5,'EXECUTED','7:fb755b55e844ad5cede58156b28bf056','createTable tableName=sql_query; dropDefaultValue columnName=created_date, tableName=sql_query; dropDefaultValue columnName=updated_date, tableName=sql_query','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111352-1','jhipster','config/liquibase/changelog/20190423111352_added_entity_TypeContent.xml','2019-04-23 18:20:44',6,'EXECUTED','7:4023f8949bdd4cfbc8574b64ea7ac90d','createTable tableName=type_content; dropDefaultValue columnName=created_date, tableName=type_content; dropDefaultValue columnName=updated_date, tableName=type_content','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111353-1','jhipster','config/liquibase/changelog/20190423111353_added_entity_Exercises.xml','2019-04-23 18:20:44',7,'EXECUTED','7:32c5d9180a5b237ceb7282e9a984a9b6','createTable tableName=exercises; dropDefaultValue columnName=created_date, tableName=exercises; dropDefaultValue columnName=updated_date, tableName=exercises','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111354-1','jhipster','config/liquibase/changelog/20190423111354_added_entity_ExercisesAnswer.xml','2019-04-23 18:20:44',8,'EXECUTED','7:69c8440732a9e0f1ad5b8279a78f4b0c','createTable tableName=exercises_answer; dropDefaultValue columnName=created_date, tableName=exercises_answer; dropDefaultValue columnName=updated_date, tableName=exercises_answer','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111355-1','jhipster','config/liquibase/changelog/20190423111355_added_entity_Orders.xml','2019-04-23 18:20:45',9,'EXECUTED','7:5557c5f6bb6c20042e552a8cdc4dfb35','createTable tableName=orders; dropDefaultValue columnName=order_date, tableName=orders','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111356-1','jhipster','config/liquibase/changelog/20190423111356_added_entity_Customer.xml','2019-04-23 18:20:45',10,'EXECUTED','7:040d2dd2923e4804138e834d4923bac6','createTable tableName=customer','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111357-1','jhipster','config/liquibase/changelog/20190423111357_added_entity_Employees.xml','2019-04-23 18:20:45',11,'EXECUTED','7:d0a881afd22e9ab23ae7ba779cec332b','createTable tableName=employees; dropDefaultValue columnName=birth_date, tableName=employees','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111358-1','jhipster','config/liquibase/changelog/20190423111358_added_entity_Shipper.xml','2019-04-23 18:20:45',12,'EXECUTED','7:650f68a81438adb351438030c6640630','createTable tableName=shipper','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111349-2','jhipster','config/liquibase/changelog/20190423111349_added_entity_constraints_Category.xml','2019-04-23 18:20:45',13,'EXECUTED','7:f7e7604de585c54d2c0a6a806b36b62d','addForeignKeyConstraint baseTableName=category, constraintName=fk_category_category_type_id, referencedTableName=category_type','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111350-2','jhipster','config/liquibase/changelog/20190423111350_added_entity_constraints_Content.xml','2019-04-23 18:20:46',14,'EXECUTED','7:28d925f20cc0572c4b70dcc51643bb6f','addForeignKeyConstraint baseTableName=content, constraintName=fk_content_category_id, referencedTableName=category; addForeignKeyConstraint baseTableName=content, constraintName=fk_content_type_content_id, referencedTableName=type_content','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111353-2','jhipster','config/liquibase/changelog/20190423111353_added_entity_constraints_Exercises.xml','2019-04-23 18:20:46',15,'EXECUTED','7:a276cb85fc2bc56f9d93978ef5858420','addForeignKeyConstraint baseTableName=exercises, constraintName=fk_exercises_category_id, referencedTableName=category','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111354-2','jhipster','config/liquibase/changelog/20190423111354_added_entity_constraints_ExercisesAnswer.xml','2019-04-23 18:20:46',16,'EXECUTED','7:33dc9b57958ec294e776601fd093f843','addForeignKeyConstraint baseTableName=exercises_answer, constraintName=fk_exercises_answer_exercises_id, referencedTableName=exercises','',NULL,'3.5.4',NULL,NULL,'6018441360'),('20190423111355-2','jhipster','config/liquibase/changelog/20190423111355_added_entity_constraints_Orders.xml','2019-04-23 18:20:47',17,'EXECUTED','7:69f1aea5fdc4070329357e4a1850745d','addForeignKeyConstraint baseTableName=orders, constraintName=fk_orders_customer_id, referencedTableName=customer; addForeignKeyConstraint baseTableName=orders, constraintName=fk_orders_employees_id, referencedTableName=employees; addForeignKeyCons...','',NULL,'3.5.4',NULL,NULL,'6018441360');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employees` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `birth_date` datetime NOT NULL,
  `notes` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercises`
--

DROP TABLE IF EXISTS `exercises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exercises` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_exercises` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `question` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `score` int(11) NOT NULL,
  `time_on_minutes` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exercises_category_id` (`category_id`),
  CONSTRAINT `fk_exercises_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercises`
--

LOCK TABLES `exercises` WRITE;
/*!40000 ALTER TABLE `exercises` DISABLE KEYS */;
INSERT INTO `exercises` VALUES (1,'Exercise 1','Insert the missing statement to get all the columns from the Customers table.',100,60,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3),(2,'Exercise 2','Write a statement that will select the City column from the Customers table.',100,60,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3),(3,'Exercise 3','Select all the different values from the Country column in the Customers table.',100,60,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3);
/*!40000 ALTER TABLE `exercises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercises_answer`
--

DROP TABLE IF EXISTS `exercises_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exercises_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `result` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  `exercises_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exercises_answer_exercises_id` (`exercises_id`),
  CONSTRAINT `fk_exercises_answer_exercises_id` FOREIGN KEY (`exercises_id`) REFERENCES `exercises` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercises_answer`
--

LOCK TABLES `exercises_answer` WRITE;
/*!40000 ALTER TABLE `exercises_answer` DISABLE KEYS */;
INSERT INTO `exercises_answer` VALUES (1,' SELECT  * FROM Customers;',_binary '','2019-04-24 05:31:00',1,'2019-04-24 05:32:00',1,1),(2,' SELECT  * FROM Customers',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1),(3,' SELECT  * FROM Customers.',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,1),(4,' SELECT City FROM Customers;',_binary '','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,2),(5,' SELECT City FROM Customers',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,2),(6,' SELECT * FROM Customers;',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,2),(7,' SELECT DISTINCT Country FROM Customers;',_binary '','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3),(8,' SELECT Country FROM Customers;',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3),(9,' SELECT * Country FROM Customers',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3),(10,' SELECT DISTINCT Country -> Customers;',_binary '\0','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1,3);
/*!40000 ALTER TABLE `exercises_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_event`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_event`
--

LOCK TABLES `jhi_persistent_audit_event` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_event` DISABLE KEYS */;
INSERT INTO `jhi_persistent_audit_event` VALUES (1,'admin','2019-04-23 04:21:34','AUTHENTICATION_SUCCESS'),(2,'admin','2019-04-23 04:22:36','AUTHENTICATION_SUCCESS'),(3,'admin','2019-04-23 04:35:57','AUTHENTICATION_SUCCESS'),(4,'admin','2019-04-23 05:15:18','AUTHENTICATION_SUCCESS'),(5,'admin','2019-04-23 23:59:49','AUTHENTICATION_SUCCESS'),(6,'admin','2019-04-24 00:24:17','AUTHENTICATION_SUCCESS'),(7,'admin','2019-04-24 00:24:20','AUTHENTICATION_SUCCESS'),(8,'admin','2019-04-24 00:55:09','AUTHENTICATION_SUCCESS');
/*!40000 ALTER TABLE `jhi_persistent_audit_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_evt_data`
--

LOCK TABLES `jhi_persistent_audit_evt_data` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password_hash` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(191) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_url` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activation_key` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_key` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','',_binary '','en',NULL,NULL,'system',NULL,NULL,'system',NULL),(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','',_binary '','en',NULL,NULL,'system',NULL,NULL,'system',NULL),(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',_binary '','en',NULL,NULL,'system',NULL,NULL,'system',NULL),(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',_binary '','en',NULL,NULL,'system',NULL,NULL,'system',NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(3,'ROLE_ADMIN'),(1,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_date` datetime NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `employees_id` bigint(20) DEFAULT NULL,
  `shipper_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_customer_id` (`customer_id`),
  KEY `fk_orders_employees_id` (`employees_id`),
  KEY `fk_orders_shipper_id` (`shipper_id`),
  CONSTRAINT `fk_orders_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_orders_employees_id` FOREIGN KEY (`employees_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `fk_orders_shipper_id` FOREIGN KEY (`shipper_id`) REFERENCES `shipper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipper`
--

DROP TABLE IF EXISTS `shipper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shipper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipper_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipper`
--

LOCK TABLES `shipper` WRITE;
/*!40000 ALTER TABLE `shipper` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sql_query`
--

DROP TABLE IF EXISTS `sql_query`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sql_query` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `name_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `query` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sql_query`
--

LOCK TABLES `sql_query` WRITE;
/*!40000 ALTER TABLE `sql_query` DISABLE KEYS */;
INSERT INTO `sql_query` VALUES (1,'SQL SELECT','select_columns','SELECT CustomerName,City FROM Customers;','SELECT Column','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(2,'SQL SELECT','select_all','SELECT * FROM Customers;','SELECT *','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(3,'SQL SELECT DISTINCT','select_distinct','SELECT DISTINCT Country FROM Customers;','SELECT DISTINCT','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(4,'SQL SELECT DISTINCT','select_distinct2','SELECT COUNT(DISTINCT Country) FROM Customers;',' SELECT COUNT(DISTINCT column_name)','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(5,'SQL SELECT DISTINCT','select_distinct3','SELECT Count(*) AS DistinctCountries FROM (SELECT DISTINCT Country FROM Customers);',' SELECT COUNT(DISTINCT column_name) workaround for MS Access','2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1);
/*!40000 ALTER TABLE `sql_query` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_content`
--

DROP TABLE IF EXISTS `type_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `type_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_type_content` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `priority` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_date` datetime NOT NULL,
  `updated_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_content`
--

LOCK TABLES `type_content` WRITE;
/*!40000 ALTER TABLE `type_content` DISABLE KEYS */;
INSERT INTO `type_content` VALUES (1,'Paragraph',1,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(2,'Note',2,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(3,'Syntax',3,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1),(4,'Table Data',4,'2019-04-24 05:31:00',1,'2019-04-24 05:31:00',1);
/*!40000 ALTER TABLE `type_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-25 14:46:44

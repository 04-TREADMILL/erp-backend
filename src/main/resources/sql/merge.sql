-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: 43.142.159.174    Database: seec_erp_test
-- ------------------------------------------------------
-- Server version	8.0.29-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `name` varchar(31) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `account_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('0123456789',40000.00),('core',114514.00);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annual_bonus`
--

DROP TABLE IF EXISTS `annual_bonus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `annual_bonus` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '年终奖记录id',
  `eid` int NOT NULL COMMENT '员工id',
  `base_bonus` decimal(10,2) NOT NULL COMMENT '基础年终奖',
  `extra_bonus` decimal(10,2) NOT NULL COMMENT '额外年终奖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annual_bonus`
--

LOCK TABLES `annual_bonus` WRITE;
/*!40000 ALTER TABLE `annual_bonus` DISABLE KEYS */;
INSERT INTO `annual_bonus` VALUES (1,59,30000.00,10000.00);
/*!40000 ALTER TABLE `annual_bonus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `parent_id` int NOT NULL COMMENT '父节点id',
  `is_leaf` tinyint NOT NULL COMMENT '是否为叶节点',
  `item_count` int NOT NULL COMMENT '商品个数',
  `item_index` int NOT NULL COMMENT '插入的下一个index',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'商品',0,0,0,0),(2,'电子产品',1,0,0,0),(3,'生活用品',1,0,0,0),(4,'电脑',2,1,3,3),(5,'手机',2,1,3,3),(6,'卫生纸',3,1,0,0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combine_promotion_strategy`
--

DROP TABLE IF EXISTS `combine_promotion_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combine_promotion_strategy` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '策略id',
  `pid_combination` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '组合商品的商品号相连接，空格相隔',
  `amount` decimal(10,2) NOT NULL COMMENT '优惠的数额',
  `begin_time` date NOT NULL COMMENT '开始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combine_promotion_strategy`
--

LOCK TABLES `combine_promotion_strategy` WRITE;
/*!40000 ALTER TABLE `combine_promotion_strategy` DISABLE KEYS */;
INSERT INTO `combine_promotion_strategy` VALUES (1,'0000000000400000 0000000000400001',2000.00,'2022-07-07','2022-07-07');
/*!40000 ALTER TABLE `combine_promotion_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类(供应商销售商)',
  `level` int DEFAULT NULL COMMENT '级别（五级，一级普通用户，五级VIP客户）',
  `name` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `phone` varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `zipcode` char(6) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮编',
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `line_of_credit` decimal(10,2) DEFAULT NULL COMMENT '应收额度本公司给客户的信用额度，客户欠本公司的钱的总额不能超过应收额度）',
  `receivable` decimal(10,2) DEFAULT NULL COMMENT '应收（客户还应付给本公司但还未付的钱， 即本公司应收的钱）',
  `payable` decimal(10,2) DEFAULT NULL COMMENT '应付（本公司欠客户的钱）',
  `operator` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '默认业务员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'供应商',1,'yzh','12306','南京大学1','123456','654321@abc.com',0.00,0.00,6500000.00,'uncln'),(2,'销售商',1,'lxs','12580','南哪儿大学','123457','12121@cba.com',20000000.00,4431400.00,0.00,'uncln'),(3,'供应商',5,'rockm3','18330877258','无1','061600',NULL,NULL,0.00,0.00,'admin');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_promotion_strategy`
--

DROP TABLE IF EXISTS `customer_promotion_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_promotion_strategy` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '策略id',
  `level` int NOT NULL COMMENT '针对的客户级别',
  `discount` decimal(10,2) NOT NULL COMMENT '折让比例',
  `amount` decimal(10,2) NOT NULL COMMENT '优惠的数额',
  `begin_time` date NOT NULL COMMENT '开始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_promotion_strategy`
--

LOCK TABLES `customer_promotion_strategy` WRITE;
/*!40000 ALTER TABLE `customer_promotion_strategy` DISABLE KEYS */;
INSERT INTO `customer_promotion_strategy` VALUES (1,2,0.90,200.00,'2022-07-07','2022-07-07');
/*!40000 ALTER TABLE `customer_promotion_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
  `gender` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工性别',
  `birthday` date NOT NULL COMMENT '员工生日',
  `phone` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工手机号码',
  `role` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工岗位',
  `basic_salary` decimal(10,2) NOT NULL COMMENT '基本工资',
  `post_salary` decimal(10,2) NOT NULL COMMENT '岗位工资',
  `salary_granting_mode` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '薪资发放方式',
  `salary_calculating_mode` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '薪资计算方式',
  `account` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '工资卡账户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_punch`
--

DROP TABLE IF EXISTS `employee_punch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_punch` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '打卡记录id',
  `eid` int NOT NULL COMMENT '员工id',
  `punch_time` datetime NOT NULL COMMENT '打卡时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_punch`
--

LOCK TABLES `employee_punch` WRITE;
/*!40000 ALTER TABLE `employee_punch` DISABLE KEYS */;
INSERT INTO `employee_punch` VALUES (1,17,'2022-07-01 17:23:53'),(2,17,'3922-07-30 00:00:00'),(3,17,'3922-07-29 05:05:05'),(5,4,'2022-07-10 09:39:22');
/*!40000 ALTER TABLE `employee_punch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_sheet`
--

DROP TABLE IF EXISTS `payment_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_sheet` (
  `id` varchar(31) NOT NULL,
  `supplier` int NOT NULL,
  `operator` varchar(31) NOT NULL,
  `account` varchar(31) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `comment` varchar(31) DEFAULT NULL,
  `state` varchar(31) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_sheet`
--

LOCK TABLES `payment_sheet` WRITE;
/*!40000 ALTER TABLE `payment_sheet` DISABLE KEYS */;
INSERT INTO `payment_sheet` VALUES ('FKD-20220704-00000',1,'jyy','core',1.00,NULL,'待审批','2022-07-10 14:01:46'),('FKD-20220704-00001',1,'jyy','core',1.00,NULL,'审批完成','2022-07-10 14:02:22');
/*!40000 ALTER TABLE `payment_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` char(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id(11位) + 位置(5位) = 编号',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `category_id` int NOT NULL COMMENT '商品分类id',
  `type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品型号',
  `quantity` int NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10,2) NOT NULL COMMENT '进价',
  `retail_price` decimal(10,2) NOT NULL COMMENT '零售价',
  `recent_pp` decimal(10,2) DEFAULT NULL COMMENT '最近进价',
  `recent_rp` decimal(10,2) DEFAULT NULL COMMENT '最近零售价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('0000000000400000','戴尔电脑',4,'戴尔(DELL)Vostro笔记本电脑5410 123色 戴尔成就3500Vostro1625D',500,3000.00,4056.00,1900.00,3000.00),('0000000000400001','小米手机',4,'lalalalala',1000,2000.00,3500.00,2700.00,4200.00),('0000000000400002','惠普',4,'惠普暗夜精灵',0,6000.00,8999.00,NULL,NULL),('0000000000500000','intel电脑',5,'iphone14maxpro',0,6000.00,10000.00,NULL,NULL),('0000000000500001','iphone',5,'iphone14普通版',0,4000.00,8000.00,NULL,NULL),('0000000000500002','坚果',5,'pro3',0,2499.00,3199.00,NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_returns_sheet`
--

DROP TABLE IF EXISTS `purchase_returns_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_returns_sheet` (
  `id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '进货退货单id',
  `purchase_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联的进货单id',
  `operator` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '退货的总金额',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_returns_sheet`
--

LOCK TABLES `purchase_returns_sheet` WRITE;
/*!40000 ALTER TABLE `purchase_returns_sheet` DISABLE KEYS */;
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00000','JHD-20220523-00001','xiaoshoujingli','审批完成','2022-05-23 23:22:41',800000.00,'退钱！'),('JHTHD-20220523-00001','JHD-20220523-00000','xiaoshoujingli','审批完成','2022-05-23 23:22:54',500000.00,'退钱！！！'),('JHTHD-20220523-00002','JHD-20220523-00000','xiaoshoujingli','审批完成','2022-05-23 23:34:34',100000.00,'退钱++++'),('JHTHD-20220523-00003','JHD-20220523-00000','xiaoshoujingli','审批完成','2022-05-23 23:39:30',200000.00,'mmmmm'),('JHTHD-20220523-00004','JHD-20220523-00000','67','审批完成','2022-05-23 23:42:32',200000.00,'mmmmk'),('JHTHD-20220524-00000','JHD-20220523-00001','xiaoshoujingli','待二级审批','2022-05-24 01:00:18',160000.00,NULL),('JHTHD-20220524-00001','JHD-20220523-00002','xiaoshoujingli','待一级审批','2022-05-24 01:00:34',140000.00,NULL),('JHTHD-20220708-00000','JHD-20220523-00000','sky','待一级审批','2022-07-08 20:19:51',1000000.00,NULL);
/*!40000 ALTER TABLE `purchase_returns_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_returns_sheet_content`
--

DROP TABLE IF EXISTS `purchase_returns_sheet_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_returns_sheet_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_returns_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '进货退货单id',
  `pid` char(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品id',
  `quantity` int DEFAULT NULL COMMENT '数量',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '该商品的总金额',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '该商品的单价',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_returns_sheet_content`
--

LOCK TABLES `purchase_returns_sheet_content` WRITE;
/*!40000 ALTER TABLE `purchase_returns_sheet_content` DISABLE KEYS */;
INSERT INTO `purchase_returns_sheet_content` VALUES (1,'JHTHD-20220523-00000','0000000000400000',500,600000.00,1200.00,'b'),(2,'JHTHD-20220523-00000','0000000000400001',100,200000.00,2000.00,'b'),(3,'JHTHD-20220523-00001','0000000000400000',500,500000.00,1000.00,'a'),(4,'JHTHD-20220523-00002','0000000000400000',100,100000.00,1000.00,'a'),(5,'JHTHD-20220523-00003','0000000000400000',200,200000.00,1000.00,'a'),(6,'JHTHD-20220523-00004','0000000000400000',200,200000.00,1000.00,'a'),(7,'JHTHD-20220524-00000','0000000000400000',50,60000.00,1200.00,'b'),(8,'JHTHD-20220524-00000','0000000000400001',50,100000.00,2000.00,'b'),(9,'JHTHD-20220524-00001','0000000000400000',0,0.00,1300.00,'c'),(10,'JHTHD-20220524-00001','0000000000400001',50,140000.00,2800.00,'c'),(11,'JHTHD-20220708-00000','0000000000400000',1000,1000000.00,1000.00,'a');
/*!40000 ALTER TABLE `purchase_returns_sheet_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_sheet`
--

DROP TABLE IF EXISTS `purchase_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_sheet` (
  `id` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT '进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx',
  `supplier` int DEFAULT NULL COMMENT '供应商',
  `operator` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总额合计',
  `state` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_sheet`
--

LOCK TABLES `purchase_sheet` WRITE;
/*!40000 ALTER TABLE `purchase_sheet` DISABLE KEYS */;
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00000',1,'xiaoshoujingli','a',1000000.00,'审批完成','2022-05-23 23:13:59'),('JHD-20220523-00001',1,'xiaoshoujingli','b',2200000.00,'审批完成','2022-05-23 23:14:34'),('JHD-20220523-00002',1,'xiaoshoujingli','c',3450000.00,'审批完成','2022-05-23 23:15:57'),('JHD-20220524-00000',1,'xiaoshoujingli',NULL,2200000.00,'待二级审批','2022-05-24 00:56:54'),('JHD-20220524-00001',1,'xiaoshoujingli',NULL,3240000.00,'待一级审批','2022-05-24 00:57:29'),('JHD-20220524-00002',1,'xiaoshoujingli',NULL,1650000.00,'审批完成','2022-05-24 01:02:04'),('JHD-20220708-00000',1,'sky',NULL,3000000.00,'待一级审批','2022-07-08 20:20:57');
/*!40000 ALTER TABLE `purchase_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_sheet_content`
--

DROP TABLE IF EXISTS `purchase_sheet_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_sheet_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '进货单id',
  `pid` char(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品id',
  `quantity` int DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_sheet_content`
--

LOCK TABLES `purchase_sheet_content` WRITE;
/*!40000 ALTER TABLE `purchase_sheet_content` DISABLE KEYS */;
INSERT INTO `purchase_sheet_content` VALUES (1,'JHD-20220523-00000','0000000000400000',1000,1000.00,1000000.00,'a'),(2,'JHD-20220523-00001','0000000000400000',1000,1200.00,1200000.00,'b'),(3,'JHD-20220523-00001','0000000000400001',500,2000.00,1000000.00,'b'),(4,'JHD-20220523-00002','0000000000400000',500,1300.00,650000.00,'c'),(5,'JHD-20220523-00002','0000000000400001',1000,2800.00,2800000.00,'c'),(6,'JHD-20220524-00000','0000000000400000',500,1500.00,750000.00,''),(7,'JHD-20220524-00000','0000000000400001',500,2900.00,1450000.00,NULL),(8,'JHD-20220524-00001','0000000000400000',600,1900.00,1140000.00,''),(9,'JHD-20220524-00001','0000000000400001',700,3000.00,2100000.00,NULL),(10,'JHD-20220524-00002','0000000000400000',300,1900.00,570000.00,''),(11,'JHD-20220524-00002','0000000000400001',400,2700.00,1080000.00,NULL),(12,'JHD-20220708-00000','0000000000400002',500,6000.00,3000000.00,'');
/*!40000 ALTER TABLE `purchase_sheet_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_sheet`
--

DROP TABLE IF EXISTS `receipt_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_sheet` (
  `id` varchar(31) NOT NULL,
  `seller` int NOT NULL,
  `operator` varchar(31) NOT NULL,
  `account` varchar(31) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `comment` varchar(31) DEFAULT NULL,
  `state` varchar(31) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_sheet`
--

LOCK TABLES `receipt_sheet` WRITE;
/*!40000 ALTER TABLE `receipt_sheet` DISABLE KEYS */;
INSERT INTO `receipt_sheet` VALUES ('SKD-20220704-00000',2,'jyy','core',1.00,NULL,'待审批','2022-07-10 14:05:24'),('SKD-20220704-00001',2,'jyy','core',1.00,NULL,'审批完成','2022-07-10 14:05:37');
/*!40000 ALTER TABLE `receipt_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary_sheet`
--

DROP TABLE IF EXISTS `salary_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salary_sheet` (
  `id` varchar(31) NOT NULL,
  `employee_id` int DEFAULT NULL,
  `name` varchar(31) DEFAULT NULL,
  `account` varchar(31) DEFAULT NULL,
  `original_salary` decimal(10,2) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT NULL,
  `real_salary` decimal(10,2) DEFAULT NULL,
  `state` varchar(31) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary_sheet`
--

LOCK TABLES `salary_sheet` WRITE;
/*!40000 ALTER TABLE `salary_sheet` DISABLE KEYS */;
INSERT INTO `salary_sheet` VALUES ('GZD-20220605-00000',17,'lyc','core',16000.00,1600.00,14400.00,'待审核','2022-07-10 10:17:09');
/*!40000 ALTER TABLE `salary_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_returns_sheet`
--

DROP TABLE IF EXISTS `sale_returns_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_returns_sheet` (
  `id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售退货单id',
  `purchase_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联的销售单id',
  `operator` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '退货的总金额',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_returns_sheet`
--

LOCK TABLES `sale_returns_sheet` WRITE;
/*!40000 ALTER TABLE `sale_returns_sheet` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_returns_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_returns_sheet_content`
--

DROP TABLE IF EXISTS `sale_returns_sheet_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_returns_sheet_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_returns_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售退货单id',
  `pid` char(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品id',
  `quantity` int DEFAULT NULL COMMENT '数量',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '该商品的总金额',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '该商品的单价',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_returns_sheet_content`
--

LOCK TABLES `sale_returns_sheet_content` WRITE;
/*!40000 ALTER TABLE `sale_returns_sheet_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_returns_sheet_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_sheet`
--

DROP TABLE IF EXISTS `sale_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_sheet` (
  `id` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT '进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx',
  `supplier` int DEFAULT NULL COMMENT '供应商',
  `operator` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `state` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `salesman` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务员',
  `raw_total_amount` decimal(10,2) DEFAULT NULL COMMENT '折让前总金额',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `final_amount` decimal(10,2) DEFAULT NULL COMMENT '折让后金额',
  `voucher_amount` decimal(10,2) DEFAULT NULL COMMENT '代金券金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_sheet`
--

LOCK TABLES `sale_sheet` WRITE;
/*!40000 ALTER TABLE `sale_sheet` DISABLE KEYS */;
INSERT INTO `sale_sheet` VALUES ('XSD-20220523-00000',2,'xiaoshoujingli','卖卖卖','审批失败','2022-05-23 23:46:12','xiaoshoujingli',1300000.00,0.80,1039800.00,200.00),('XSD-20220524-00000',2,'xiaoshoujingli',NULL,'审批完成','2022-05-24 00:04:37','xiaoshoujingli',4200000.00,0.80,3359800.00,200.00),('XSD-20220524-00001',2,'xiaoshoujingli',NULL,'审批完成','2022-05-24 00:32:41','xiaoshoujingli',620000.00,0.80,495800.00,200.00),('XSD-20220524-00002',2,'xiaoshoujingli',NULL,'审批完成','2022-05-24 00:45:25','xiaoshoujingli',720000.00,0.80,575800.00,200.00),('XSD-20220524-00003',2,'xiaoshoujingli',NULL,'待二级审批','2022-05-24 01:05:15','xiaoshoujingli',660000.00,0.80,527700.00,300.00),('XSD-20220524-00004',2,'xiaoshoujingli',NULL,'待一级审批','2022-05-24 01:07:23','xiaoshoujingli',2900000.00,0.90,2609800.00,200.00);
/*!40000 ALTER TABLE `sale_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_sheet_content`
--

DROP TABLE IF EXISTS `sale_sheet_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_sheet_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '进货单id',
  `pid` char(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品id',
  `quantity` int DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_sheet_content`
--

LOCK TABLES `sale_sheet_content` WRITE;
/*!40000 ALTER TABLE `sale_sheet_content` DISABLE KEYS */;
INSERT INTO `sale_sheet_content` VALUES (1,'XSD-20220523-00000','0000000000400000',100,5000.00,500000.00,'卖卖卖1'),(2,'XSD-20220523-00000','0000000000400001',400,2000.00,800000.00,'卖卖卖2'),(3,'XSD-20220524-00000','0000000000400000',600,3500.00,2100000.00,''),(4,'XSD-20220524-00000','0000000000400001',600,3500.00,2100000.00,NULL),(5,'XSD-20220524-00001','0000000000400000',100,2200.00,220000.00,''),(6,'XSD-20220524-00001','0000000000400001',100,4000.00,400000.00,NULL),(7,'XSD-20220524-00002','0000000000400000',100,3000.00,300000.00,''),(8,'XSD-20220524-00002','0000000000400001',100,4200.00,420000.00,NULL),(9,'XSD-20220524-00003','0000000000400000',100,2800.00,280000.00,''),(10,'XSD-20220524-00003','0000000000400001',100,3800.00,380000.00,NULL),(11,'XSD-20220524-00004','0000000000400000',300,3000.00,900000.00,''),(12,'XSD-20220524-00004','0000000000400001',500,4000.00,2000000.00,NULL);
/*!40000 ALTER TABLE `sale_sheet_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `total_promotion_strategy`
--

DROP TABLE IF EXISTS `total_promotion_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `total_promotion_strategy` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '策略id',
  `condition` decimal(10,2) NOT NULL COMMENT '触发总额促销策略的最低总价',
  `amount` decimal(10,2) NOT NULL COMMENT '优惠的数额',
  `begin_time` date NOT NULL COMMENT '开始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `total_promotion_strategy`
--

LOCK TABLES `total_promotion_strategy` WRITE;
/*!40000 ALTER TABLE `total_promotion_strategy` DISABLE KEYS */;
INSERT INTO `total_promotion_strategy` VALUES (1,5000.00,200.00,'2022-07-04','2022-07-04');
/*!40000 ALTER TABLE `total_promotion_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `role` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户身份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'seecoder','123456','INVENTORY_MANAGER'),(2,'uncln','123456','INVENTORY_MANAGER'),(3,'kucun','123456','INVENTORY_MANAGER'),(4,'sky','123456','ADMIN'),(5,'zxr','123456','SALE_MANAGER'),(6,'67','123456','GM'),(7,'xiaoshou','123456','SALE_STAFF'),(8,'Leone','123456','GM'),(9,'xiaoshoujingli','123456','SALE_MANAGER'),(10,'13323332333','123456','SALE_STAFF'),(11,'18330877258','123456','SALE_MANAGER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `pid` char(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编号',
  `quantity` int NOT NULL COMMENT '数量',
  `purchase_price` decimal(10,2) NOT NULL COMMENT '进价',
  `batch_id` int NOT NULL COMMENT '批次',
  `production_date` datetime DEFAULT NULL COMMENT '出厂日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'0000000000400000',0,1000.00,0,NULL),(2,'0000000000400000',200,1200.00,1,NULL),(3,'0000000000400001',400,2000.00,1,NULL),(4,'0000000000400000',0,1300.00,2,NULL),(5,'0000000000400001',200,2800.00,2,NULL),(6,'0000000000400000',300,1900.00,3,NULL),(7,'0000000000400001',400,2700.00,3,NULL);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_input_sheet`
--

DROP TABLE IF EXISTS `warehouse_input_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_input_sheet` (
  `id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'RKD + 日期 + index = 入库单编号',
  `batch_id` int NOT NULL COMMENT '批次',
  `operator` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `state` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单据状态',
  `purchase_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联的进货单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_input_sheet`
--

LOCK TABLES `warehouse_input_sheet` WRITE;
/*!40000 ALTER TABLE `warehouse_input_sheet` DISABLE KEYS */;
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00000',0,'kucun','2022-05-23 23:17:41','审批完成','JHD-20220523-00000'),('RKD-20220523-00001',1,'kucun','2022-05-23 23:17:42','审批完成','JHD-20220523-00001'),('RKD-20220523-00002',2,'kucun','2022-05-23 23:17:44','审批完成','JHD-20220523-00002'),('RKD-20220524-00000',3,'kucun','2022-05-24 01:02:31','审批完成','JHD-20220524-00002');
/*!40000 ALTER TABLE `warehouse_input_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_input_sheet_content`
--

DROP TABLE IF EXISTS `warehouse_input_sheet_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_input_sheet_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wi_id` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT '入库单编号',
  `pid` char(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `quantity` int NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10,2) NOT NULL COMMENT '单价',
  `production_date` datetime DEFAULT NULL COMMENT '出厂日期',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_input_sheet_content`
--

LOCK TABLES `warehouse_input_sheet_content` WRITE;
/*!40000 ALTER TABLE `warehouse_input_sheet_content` DISABLE KEYS */;
INSERT INTO `warehouse_input_sheet_content` VALUES (1,'RKD-20220523-00000','0000000000400000',1000,1000.00,NULL,'a'),(2,'RKD-20220523-00001','0000000000400000',1000,1200.00,NULL,'b'),(3,'RKD-20220523-00001','0000000000400001',500,2000.00,NULL,'b'),(4,'RKD-20220523-00002','0000000000400000',500,1300.00,NULL,'c'),(5,'RKD-20220523-00002','0000000000400001',1000,2800.00,NULL,'c'),(6,'RKD-20220524-00000','0000000000400000',300,1900.00,NULL,''),(7,'RKD-20220524-00000','0000000000400001',400,2700.00,NULL,NULL);
/*!40000 ALTER TABLE `warehouse_input_sheet_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_output_sheet`
--

DROP TABLE IF EXISTS `warehouse_output_sheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_output_sheet` (
  `id` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'CKD + date + index = 出库单id',
  `operator` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作员名字',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sale_sheet_id` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售单id',
  `state` varchar(31) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_output_sheet`
--

LOCK TABLES `warehouse_output_sheet` WRITE;
/*!40000 ALTER TABLE `warehouse_output_sheet` DISABLE KEYS */;
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00000','kucun','2022-05-24 00:05:32','XSD-20220524-00000','审批完成'),('CKD-20220524-00001','kucun','2022-05-24 00:33:12','XSD-20220524-00001','审批完成'),('CKD-20220524-00002','kucun','2022-05-24 00:45:38','XSD-20220524-00002','审批完成');
/*!40000 ALTER TABLE `warehouse_output_sheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_output_sheet_content`
--

DROP TABLE IF EXISTS `warehouse_output_sheet_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_output_sheet_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '出库商品列表id',
  `pid` char(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `wo_id` varchar(31) COLLATE utf8mb4_general_ci NOT NULL COMMENT '出库单单据编号',
  `batch_id` int DEFAULT NULL COMMENT '批次',
  `quantity` int NOT NULL COMMENT '数量',
  `sale_price` decimal(10,2) NOT NULL COMMENT '对应批次的单价',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_output_sheet_content`
--

LOCK TABLES `warehouse_output_sheet_content` WRITE;
/*!40000 ALTER TABLE `warehouse_output_sheet_content` DISABLE KEYS */;
INSERT INTO `warehouse_output_sheet_content` VALUES (1,'0000000000400000','CKD-20220524-00000',2,600,3500.00,''),(2,'0000000000400000','CKD-20220524-00000',1,600,3500.00,''),(3,'0000000000400001','CKD-20220524-00000',2,600,3500.00,NULL),(4,'0000000000400000','CKD-20220524-00001',1,100,2200.00,''),(5,'0000000000400001','CKD-20220524-00001',2,100,4000.00,NULL),(6,'0000000000400000','CKD-20220524-00002',1,100,3000.00,''),(7,'0000000000400001','CKD-20220524-00002',2,100,4200.00,NULL);
/*!40000 ALTER TABLE `warehouse_output_sheet_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-10 14:27:44

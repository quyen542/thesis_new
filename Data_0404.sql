CREATE
DATABASE  IF NOT EXISTS `thesis_new` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION=''N'' */;
USE
`thesis_new`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: thesis_new
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE=''+00:00'' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=''NO_AUTO_VALUE_ON_ZERO'' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `date`    date   DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`),
    CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK
TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart`
VALUES (1, ''2024 - 03 - 22 '', 4),
       (2, ''2024 - 03 - 25 '', 5),
       (3, ''2024 - 04 - 02 '', 6);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `cartitem`
--

DROP TABLE IF EXISTS `cartitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartitem`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `date`        date   DEFAULT NULL,
    `quantity`    int    NOT NULL,
    `total_price` double NOT NULL,
    `cart_id`     bigint DEFAULT NULL,
    `food_id`     bigint DEFAULT NULL,
    `order_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FKcj0jvvlv7mum72m5qblw5m1tc` (`cart_id`),
    KEY           `FK5qxiibw1glle7bnyavvbmy415` (`food_id`),
    KEY           `FKbdu82nk8udx2brpc2drph5eo` (`order_id`),
    CONSTRAINT `FK5qxiibw1glle7bnyavvbmy415` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`),
    CONSTRAINT `FKbdu82nk8udx2brpc2drph5eo` FOREIGN KEY (`order_id`) REFERENCES `foodorder` (`id`),
    CONSTRAINT `FKcj0jvvlv7mum72m5qblw5m1tc` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartitem`
--

LOCK
TABLES `cartitem` WRITE;
/*!40000 ALTER TABLE `cartitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartitem` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `delivery_info`
--

DROP TABLE IF EXISTS `delivery_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_info`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `available`   bit(1) NOT NULL,
    `salary`      double DEFAULT NULL,
    `delivery_id` bigint DEFAULT NULL,
    `rating`      double DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_dbmf70tal5wm37vuwipxkrj33` (`delivery_id`),
    CONSTRAINT `FKlq9pii97df0o1p2fosbtrslvn` FOREIGN KEY (`delivery_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_info`
--

LOCK
TABLES `delivery_info` WRITE;
/*!40000 ALTER TABLE `delivery_info` DISABLE KEYS */;
INSERT INTO `delivery_info`
VALUES (1, _binary '''', 24, 2, 3.3),
       (2, _binary '''', 2, 3, NULL);
/*!40000 ALTER TABLE `delivery_info` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `food_dislike`
--

DROP TABLE IF EXISTS `food_dislike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_dislike`
(
    `customer_id` bigint NOT NULL,
    `food_id`     bigint NOT NULL,
    KEY           `FK52x0a79rwwtqyxbj60qu5sxcy` (`food_id`),
    KEY           `FKlmynk4gnujdap4txb8fiy8m9i` (`customer_id`),
    CONSTRAINT `FK52x0a79rwwtqyxbj60qu5sxcy` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`),
    CONSTRAINT `FKlmynk4gnujdap4txb8fiy8m9i` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_dislike`
--

LOCK
TABLES `food_dislike` WRITE;
/*!40000 ALTER TABLE `food_dislike` DISABLE KEYS */;
INSERT INTO `food_dislike`
VALUES (6, 7),
       (4, 7),
       (4, 1),
       (4, 6);
/*!40000 ALTER TABLE `food_dislike` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `food_like`
--

DROP TABLE IF EXISTS `food_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_like`
(
    `customer_id` bigint NOT NULL,
    `food_id`     bigint NOT NULL,
    KEY           `FK2dmki9e4jb6cnmw81qotr2usv` (`food_id`),
    KEY           `FK33l8bnpsm0hbheg4ao3ielr7k` (`customer_id`),
    CONSTRAINT `FK2dmki9e4jb6cnmw81qotr2usv` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`),
    CONSTRAINT `FK33l8bnpsm0hbheg4ao3ielr7k` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_like`
--

LOCK
TABLES `food_like` WRITE;
/*!40000 ALTER TABLE `food_like` DISABLE KEYS */;
INSERT INTO `food_like`
VALUES (5, 7),
       (4, 4),
       (4, 10);
/*!40000 ALTER TABLE `food_like` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `food_rating`
--

DROP TABLE IF EXISTS `food_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_rating`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `packaged`    double NOT NULL,
    `price`       double NOT NULL,
    `quality`     double NOT NULL,
    `customer_id` bigint DEFAULT NULL,
    `food_id`     bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FK64j3iskf7bdcluxaw28a46fhw` (`customer_id`),
    KEY           `FKdsf34qkg1ptf1coh4o6mn5rkx` (`food_id`),
    CONSTRAINT `FK64j3iskf7bdcluxaw28a46fhw` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKdsf34qkg1ptf1coh4o6mn5rkx` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_rating`
--

LOCK
TABLES `food_rating` WRITE;
/*!40000 ALTER TABLE `food_rating` DISABLE KEYS */;
INSERT INTO `food_rating`
VALUES (7, 1, 3, 2, 6, 7),
       (8, 2, 2, 2, 4, 7),
       (10, 5, 5, 5, 5, 7),
       (11, 5, 4, 4, 4, 4),
       (12, 2, 1, 2, 4, 1),
       (14, 5, 4, 5, 4, 10),
       (15, 2, 1, 2, 4, 6);
/*!40000 ALTER TABLE `food_rating` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `foodorder`
--

DROP TABLE IF EXISTS `foodorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodorder`
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT,
    `date`               date         DEFAULT NULL,
    `address`            varchar(255) NOT NULL,
    `lat`                varchar(255) DEFAULT NULL,
    `lon`                varchar(255) DEFAULT NULL,
    `name`               varchar(255) DEFAULT NULL,
    `phonenumber`        varchar(255) DEFAULT NULL,
    `status`             tinyint      DEFAULT NULL,
    `total_price`        double       NOT NULL,
    `delivery_person_id` bigint       DEFAULT NULL,
    `user_id`            bigint       DEFAULT NULL,
    `delivery_rating`    double       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                  `FKm14vdpa6v2voqs2oxrfug1mro` (`delivery_person_id`),
    KEY                  `FK248rpiwlr7xyvgxg05jygg7u5` (`user_id`),
    CONSTRAINT `FK248rpiwlr7xyvgxg05jygg7u5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKm14vdpa6v2voqs2oxrfug1mro` FOREIGN KEY (`delivery_person_id`) REFERENCES `user` (`id`),
    CONSTRAINT `foodorder_chk_1` CHECK ((`status` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodorder`
--

LOCK
TABLES `foodorder` WRITE;
/*!40000 ALTER TABLE `foodorder` DISABLE KEYS */;
INSERT INTO `foodorder`
VALUES (6, ''2024 - 03 - 26 '', ''Binh Tri Dong Ward, Binh Tan District, Ho Chi Minh City, 30000, Vietnam '',
        ''10.761032 '', ''106.6166162 '', ''Customer Person 1'', ''01229486861 '', 4, 22, 2, 4, 4),
       (7, ''2024 - 03 - 26 '', ''Binh Tri Dong Ward, Binh Tan District, Ho Chi Minh City, 30000, Vietnam '',
        ''10.761032 '', ''106.6166162 '', ''Customer Person 1'', ''0773566573 '', 4, 23, 3, 4, NULL),
       (8, ''2024 - 04 - 02 '', ''Xã Vĩnh Lộc A, Bình Chánh District, Ho Chi Minh City, 71821, Vietnam '',
        ''10.8199936 '', ''106.57792 '', ''Customer Person 1'', ''0123456789 '', 4, 67, 2, 4, 3),
       (9, ''2024 - 04 - 09 '', ''Tan Tao Ward, Binh Tan District, Ho Chi Minh City, 30000, Vietnam '', ''10.7623239 '',
        ''106.5937774 '', ''Customer Person 1'', ''01229486861 '', 4, 9.25, 2, 4, 3),
       (10, ''2024 - 04 - 09 '', ''Tan Tao Ward, Binh Tan District, Ho Chi Minh City, 30000, Vietnam '',
        ''10.7623239 '', ''106.5937774 '', ''Customer Person 1'', ''0773566573 '', 0, 31, NULL, 4, NULL),
       (11, ''2024 - 04 - 09 '', ''Tan Tao Ward, Binh Tan District, Ho Chi Minh City, 30000, Vietnam '',
        ''10.7623239 '', ''106.5937774 '', ''Customer Person 1'', ''01229486861 '', 0, 52.75, NULL, 4, NULL);
/*!40000 ALTER TABLE `foodorder` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `foods`
--

DROP TABLE IF EXISTS `foods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foods`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `category`        varchar(20) NOT NULL,
    `description`     varchar(250) DEFAULT NULL,
    `name`            varchar(45) NOT NULL,
    `photos`          varchar(64)  DEFAULT NULL,
    `price`           varchar(20) NOT NULL,
    `avg_rating`      double       DEFAULT NULL,
    `packaged_rating` double       DEFAULT NULL,
    `price_rating`    double       DEFAULT NULL,
    `quality_rating`  double       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_lmnpdno9yst912bustvp4vuyr` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foods`
--

LOCK
TABLES `foods` WRITE;
/*!40000 ALTER TABLE `foods` DISABLE KEYS */;
INSERT INTO `foods`
VALUES (1, ''pizza'', ''Shrimp, Peach \"exploded\" together with Thousand Island sauce'',''Seafood & Peach'',''0003102_seafood-peach_300.png'',''11'',1.6666666666666667,2,1,2),(2,''pizza'',''Fresh prawns, succulent crab sticks, ham and juicy pineapples with Thousand Island sauce'',''Seafood Cocktail'',''0002212_sf-cocktail_300.png'',''10'',3,NULL,NULL,NULL),(4,''pizza'',''Shrimps, champignon mushrooms, juicy pineapples, fresh tomatoes and Thousand Island Sauce'',''Shrimp Cocktail'',''0002216_shrimp-ctl_300.png'',''10'',4.333333333333333,5,4,4),(5,''pizza'',''Italian sausages, bacon, ham, green capsicums and tomatoes'',''Bacon Super Delight'',''0002624_seafood-pesto_300.png'',''9.5'',3,NULL,NULL,NULL),(6,''appetizer'',''Fresh, juicy chicken wings marinated in BBQ sauce'',''BBQ Chicken Wings (10 pcs)'',''0002416_bbq-chicken-wings-10-pcs_300.png'',''11.5'',1.6666666666666667,2,1,2),(7,''appetizer'',''Garlic butter on slices of bread'',''Garlic bread'',''0003620_garlic-bread_300.png'',''5'',3,2.7,3.3,3),(8,''appetizer'',NULL,''Caramelized French Onion Cheese Tart'',''0003791_caramelized-french-onion-cheese-tart_300.jpeg'',''6.5'',3,NULL,NULL,NULL),(9,''appetizer'',''Crispy chicken with Cocktail sauce'',''Chicken Strip'',''0003795_chicken-strip_300.jpeg'',''5.5'',3,NULL,NULL,NULL),(10,''pasta'',''Black Truffle with impressive scent, Mushroom Cream Sauce and Ham'',''Pasta Ham Mushroom With Cream & Truffle Sauce'',''0003667_ham-mushroom-w-cream-truffle-sause_300.png'',''7.25'',4.666666666666667,5,4,5),(12,''pasta'',''Pasta stir fried with fresh seafood cooked with lots of spices and garlic'',''Stir fried pasta with spicy seafood'',''0002257_spaghetti-shrimp-rose_300.png'',''7'',3,NULL,NULL,NULL),(14,''pasta'',''Premium Seafood: Shrimp and Squid mixed with Pesto Sauce made from fresh Basil leaves'',''Pasta Seafood With Pesto Sauce'',''0003669_pasta-seafood-w-pesto-sauce_300.png'',''8.25'',3,NULL,NULL,NULL),(15,''pasta'',''The juiciness of fresh shrimps combined with delectable rosé sauce'',''Pasta shrimp rose'',''0002257_spaghetti-shrimp-rose_300.png'',''5.5'',3,NULL,NULL,NULL),(16,''salad'',''Fresh fruits: peach, watermelon, apple, dragon fruit and cherry tomato mixed with signature Peach sauce and Grilled Bacon'',''Fruit Salad with Bacon & Peach Sauce'',''0003668_fruitsaladbaconpeachsauce_300.png'',''4.25'',3,NULL,NULL,NULL),(17,''salad'',''Fresh fruit, vegetable salad served with The Pizza Company\''s special cream sauce.'',''Signature salad'',''0002250_signature-salad_300.png'',''4'',3,NULL,NULL,NULL),(18,''salad'',''Salad with caesar dressing'',''Caesar\''s salad'',''0002251_caesars-salad_300.png'',''4'',3,NULL,NULL,NULL),(19,''drink'',NULL,''Pepsi Can'',''0002365_pepsi-can_300.jpeg'',''1.5'',3,4,5,4),(20,''drink'',NULL,''Aquafina Bottle'',''0002439_aquafina_300.png'',''1.5'',3,NULL,NULL,NULL),(22,''drink'',NULL,''7-up can'',''0003087_7up-fiber-lon-_300.png'',''1.5'',3,NULL,NULL,NULL);
/*!40000 ALTER TABLE `foods` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `quantity`    int    NOT NULL,
    `total_price` double NOT NULL,
    `food_id`     bigint DEFAULT NULL,
    `order_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FKd1euu45pv4s9fb6hxiduh6fgv` (`food_id`),
    KEY           `FKarwkpx9u1abdbefi1mi5ahx0f` (`order_id`),
    CONSTRAINT `FKarwkpx9u1abdbefi1mi5ahx0f` FOREIGN KEY (`order_id`) REFERENCES `foodorder` (`id`),
    CONSTRAINT `FKd1euu45pv4s9fb6hxiduh6fgv` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK
TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item`
VALUES (8, 2, 20, 2, 6),
       (9, 1, 5, 7, 7),
       (10, 2, 14.5, 10, 7),
       (11, 1, 1.5, 19, 7),
       (12, 10, 65, 8, 8),
       (13, 1, 7.25, 10, 9),
       (14, 4, 29, 10, 10),
       (15, 7, 50.75, 10, 11);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK
TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role`
VALUES (1, ''admin''),
       (2, ''customer''),
       (3, ''delivery'');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `phonenumber` varchar(20)  DEFAULT NULL,
    `repassword`  varchar(255) NOT NULL,
    `address`     varchar(200) DEFAULT NULL,
    `email`       varchar(45)  DEFAULT NULL,
    `lat`         varchar(255) DEFAULT NULL,
    `lon`         varchar(255) DEFAULT NULL,
    `name`        varchar(20)  NOT NULL,
    `password`    varchar(255) NOT NULL,
    `username`    varchar(45)  NOT NULL,
    `role_id`     bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
    KEY           `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
    CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK
TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, '''', ''admin'', '''', NULL, NULL, NULL, ''Admintrastor'',
        ''$2a$10$ENfxS754L7JL7uq/cO4C3OgOKCPvOQY6ltnPPAknOJ808swjjsnXS'', ''admin'', 1),
       (2, ''0773566573 '', ''delivery'', '''', NULL, NULL, NULL, ''delivery person 1'',
        ''$2a$10$9f5MpYsVwO8jsiJKA267uu6gCEYlwqfzB2k/XjgGJLAFiMdG5rLY2'', ''delivery01'', 3),
       (3, ''01229486861 '', ''delivery'', '''', NULL, NULL, NULL, ''delivery person 2'',
        ''$2a$10$VU5n5CIcgw04NoCNsPXe9ORw9h52IJyoeR2jhBVphhnYr33JPfmU6'', ''delivery02'', 3),
       (4, '''', ''customer'', '''', NULL, NULL, NULL, ''Customer Person 1'',
        ''$2a$10$GV0JYmciJUoC3kScKu/lkO0mHX8o7ZCYuiWglU/5XEL9g2AaCWwlG'', ''Customer01'', 2),
       (5, '''', ''customer'', '''', NULL, NULL, NULL, ''Customer Person 2'',
        ''$2a$10$drigM6Yof1fQ7kJFw.WTyu7xnhMFI45OixjWNEwrA5D8Z.oshfUAm'', ''Customer02'', 2),
       (6, '''', ''customer'', '''', NULL, NULL, NULL, ''Customer Person 3'',
        ''$2a$10$zjpB029YrfC4DDuaf8LrmuE4AsJWfsXrkmFbc/MP1tQVchK4JxU0u'', ''Customer03'', 2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK
TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-17 11:43:36

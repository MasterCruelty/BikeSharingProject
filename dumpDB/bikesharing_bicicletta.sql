-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: bikesharing
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bicicletta`
--

DROP TABLE IF EXISTS `bicicletta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bicicletta` (
  `biciutente` int DEFAULT NULL,
  `bicirastrelliera` int DEFAULT NULL,
  `tariffa` double NOT NULL,
  `orarioprelievo` varchar(5) DEFAULT NULL,
  `tipologia` varchar(9) NOT NULL,
  `seggiolino` tinyint(1) NOT NULL,
  KEY `biciclettautente_idx` (`biciutente`),
  KEY `biciclettarastrelliera_idx` (`bicirastrelliera`),
  CONSTRAINT `biciclettarastrelliera` FOREIGN KEY (`bicirastrelliera`) REFERENCES `rastrelliera` (`numerorastrelliera`),
  CONSTRAINT `biciclettautente` FOREIGN KEY (`biciutente`) REFERENCES `utenti` (`codiceutente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabella che rappresenta le biciclette legate a una rastrelliera';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bicicletta`
--

LOCK TABLES `bicicletta` WRITE;
/*!40000 ALTER TABLE `bicicletta` DISABLE KEYS */;
INSERT INTO `bicicletta` VALUES (NULL,1,0.5,NULL,'normale',0),(NULL,1,0.5,NULL,'normale',0),(NULL,1,1.5,NULL,'elettrica',0),(NULL,1,1.5,NULL,'elettrica',0),(NULL,1,1.5,NULL,'elettrica',1),(NULL,2,0.5,NULL,'normale',0),(NULL,2,0.5,NULL,'normale',0),(NULL,2,1.5,NULL,'elettrica',0),(NULL,2,1.5,NULL,'elettrica',0),(NULL,2,1.5,NULL,'elettrica',1),(NULL,3,0.5,NULL,'normale',0),(NULL,3,0.5,NULL,'normale',0),(NULL,3,1.5,NULL,'elettrica',0),(NULL,3,1.5,NULL,'elettrica',0),(NULL,3,1.5,NULL,'elettrica',1);
/*!40000 ALTER TABLE `bicicletta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-12 17:52:21

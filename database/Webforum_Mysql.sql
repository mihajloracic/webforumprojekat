CREATE DATABASE  IF NOT EXISTS `webforum` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `webforum`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: localhost    Database: webforum
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `komentar`
--

DROP TABLE IF EXISTS `komentar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `komentar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_tema` int(11) NOT NULL,
  `autor` int(11) NOT NULL,
  `datum_komentara` date NOT NULL,
  `id_parent_komentar` int(11) DEFAULT NULL,
  `tekst_komentar` text,
  `izmenjen` tinyint(4) DEFAULT NULL,
  `obrisan` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tema_komentar_idx` (`id_tema`),
  KEY `fk_autor_komentar_idx` (`autor`),
  CONSTRAINT `fk_autor_komentar` FOREIGN KEY (`autor`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tema_komentar` FOREIGN KEY (`id_tema`) REFERENCES `tema` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `komentar`
--

LOCK TABLES `komentar` WRITE;
/*!40000 ALTER TABLE `komentar` DISABLE KEYS */;
/*!40000 ALTER TABLE `komentar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_komentar`
--

DROP TABLE IF EXISTS `like_komentar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `like_komentar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_komentar` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `like` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_like_komentar_user` (`id_komentar`,`id_user`),
  KEY `fk_like_komentar_k_idx` (`id_komentar`),
  KEY `fk_like_komentar_user_idx` (`id_user`),
  CONSTRAINT `fk_like_komentar_k` FOREIGN KEY (`id_komentar`) REFERENCES `komentar` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_like_komentar_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_komentar`
--

LOCK TABLES `like_komentar` WRITE;
/*!40000 ALTER TABLE `like_komentar` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_komentar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_tema`
--

DROP TABLE IF EXISTS `like_tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `like_tema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_tema` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `like` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_like_user_tema` (`id_tema`,`id_user`),
  KEY `fk_like_tema_idx` (`id_tema`),
  KEY `fk_like_user_idx` (`id_user`),
  CONSTRAINT `fk_like_tema` FOREIGN KEY (`id_tema`) REFERENCES `tema` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_like_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_tema`
--

LOCK TABLES `like_tema` WRITE;
/*!40000 ALTER TABLE `like_tema` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_tema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `podforum`
--

DROP TABLE IF EXISTS `podforum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podforum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) DEFAULT NULL,
  `opis` varchar(100) DEFAULT NULL,
  `ikonica` blob,
  `spisak_pravila` varchar(500) DEFAULT NULL,
  `odgovorni_moderator` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naziv_UNIQUE` (`naziv`),
  KEY `fk_odgovorni_moderator_idx` (`odgovorni_moderator`),
  CONSTRAINT `fk_odgovorni_moderator` FOREIGN KEY (`odgovorni_moderator`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podforum`
--

LOCK TABLES `podforum` WRITE;
/*!40000 ALTER TABLE `podforum` DISABLE KEYS */;
/*!40000 ALTER TABLE `podforum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `podforum_moderator`
--

DROP TABLE IF EXISTS `podforum_moderator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podforum_moderator` (
  `idpodforum_moderator` int(11) NOT NULL AUTO_INCREMENT,
  `id_podforum` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`idpodforum_moderator`),
  KEY `fk_podforum_idx` (`id_podforum`),
  KEY `fk_moderator_idx` (`id_user`),
  CONSTRAINT `fk_moderator` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_podforum` FOREIGN KEY (`id_podforum`) REFERENCES `podforum` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podforum_moderator`
--

LOCK TABLES `podforum_moderator` WRITE;
/*!40000 ALTER TABLE `podforum_moderator` DISABLE KEYS */;
/*!40000 ALTER TABLE `podforum_moderator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `podforum_user_pracenje`
--

DROP TABLE IF EXISTS `podforum_user_pracenje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podforum_user_pracenje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_podforum` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_podforum_pracenje` (`id_podforum`,`id_user`),
  KEY `fk_podforum_user_idx` (`id_user`),
  KEY `fk_podforum_podforum_idx` (`id_podforum`),
  CONSTRAINT `fk_podforum_podforum` FOREIGN KEY (`id_podforum`) REFERENCES `podforum` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_podforum_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podforum_user_pracenje`
--

LOCK TABLES `podforum_user_pracenje` WRITE;
/*!40000 ALTER TABLE `podforum_user_pracenje` DISABLE KEYS */;
/*!40000 ALTER TABLE `podforum_user_pracenje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tema`
--

DROP TABLE IF EXISTS `tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_podforum` int(11) DEFAULT NULL,
  `naslov` varchar(45) DEFAULT NULL,
  `tip` enum('tekst','link','slika') DEFAULT NULL,
  `temacol` varchar(45) DEFAULT NULL,
  `autor` int(11) DEFAULT NULL,
  `tekst` mediumtext,
  `slika` blob,
  `link` varchar(255) DEFAULT NULL,
  `datum_kreiranja` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_podforum_naslov` (`id_podforum`,`naslov`),
  KEY `fk_tema_podforum_idx` (`id_podforum`),
  KEY `fk_tema_user_idx` (`autor`),
  CONSTRAINT `fk_tema_podforum` FOREIGN KEY (`id_podforum`) REFERENCES `podforum` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tema_user` FOREIGN KEY (`autor`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tema`
--

LOCK TABLES `tema` WRITE;
/*!40000 ALTER TABLE `tema` DISABLE KEYS */;
/*!40000 ALTER TABLE `tema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(30) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `lozinka` varchar(45) DEFAULT NULL,
  `uloga` enum('user','moderator','admin') DEFAULT NULL,
  `telefon` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `datum_registracije` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_UNIQUE` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-22  7:27:33

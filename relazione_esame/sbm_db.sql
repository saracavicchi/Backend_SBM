CREATE DATABASE  IF NOT EXISTS `sbm_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sbm_db`;
-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: sbm_db
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `carte_organizzatore`
--

DROP TABLE IF EXISTS `carte_organizzatore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carte_organizzatore` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `numero` char(16) NOT NULL,
  `data_scadenza` date NOT NULL,
  `cvv` char(3) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `id_organizzatore` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_carte_idx` (`id_organizzatore`),
  CONSTRAINT `fk1_carte` FOREIGN KEY (`id_organizzatore`) REFERENCES `organizzatore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carte_organizzatore`
--

LOCK TABLES `carte_organizzatore` WRITE;
/*!40000 ALTER TABLE `carte_organizzatore` DISABLE KEYS */;
INSERT INTO `carte_organizzatore` VALUES (1,'1234567812345678','2025-06-30','123','Marco','Rossi',1),(2,'2345678923456789','2026-07-31','234','Elena','Bianchi',2),(3,'3456789034567890','2027-08-31','345','Alessandro','Ferrari',3),(4,'4567890145678901','2028-09-30','456','Chiara','Moretti',4),(5,'5678901256789012','2029-10-31','567','Matteo','Russo',5),(6,'6789012367890123','2030-11-30','678','Giulia','Esposito',6),(7,'7890123478901234','2031-12-31','789','Luca','Romano',7),(8,'8901234589012345','2032-01-31','890','Sara','Conti',8),(9,'9012345690123456','2033-02-28','901','Francesco','Ricci',9),(10,'1123456701234567','2034-03-31','012','Martina','Marini',10),(11,'2234567812345678','2035-04-30','123','Simone','De Luca',11),(12,'3345678923456789','2036-05-31','234','Federica','Barbieri',12),(13,'4456789034567890','2037-06-30','345','Andrea','Galli',13),(14,'5567890145678901','2038-07-31','456','Laura','Rizzo',14),(15,'6678901256789012','2039-08-31','567','Giovanni','Greco',15),(16,'7789012367890123','2040-09-30','678','Valentina','Palmieri',16),(17,'8890123478901234','2041-10-31','789','Davide','Costa',17),(18,'9901234589012345','2042-11-30','890','Chiara','Montanari',18),(19,'0012345690123456','2043-12-31','901','Riccardo','Ferri',19),(20,'1123456701234567','2044-01-31','012','Sofia','Grenali',20),(23,'0000000000000000','2024-07-31','123','Mario','Rossi',21);
/*!40000 ALTER TABLE `carte_organizzatore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_organizzatore`
--

DROP TABLE IF EXISTS `link_organizzatore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `link_organizzatore` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nome_social` varchar(20) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `id_organizzatore` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_link_organizzatore_idx` (`id_organizzatore`),
  CONSTRAINT `fk1_link_organizzatore` FOREIGN KEY (`id_organizzatore`) REFERENCES `organizzatore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_organizzatore`
--

LOCK TABLES `link_organizzatore` WRITE;
/*!40000 ALTER TABLE `link_organizzatore` DISABLE KEYS */;
INSERT INTO `link_organizzatore` VALUES (1,'Facebook','https://www.facebook.com/marco.rossi',1),(2,'Linkedin','https://www.linkedin.com/in/elena-bianchi',2),(3,'Instagram','https://www.instagram.com/alessandro.ferrari/',3),(4,'Facebook','https://www.facebook.com/chiara.moretti',4),(5,'Linkedin','https://www.linkedin.com/in/matteo-russo',5),(6,'Instagram','https://www.instagram.com/giulia.esposito/',6),(7,'Facebook','https://www.facebook.com/luca.romano',7),(8,'Linkedin','https://www.linkedin.com/in/sara-conti',8),(9,'Instagram','https://www.instagram.com/francesco.ricci/',9),(10,'Facebook','https://www.facebook.com/martina.marini',10),(11,'Linkedin','https://www.linkedin.com/in/simone-de-luca',11),(12,'Instagram','https://www.instagram.com/federica.barbieri/',12),(13,'Facebook','https://www.facebook.com/andrea.galli',13),(14,'Linkedin','https://www.linkedin.com/in/laura-rizzo',14),(15,'Instagram','https://www.instagram.com/giovanni.greco/',15),(16,'Facebook','https://www.facebook.com/valentina.palmieri',16),(17,'Linkedin','https://www.linkedin.com/in/davide-costa',17),(18,'Instagram','https://www.instagram.com/chiara.montanari/',18),(19,'Facebook','https://www.facebook.com/riccardo.ferri',19),(20,'Linkedin','https://www.linkedin.com/in/sofia-grenali',20),(21,'Instagram','https://www.instagram.com/mario.rossi',21),(22,'Sito','https://www.mr.com',21),(25,'Sito','https://www.riccardoferri.com',19);
/*!40000 ALTER TABLE `link_organizzatore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_organizzazione`
--

DROP TABLE IF EXISTS `link_organizzazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `link_organizzazione` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nome_social` varchar(20) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `id_organizzazione` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_link_organizzazione_idx` (`id_organizzazione`),
  CONSTRAINT `fk1_link_organizzazione` FOREIGN KEY (`id_organizzazione`) REFERENCES `organizzazione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_organizzazione`
--

LOCK TABLES `link_organizzazione` WRITE;
/*!40000 ALTER TABLE `link_organizzazione` DISABLE KEYS */;
INSERT INTO `link_organizzazione` VALUES (1,'Facebook','https://www.facebook.com/SplendidoEventi',1),(2,'Linkedin','https://www.linkedin.com/company/creativa-organizzazione-eventi',2),(3,'Instagram','https://www.instagram.com/magico_momento/',3),(4,'Facebook','https://www.facebook.com/EleganzaEventi',4),(5,'Linkedin','https://www.linkedin.com/company/gioia-in-festa',5),(6,'Instagram','https://www.instagram.com/fantasia_eventi/',6),(7,'Instagram','https://www.instagram.com/SplendidoEventi',1),(8,'Linkedin','https://www.linkedin.com/company/splendido-eventi-official',1),(9,'Instagram','https://www.instagram.com/creativaorganizzazione/',2),(10,'Facebook','https://www.facebook.com/magicomomentoitalia/',3),(11,'Linkedin','https://www.linkedin.com/company/magicomomentoitalia',3),(12,'Instagram','https://www.instagram.com/gioiainfestaeventi/',5),(13,'Facebook','https://www.facebook.com/fantasiaeventiarte/',6),(36,'Sito','http://www.sbm.com',7),(51,'Twitter','http://www.twitter.com/sbm',7),(52,'Instagram','http://www.instagram.com/sbm',7);
/*!40000 ALTER TABLE `link_organizzazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizzatore`
--

DROP TABLE IF EXISTS `organizzatore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizzatore` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `cod_fiscale` char(16) DEFAULT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `username` varchar(20) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `bio` text,
  `url_foto` varchar(2000) DEFAULT NULL,
  `p_iva` char(11) DEFAULT NULL,
  `stato` varchar(45) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `città` varchar(45) DEFAULT NULL,
  `cap` char(5) DEFAULT NULL,
  `via` varchar(45) DEFAULT NULL,
  `num_civico` varchar(10) DEFAULT NULL,
  `id_organizzazione` bigint unsigned DEFAULT NULL,
  `data_nascita` date NOT NULL,
  `iban` varchar(34) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Username_UNIQUE` (`username`),
  UNIQUE KEY `Mail_UNIQUE` (`mail`),
  UNIQUE KEY `Cod_fiscale_UNIQUE` (`cod_fiscale`),
  UNIQUE KEY `Telefono_UNIQUE` (`telefono`),
  UNIQUE KEY `P_iva_UNIQUE` (`p_iva`),
  UNIQUE KEY `iban_UNIQUE` (`iban`),
  KEY `fk1_organizzatore_idx` (`id_organizzazione`),
  CONSTRAINT `fk1_organizzatore` FOREIGN KEY (`id_organizzazione`) REFERENCES `organizzazione` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizzatore`
--

LOCK TABLES `organizzatore` WRITE;
/*!40000 ALTER TABLE `organizzatore` DISABLE KEYS */;
INSERT INTO `organizzatore` VALUES (1,'RSSMRA80A01H501E','Marco','Rossi','marco.rossi','marco.rossi@email.com','+39123456789','Appassionato di cucina e viaggi.','http://example.com/marco_rossi.jpg','12345678901','Italia','RM','Roma','00100','Via Roma','10',1,'1984-09-11','IT02KSNV5600000CKSLW2S'),(2,NULL,'Elena','Bianchi','elena.bianchi','elena.bianchi@email.com','+39234567890','Amante dell\'arte e della musica.','http://example.com/elena_bianchi.jpg','23456789012','Italia','MI','Milano','20100','Via Milano','20',7,'1978-04-18',NULL),(3,'FRRAAS82A15C351T','Alessandro','Ferrari','alessandro.ferrari','alessandro.ferrari@email.com','+39345678901','Ingegnere informatico con passione per la fotografia.','http://example.com/alessandro_ferrari.jpg',NULL,'Italia','TO','Torino','10100','Via Torino','30',NULL,'1978-04-18',NULL),(4,'MRTCHR88A42F495K','Chiara','Moretti','chiara.moretti','chiara.moretti@email.com','+39456789012',NULL,'http://example.com/chiara_moretti.jpg','45678901234','Italia','NA','Napoli','80100','Via Napoli','40',4,'1978-04-18','LTSIDIV0000000K'),(5,'RSMTTO91A02G607D','Matteo','Russo','matteo.russo','matteo.russo@email.com','+39567890123','Studente di economia con passione per il cinema.',NULL,'56789012345','Italia','FI','Firenze','50100','Via Firenze','50',NULL,'1978-04-18',NULL),(6,'GSPTLJ94A41H297P','Giulia','Esposito','giulia.esposito','giulia.esposito@email.com','+39678901234','Futura avvocatessa con interesse per la moda.','http://example.com/giulia_esposito.jpg','67890123456','Italia','BO','Bologna','40100','Via Bologna','60',6,'1978-04-18','ITHCUW746900DJC'),(7,'LRCRMO97A41I093F','Luca','Romano','luca.romano','luca.romano@email.com','+39789012345','Ingegnere meccanico appassionato di sport.','http://example.com/luca_romano.jpg','78901234567','Italia','VE','Venezia','30100','Via Venezia','70',1,'1978-04-18',NULL),(8,'CNTSRA00A41A394G','Sara','Conti','sara.conti','sara.conti@email.com','+39890123456',NULL,NULL,'89012345678','Italia','PA','Palermo','90100','Via Palermo','80',2,'1978-04-18',NULL),(9,'FRRCSC03A41F404R','Francesco','Ricci','francesco.ricci','francesco.ricci@email.com','+39901234567','Appassionato di tecnologia e viaggiatore.','http://example.com/francesco_ricci.jpg',NULL,'Italia','BR','Brindisi','70100','Via Brindisi','90',NULL,'1978-04-18',NULL),(10,'MRTMRT06A41D506Q','Martina','Marini','martina.marini','martina.marini@email.com','+39912345678','Amante dei libri e del teatro.','http://example.com/martina_marini.jpg','01234567890','Italia','BG','Bergamo','24100','Via Bergamo','100',7,'1997-02-26','IT322439AI00AA'),(11,'SMNDLC09A41E615S','Simone','De Luca','simone.deluca','simone.deluca@email.com',NULL,'Aspirante musicista e appassionato di fotografia.','http://example.com/simone_deluca.jpg','12345678111','Italia','RC','Reggio Calabria','89100','Via Reggio','110',5,'1997-02-26',NULL),(12,NULL,'Federica','Barbieri','federica.barbieri','federica.barbieri@email.com','+39123556789','Ingegnere ambientale con passione per l\'arrampicata.','http://example.com/federica_barbieri.jpg','23466789012','Italia','CT','Catania','95100','Via Catania','120',6,'1997-02-26',NULL),(13,'NDRGLL15A41G837D','Andrea','Galli','andrea.galli','andrea.galli@email.com','+39233367890','Appassionato di storia e arte contemporanea.','http://example.com/andrea_galli.jpg',NULL,'Italia','LI','Livorno','57100','Via Livorno','130',1,'1997-02-26','JFJOWIEJ000WDJNF'),(14,'LRRLZZ18A41H948V','Laura','Rizzo','laura.rizzo','laura.rizzo@email.com','+39347678901','Aspirante psicologa e appassionata di yoga.','http://example.com/laura_rizzo.jpg','45678901244','Italia','PN','Pordenone','33100','Via Pordenone','140',NULL,'1997-02-26','JUWBFUBCW789000'),(15,'GVNNGR21A41I059C','Giovanni','Greco','giovanni.greco','giovanni.greco@email.com','+39466789012','Ingegnere civile con passione per la pittura.','http://example.com/giovanni_greco.jpg','56699012345','Italia','SA','Salerno','84100','Via Salerno','150',3,'1981-06-18',NULL),(16,'VLTPLM24A41F015W','Valentina','Palmieri','valentina.palmieri','valentina.palmieri@email.com','+39577890123',NULL,'http://example.com/valentina_palmieri.jpg',NULL,'Italia','CS','Cosenza','87100','Via Cosenza','160',4,'1981-06-18','ES230498324PPINDV00'),(17,'DVDCST27A41F103A','Davide','Costa','davide.costa','davide.costa@email.com','+39679901234','Aspirante architetto e amante del design.','back3.jpg','79901234567','Italia','SV','Savona','17100','Via Savona','170',7,'1981-06-18',NULL),(18,NULL,'Chiara','Montanari','chiara.montanari','chiara.montanari@email.com','+39789112345','Appassionata di danza e arte moderna.','back5.jpg','89012355678','Italia','BO','Bologna','40100','Via Bologna','65',7,'1981-06-18',NULL),(19,'FRRRCR20A41F105E','Riccardo','Ferri','riccardo.ferri','riccardo.ferri@email.com','+39891123456','Aspirante imprenditore e appassionato di tecnologia.','back4.jpg',NULL,'Italia','MI','Milano','20100','Via Milano','75',7,'1981-06-18','ITCJAOCMS0000LPL'),(20,'SGNSFA25A41I125P','Sofia','Grenali','sofia.grenali','sofia.grenali@email.com',NULL,'Amante della natura e degli animali.','6ad2b502-814a-492b-b56c-33cef7b6b5c4_back.jpg','01334567890','Italia','RM','Roma','00100','Via Roma','85',7,'2001-01-23',NULL),(21,'RSSMRA01A23D548F','Mario','Rossi','mrossi','mario.rossi@email.com','+393330003333','Membro di SBM','07fc5b9a-4d68-4119-9475-2431297fb47d_back2.jpg',NULL,'Italia','FE','Ferrara',NULL,NULL,NULL,7,'2001-01-23','IT0000AAAAAAA00AA'),(23,NULL,'Fabrizio','Miccoli','Romario del Salento','fabrizio.miccoli@email.com',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,'2001-01-23',NULL);
/*!40000 ALTER TABLE `organizzatore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizzazione`
--

DROP TABLE IF EXISTS `organizzazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizzazione` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `descrizione` text,
  `mail` varchar(254) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `url_foto` varchar(2000) DEFAULT NULL,
  `stato` varchar(45) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `città` varchar(45) DEFAULT NULL,
  `cap` char(5) DEFAULT NULL,
  `via` varchar(45) DEFAULT NULL,
  `num_civico` varchar(10) DEFAULT NULL,
  `iban` varchar(34) DEFAULT NULL,
  `id_admin` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Mail_UNIQUE` (`mail`),
  UNIQUE KEY `id_admin_UNIQUE` (`id_admin`),
  UNIQUE KEY `Telefono_UNIQUE` (`telefono`),
  UNIQUE KEY `iban_UNIQUE` (`iban`),
  CONSTRAINT `fk1_organizzazione` FOREIGN KEY (`id_admin`) REFERENCES `organizzatore` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizzazione`
--

LOCK TABLES `organizzazione` WRITE;
/*!40000 ALTER TABLE `organizzazione` DISABLE KEYS */;
INSERT INTO `organizzazione` VALUES (1,'Splendido Eventi','Specializzata in matrimoni e eventi esclusivi.','info@splendidoeventi.com','+390987654321','http://example.com/splendido_eventi.jpg','Italia','RM','Roma','00100','Via Roma','5','ITHCNQMC65000LP',7),(2,'Creativa organizzazione Eventi','Agenzia creativa per eventi aziendali e privati.','info@creativaorganizzazioneeventi.com','+391234567890','http://example.com/creativa_organizzazione_eventi.jpg','Italia','MI','Milano','20100','Via Milano','15',NULL,8),(3,'Magico Momento','Pianificazione di feste a tema e eventi speciali.','info@magicomomento.com','+392345678901',NULL,'Italia','TO','Torino','10100','Via Torino','25','LTHALMWU3738928JJJ',15),(4,'Eleganza Eventi','organizzazione di eventi di lusso e gala esclusivi.','info@eleganzaeventi.com','+393456789012','http://example.com/eleganza_eventi.jpg','Italia','NA','Napoli','80100','Via Napoli','35',NULL,4),(5,'Gioia in Festa','Servizi completi per feste di compleanno e eventi familiari.','info@gioiainfesta.com','+394567890123','http://example.com/gioia_in_festa.jpg','Italia','NA','Napoli','80100','Via dei Tribunali','45','ITGABCY6700000KTY',11),(6,'Fantasia Eventi','Specializzata in eventi tematici e spettacoli d\'arte.','info@fantasiaeventi.com','+395678901234','http://example.com/fantasia_eventi.jpg','Italia','BO','Bologna','40100','Via degli Artisti','55',NULL,6),(7,'SBM','Team di Ingegneria del Software per lo sviluppo delle funzionalità profilo/area personale dell\'utente organizzatore di EventGo','info@sbm.com',NULL,'80cbfa50-7cbb-47be-9a93-c36d07f1d8e1_sfondo.jpeg','Italia','FE','Ferrara','44121','Corso Martiri della Libertà','1','IT000000000KAAAA',19);
/*!40000 ALTER TABLE `organizzazione` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-30 19:18:06

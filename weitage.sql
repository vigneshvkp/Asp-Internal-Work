-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.22-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for thi_prod_bug
DROP DATABASE IF EXISTS `thi_prod_bug`;
CREATE DATABASE IF NOT EXISTS `thi_prod_bug` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `thi_prod_bug`;

-- Dumping structure for table thi_prod_bug.weitage
DROP TABLE IF EXISTS `weitage`;
CREATE TABLE IF NOT EXISTS `weitage` (
  `id` int(10) unsigned NOT NULL,
  `line_item` varchar(100) DEFAULT NULL,
  `percentage` integer DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table thi_prod_bug.weitage: ~20 rows (approximately)
/*!40000 ALTER TABLE `weitage` DISABLE KEYS */;
INSERT INTO `weitage` (`id`,`line_item`, `percentage`) VALUES
	(1,'Development Environment', 35),
	(2,'QA environment', 25),
	(3,'UAT Environment', 10),
	(4,'Code Synchronization with target repository', 30),
	(5,'Technical and Non functional Requirements', 35),
	(6,'Requirement Analysis', 30),
	(7,'Impact Analysis', 35),
	(8,'Technical Design (HLD or LLD)', 50),
	(9,'Database Design', 50),
	(10,'Server Code Quality\r\n', 20),
	(11,'Client Code Quality\r\n', 20),
	(12,'DB Scripts Quality\r\n', 20),
	(13,'Automated Code Analysis (Server & Client)\r\n', 20),
	(14,'Design Adherence\r\n', 20),
	(15,'Automated Unit test classes\r\n', 40),
	(16,'Functional Unit test case for the implemented feat\r\n', 60),
	(17,'Build procedure\r\n', 25),
	(18,'Deployment procedure for all environments\r\n', 25),
	(19,'Code Release & merge\r\n', 50),
	(20,'Recommended practices for project\r\n', 10);
/*!40000 ALTER TABLE `weitage` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

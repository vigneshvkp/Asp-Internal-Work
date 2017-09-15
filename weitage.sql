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
  `line_item` varchar(100) DEFAULT NULL,
  `percentage` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table thi_prod_bug.weitage: ~20 rows (approximately)
/*!40000 ALTER TABLE `weitage` DISABLE KEYS */;
INSERT INTO `weitage` (`line_item`, `percentage`) VALUES
	('Development Environment', 0.35),
	('QA environment', 0.25),
	('UAT Environment', 0.1),
	('Code Synchronization with target repository', 0.3),
	('Technical and Non functional Requirements', 0.35),
	('Requirement Analysis', 0.3),
	('Impact Analysis', 0.35),
	('Technical Design (HLD or LLD)', 0.5),
	('Database Design', 0.5),
	('Server Code Quality\r\n', 0.2),
	('Client Code Quality\r\n', 0.2),
	('DB Scripts Quality\r\n', 0.2),
	('Automated Code Analysis (Server & Client)\r\n', 0.2),
	('Design Adherence\r\n', 0.2),
	('Automated Unit test classes\r\n', 0.4),
	('Functional Unit test case for the implemented feat\r\n', 0.6),
	('Build procedure\r\n', 0.25),
	('Deployment procedure for all environments\r\n', 0.25),
	('Code Release & merge\r\n', 0.5),
	('Recommended practices for project\r\n', 1);
/*!40000 ALTER TABLE `weitage` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

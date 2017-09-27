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
  `assesment_line_item_id` int(3) unsigned NOT NULL,
  `percentage` integer DEFAULT NULL,
   KEY `FK_ass_line_item_id` (`assesment_line_item_id`),
   CONSTRAINT `FK_ass_line_item_id` FOREIGN KEY (`assesment_line_item_id`) REFERENCES `assesment_line_item` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table thi_prod_bug.weitage: ~20 rows (approximately)
/*!40000 ALTER TABLE `weitage` DISABLE KEYS */;
INSERT INTO `weitage` (`id`,`line_item`, `percentage`) VALUES
	(1,2, 35),
	(2,3, 25),
	(3,4, 10),
	(4,5, 30),
	(5,6, 35),
	(6,7, 30),
	(7,8, 35),
	(8,9, 50),
	(9,10, 50),
	(10,11, 20),
	(11,12, 20),
	(12,13, 20),
	(13,14, 40),
	(14,15, 60),
	(15,16, 50),
	(16,17, 25),
	(17,18, 25),
	(18,19, 100);
/*!40000 ALTER TABLE `weitage` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

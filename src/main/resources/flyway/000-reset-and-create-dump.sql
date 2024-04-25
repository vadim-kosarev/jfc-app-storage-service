-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               11.3.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table jfc.tbl_base
DROP TABLE IF EXISTS `tbl_base`;
CREATE TABLE IF NOT EXISTS `tbl_base` (
  `created_dt` bigint(20) NOT NULL,
  `base_id` uuid NOT NULL,
  `base_object_parent` uuid DEFAULT NULL,
  `base_label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`base_id`),
  KEY `FKjyrk83n58oyme5kf6sow24xtp` (`base_object_parent`),
  CONSTRAINT `FKjyrk83n58oyme5kf6sow24xtp` FOREIGN KEY (`base_object_parent`) REFERENCES `tbl_base` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_base: ~5 rows (approximately)
DELETE FROM `tbl_base`;
INSERT INTO `tbl_base` (`created_dt`, `base_id`, `base_object_parent`, `base_label`) VALUES
	(1714018738759, '09bbeb28-00d6-4e9c-a70a-096ec87955cb', NULL, NULL),
	(1714018738795, 'eeb58813-b567-4aee-9706-2dc10523c7cc', NULL, NULL),
	(1714018738757, '28c94d01-4021-4057-87c0-6d0cee06b85c', 'f652f9f7-1216-45f2-b968-9ac463537d41', '**Label: `IndexedDataEntity` / 28c94d01-4021-4057-87c0-6d0cee06b85c'),
	(1714018738743, 'f652f9f7-1216-45f2-b968-9ac463537d41', NULL, NULL),
	(1714018738761, '1c2db4e7-6e39-4f7a-b7dc-d71e82dadae0', NULL, NULL);

-- Dumping structure for table jfc.tbl_float_array_items
DROP TABLE IF EXISTS `tbl_float_array_items`;
CREATE TABLE IF NOT EXISTS `tbl_float_array_items` (
  `ind` int(11) NOT NULL,
  `val` float DEFAULT NULL,
  `objid` uuid NOT NULL,
  PRIMARY KEY (`ind`,`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_float_array_items: ~0 rows (approximately)
DELETE FROM `tbl_float_array_items`;
INSERT INTO `tbl_float_array_items` (`ind`, `val`, `objid`) VALUES
	(0, -0.0351957, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(0, 0.100742, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(1, 0.184127, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(1, 0.0192578, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(2, -0.0171573, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(2, -0.0857885, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(3, -0.0565918, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(3, -0.0714663, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(4, 0.00281471, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(4, 0.104188, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(5, 0.156564, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(5, 0.214387, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(6, -0.0220757, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(6, 0.158632, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(7, -0.00606727, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(7, 0.0894758, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(8, -0.0854241, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(8, -0.140082, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(9, 0.0865144, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(9, -0.0100232, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(10, 0.0314497, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(10, -0.0517938, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(11, -0.0420124, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(11, 0.0898888, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(12, 0.00355791, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(12, 0.0900064, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(13, -0.0809214, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(13, -0.164654, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(14, 0.0693224, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(14, 0.0596433, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(15, -0.0914079, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(15, -0.0652471, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(16, -0.001857, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(16, -0.0316186, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(17, 0.0533021, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(17, -0.0162979, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(18, 0.122111, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(18, -0.0531038, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(19, -0.00844095, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(19, 0.0843521, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(20, 0.0285479, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(20, 0.072059, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(21, -0.0047258, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(21, 0.0611922, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(22, 0.00451076, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(22, -0.055529, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(23, 0.100766, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(23, 0.0955048, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(24, 0.0318436, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(24, -0.0431629, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(25, 0.0567404, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(25, -0.00920609, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(26, 0.0401426, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(26, -0.117535, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(27, -0.127668, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(27, -0.204674, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(28, 0.174235, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(28, 0.0498131, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(29, 0.0191156, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(29, 0.078503, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(30, 0.0834329, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(30, -0.0241827, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(31, -0.0741767, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(31, 0.0353901, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(32, 0.0178609, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(32, -0.144992, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(33, -0.00745891, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(33, 0.0575996, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(34, 0.0924756, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(34, 0.0362298, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(35, 0.0527046, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(35, -0.0337891, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(36, 0.0328461, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(36, -0.0331189, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(37, 0.0626171, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(37, -0.0109816, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(38, -0.0214792, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(38, -0.0509107, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(39, -0.0433145, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(39, -0.0150932, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(40, 0.0407818, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(40, 0.116642, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(41, -0.0825321, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(41, -0.0333846, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(42, -0.0418997, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(42, 0.00652006, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(43, 0.161736, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(43, 0.177223, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(44, -0.143772, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(44, -0.172545, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(45, 0.0630714, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(45, 0.0261727, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(46, 0.188095, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(46, 0.149902, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(47, 0.0300884, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(47, 0.00922665, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(48, -0.188357, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(48, -0.152537, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(49, -0.0831731, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(49, 0.0301896, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(50, -0.0108396, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(50, 0.0763081, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(51, 0.112699, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(51, -0.127192, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(52, 0.00118741, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(52, -0.0710508, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(53, -0.0587578, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(53, -0.0287152, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(54, 0.0642944, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(54, 0.0961159, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(55, 0.107554, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(55, 0.0118216, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(56, -0.0156128, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(56, -0.121194, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(57, 0.104958, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(57, 0.194394, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(58, -0.00182736, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(58, 0.0150527, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(59, -0.156558, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(59, -0.0835714, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(60, -0.118508, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(60, -0.0589634, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(61, -0.0488263, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(61, 0.0113297, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(62, 0.201202, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(62, 0.149937, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(63, -0.11683, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(63, -0.122678, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(64, 0.134754, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(64, 0.0783049, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(65, -0.0314932, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(65, -0.132471, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(66, 0.0517325, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(66, 0.0295872, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(67, -0.0995119, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(67, -0.0471971, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(68, -0.069861, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(68, -0.076469, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(69, 0.166905, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(69, -0.0741037, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(70, -0.0497107, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(70, 0.0290148, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(71, 0.0168315, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(71, 0.109284, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(72, -0.0998968, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(72, -0.0112293, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(73, -0.0120251, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(73, -0.00628016, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(74, 0.0783147, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(74, -0.0468321, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(75, -0.0928482, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(75, 0.067259, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(76, -0.0930421, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(76, -0.0865019, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(77, 0.188288, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(77, 0.0392392, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(78, 0.069289, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(78, 0.0824722, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(79, -0.0170686, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(79, 0.110808, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(80, -0.139191, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(80, 0.0211863, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(81, 0.112905, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(81, 0.0107018, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(82, 0.0143279, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(82, 0.0222813, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(83, -0.0104724, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(83, -0.00302041, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(84, 0.0525262, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(84, 0.00358204, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(85, -0.12472, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(85, -0.0450968, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(86, 0.0263323, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(86, 0.143018, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(87, 0.0214321, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(87, -0.0202433, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(88, 0.0664697, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(88, -0.0136242, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(89, 0.0691075, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(89, 0.0250767, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(90, 0.0840283, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(90, 0.0403461, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(91, -0.0168227, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(91, -0.101594, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(92, -0.0180773, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(92, -0.134966, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(93, -0.0663321, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(93, -0.0354414, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(94, 0.0621209, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(94, -0.108461, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(95, -0.144066, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(95, -0.0367651, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(96, 0.0914713, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(96, 0.119991, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(97, 0.146696, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(97, 0.003158, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(98, 0.0165044, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(98, -0.0471738, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(99, -0.0805983, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(99, -0.0124961, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(100, -0.0396304, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(100, -0.0282462, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(101, 0.126451, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(101, 0.0804124, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(102, 0.01985, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(102, 0.00610483, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(103, -0.00537282, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(103, 0.0951398, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(104, -0.144042, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(104, -0.278445, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(105, 0.0869507, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(105, 0.0140505, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(106, 0.0222554, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(106, -0.131347, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(107, -0.0240533, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(107, 0.144942, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(108, 0.1155, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(108, -0.072826, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(109, -0.051865, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(109, 0.0212171, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(110, 0.0809823, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(110, 0.152666, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(111, 0.0594542, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(111, 0.0519836, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(112, -0.0859868, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(112, -0.146758, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(113, -0.064154, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(113, -0.0700266, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(114, 0.0954349, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(114, -0.00979241, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(115, -0.0288483, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(115, 0.0394895, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(116, -0.0298238, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(116, -0.107946, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(117, -0.279429, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(117, -0.101043, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(118, 0.0840208, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(118, -0.0780467, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(119, 0.0901257, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(119, 0.0981125, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(120, 0.111292, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(120, 0.00827686, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(121, -0.0891901, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(121, -0.0363506, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(122, -0.0274827, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(122, 0.0396925, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(123, 0.0111185, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(123, 0.0665312, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(124, -0.0337703, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(124, -0.0112813, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(125, 0.0757259, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(125, -0.00985273, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(126, 0.117403, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(126, -0.0583023, 'eeb58813-b567-4aee-9706-2dc10523c7cc'),
	(127, 0.0334028, '09bbeb28-00d6-4e9c-a70a-096ec87955cb'),
	(127, -0.0892106, 'eeb58813-b567-4aee-9706-2dc10523c7cc');

-- Dumping structure for table jfc.tbl_headers
DROP TABLE IF EXISTS `tbl_headers`;
CREATE TABLE IF NOT EXISTS `tbl_headers` (
  `columnid` int(11) DEFAULT NULL,
  `frame_no` int(11) DEFAULT NULL,
  `broker_timestamp` bigint(20) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `base_id` uuid NOT NULL,
  `hostname` varchar(255) DEFAULT NULL,
  `marker_headers_object` varchar(255) DEFAULT NULL,
  `message_type` varchar(255) DEFAULT NULL,
  `s3path` text DEFAULT NULL,
  `source` text DEFAULT NULL,
  PRIMARY KEY (`base_id`),
  CONSTRAINT `FK41wuwwu16vhfewovw0ya6c6hm` FOREIGN KEY (`base_id`) REFERENCES `tbl_base` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_headers: ~0 rows (approximately)
DELETE FROM `tbl_headers`;
INSERT INTO `tbl_headers` (`columnid`, `frame_no`, `broker_timestamp`, `timestamp`, `base_id`, `hostname`, `marker_headers_object`, `message_type`, `s3path`, `source`) VALUES
	(NULL, NULL, 1714018738759, 1714018738759, '09bbeb28-00d6-4e9c-a70a-096ec87955cb', NULL, NULL, NULL, NULL, NULL),
	(NULL, NULL, 1714018738795, 1714018738795, 'eeb58813-b567-4aee-9706-2dc10523c7cc', NULL, NULL, NULL, NULL, NULL),
	(NULL, NULL, 1714018738757, 1714018738757, '28c94d01-4021-4057-87c0-6d0cee06b85c', NULL, NULL, NULL, NULL, NULL),
	(797568, 0, 1714018738531, 1714018704081, 'f652f9f7-1216-45f2-b968-9ac463537d41', 'HARON', NULL, 'processed-frame', 'HARON/out/orban_putin.jpg/_f652f9f7-1216-45f2-b968-9ac463537d41_frame_1714018704081_0_797568.jpg', 'out/orban_putin.jpg'),
	(797568, 0, 1714018738531, 1714018704081, '1c2db4e7-6e39-4f7a-b7dc-d71e82dadae0', 'HARON', NULL, 'processed-frame-face', 'HARON/out/orban_putin.jpg/f652f9f7-1216-45f2-b968-9ac463537d41/1c2db4e7-6e39-4f7a-b7dc-d71e82dadae0_frame_1714018704081_0_797568.jpg', 'out/orban_putin.jpg');

-- Dumping structure for table jfc.tbl_indexed_data
DROP TABLE IF EXISTS `tbl_indexed_data`;
CREATE TABLE IF NOT EXISTS `tbl_indexed_data` (
  `marker_image_indexed` bit(1) DEFAULT NULL,
  `base_id` uuid NOT NULL,
  `base_object_parent1` uuid DEFAULT NULL,
  PRIMARY KEY (`base_id`),
  UNIQUE KEY `UK_ioi0jcsjsjlw5bq72fmq2di6x` (`base_object_parent1`),
  CONSTRAINT `FKai6a310tjqahsvswpu7cx9bhe` FOREIGN KEY (`base_object_parent1`) REFERENCES `tbl_processed_images` (`base_id`),
  CONSTRAINT `FKc6fu1k1x2gcgk8o5uju4ou1fc` FOREIGN KEY (`base_id`) REFERENCES `tbl_headers` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_indexed_data: ~0 rows (approximately)
DELETE FROM `tbl_indexed_data`;
INSERT INTO `tbl_indexed_data` (`marker_image_indexed`, `base_id`, `base_object_parent1`) VALUES
	(NULL, '28c94d01-4021-4057-87c0-6d0cee06b85c', NULL);

-- Dumping structure for table jfc.tbl_indexed_data_items
DROP TABLE IF EXISTS `tbl_indexed_data_items`;
CREATE TABLE IF NOT EXISTS `tbl_indexed_data_items` (
  `detection` float DEFAULT NULL,
  `face_index` int(11) DEFAULT NULL,
  `img_box_p1_x` int(11) DEFAULT NULL,
  `img_box_p1_y` int(11) DEFAULT NULL,
  `img_box_p2_x` int(11) DEFAULT NULL,
  `img_box_p2_y` int(11) DEFAULT NULL,
  `base_id` uuid NOT NULL,
  `col_parent_image` uuid DEFAULT NULL,
  PRIMARY KEY (`base_id`),
  KEY `FKmkayrwkhqmvy8h1237p5qdweo` (`col_parent_image`),
  CONSTRAINT `FKdo688tt7rmfye7y9x6kw039wm` FOREIGN KEY (`base_id`) REFERENCES `tbl_headers` (`base_id`),
  CONSTRAINT `FKmkayrwkhqmvy8h1237p5qdweo` FOREIGN KEY (`col_parent_image`) REFERENCES `tbl_indexed_data` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_indexed_data_items: ~0 rows (approximately)
DELETE FROM `tbl_indexed_data_items`;
INSERT INTO `tbl_indexed_data_items` (`detection`, `face_index`, `img_box_p1_x`, `img_box_p1_y`, `img_box_p2_x`, `img_box_p2_y`, `base_id`, `col_parent_image`) VALUES
	(0.99986, 0, 139, 17, 233, 136, '09bbeb28-00d6-4e9c-a70a-096ec87955cb', '28c94d01-4021-4057-87c0-6d0cee06b85c'),
	(0.985217, 1, 507, 40, 597, 167, 'eeb58813-b567-4aee-9706-2dc10523c7cc', '28c94d01-4021-4057-87c0-6d0cee06b85c');

-- Dumping structure for table jfc.tbl_processed_images
DROP TABLE IF EXISTS `tbl_processed_images`;
CREATE TABLE IF NOT EXISTS `tbl_processed_images` (
  `base_id` uuid NOT NULL,
  PRIMARY KEY (`base_id`),
  CONSTRAINT `FKfmynx03rvfpod9hsthokgtffw` FOREIGN KEY (`base_id`) REFERENCES `tbl_headers` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_processed_images: ~0 rows (approximately)
DELETE FROM `tbl_processed_images`;
INSERT INTO `tbl_processed_images` (`base_id`) VALUES
	('f652f9f7-1216-45f2-b968-9ac463537d41');

-- Dumping structure for table jfc.tbl_processed_image_data
DROP TABLE IF EXISTS `tbl_processed_image_data`;
CREATE TABLE IF NOT EXISTS `tbl_processed_image_data` (
  `base_id` uuid NOT NULL,
  `imgdata_image` uuid DEFAULT NULL,
  `marker_processed_image_data_object` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`base_id`),
  UNIQUE KEY `UK_45rwl01yb2tn8leycdh5i228c` (`imgdata_image`),
  CONSTRAINT `FKgr8yadl9ijgdvts0j4iod7m7j` FOREIGN KEY (`base_id`) REFERENCES `tbl_headers` (`base_id`),
  CONSTRAINT `FKsfryscstbw622e9dixxqk89sq` FOREIGN KEY (`imgdata_image`) REFERENCES `tbl_processed_images` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.tbl_processed_image_data: ~0 rows (approximately)
DELETE FROM `tbl_processed_image_data`;
INSERT INTO `tbl_processed_image_data` (`base_id`, `imgdata_image`, `marker_processed_image_data_object`) VALUES
	('1c2db4e7-6e39-4f7a-b7dc-d71e82dadae0', NULL, NULL);

-- Dumping structure for table jfc.__tst_address
DROP TABLE IF EXISTS `__tst_address`;
CREATE TABLE IF NOT EXISTS `__tst_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5sae5ooaxg3n5lwkmmb00ijpm` (`order_id`),
  CONSTRAINT `FKq1evr15atteyu8haaah837oem` FOREIGN KEY (`order_id`) REFERENCES `__tst_orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.__tst_address: ~0 rows (approximately)
DELETE FROM `__tst_address`;

-- Dumping structure for table jfc.__tst_orders
DROP TABLE IF EXISTS `__tst_orders`;
CREATE TABLE IF NOT EXISTS `__tst_orders` (
  `total_price` decimal(38,2) DEFAULT NULL,
  `total_quantity` int(11) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_updated` datetime(6) DEFAULT NULL,
  `order_tracking_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.__tst_orders: ~0 rows (approximately)
DELETE FROM `__tst_orders`;

-- Dumping structure for table jfc.__tst_tbl_children
DROP TABLE IF EXISTS `__tst_tbl_children`;
CREATE TABLE IF NOT EXISTS `__tst_tbl_children` (
  `child_container` uuid DEFAULT NULL,
  `child_id` uuid NOT NULL,
  `child_str_label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`child_id`),
  KEY `FK2ajb67lmdwnl7o1405oyn7o1m` (`child_container`),
  CONSTRAINT `FK2ajb67lmdwnl7o1405oyn7o1m` FOREIGN KEY (`child_container`) REFERENCES `__tst_tbl_container` (`col_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.__tst_tbl_children: ~0 rows (approximately)
DELETE FROM `__tst_tbl_children`;

-- Dumping structure for table jfc.__tst_tbl_container
DROP TABLE IF EXISTS `__tst_tbl_container`;
CREATE TABLE IF NOT EXISTS `__tst_tbl_container` (
  `col_id` uuid NOT NULL,
  `col_label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`col_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table jfc.__tst_tbl_container: ~0 rows (approximately)
DELETE FROM `__tst_tbl_container`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

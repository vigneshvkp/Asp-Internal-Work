
DROP TABLE IF EXISTS `assesment_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assesment_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) CHARACTER SET latin1 NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 NOT NULL,
  `assesment_type_id` int(10) unsigned NOT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `last_modified_by` varchar(10) DEFAULT NULL,
  `last_modified_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ass_group_ass_type` (`assesment_type_id`),
  CONSTRAINT `FK_ass_group_ass_type` FOREIGN KEY (`assesment_type_id`) REFERENCES `assesment_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='Assesment Groups';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `assesment_group_checklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assesment_group_checklist` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_checklist` varchar(1000) NOT NULL,
  `assesment_group_id` int(10) unsigned NOT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `last_modified_by` varchar(10) DEFAULT NULL,
  `last_modified_on` datetime DEFAULT NULL,
  `heading` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ass_grp_chklist_ass_group` (`assesment_group_id`) USING BTREE,
  CONSTRAINT `FK_ass_grp_chklist_ass_group` FOREIGN KEY (`assesment_group_id`) REFERENCES `assesment_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8 COMMENT='CheckList Item for Assesment Groups';
/*!40101 SET character_set_client = @saved_cs_client */;




DROP TABLE IF EXISTS `assesment_line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assesment_line_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `line_item_text` varchar(50) CHARACTER SET latin1 NOT NULL,
  `line_item_description` varchar(350) DEFAULT NULL,
  `assesment_group_id` int(10) unsigned NOT NULL,
  `assesment_type_id` int(10) unsigned DEFAULT NULL,
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ass_line_item_ass_group` (`assesment_group_id`) USING BTREE,
  KEY `FK_ass_line_item_ass_type` (`assesment_type_id`) USING BTREE,
  CONSTRAINT `FK_ass_line_item_ass_group` FOREIGN KEY (`assesment_group_id`) REFERENCES `assesment_group` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ass_line_item_ass_type` FOREIGN KEY (`assesment_type_id`) REFERENCES `assesment_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='Assessment Line item';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `assesment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assesment_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `last_modified_by` varchar(10) DEFAULT NULL,
  `last_modified_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Various Asssment Types';
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ace_no` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `dept_id` varchar(10) NOT NULL,
  `dept_name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='Auditor Log';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(10) unsigned NOT NULL,
  `customer_name` varchar(100) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pros_dept_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `dept_head_ace_no` varchar(10) NOT NULL,
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pros_project_id` int(10) unsigned NOT NULL,
  `prj_name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `start_date` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  `audit_freq` tinyint(2) unsigned NOT NULL DEFAULT '2',
  `customer_id` int(10) unsigned NOT NULL,
  `dept_id` int(10) unsigned NOT NULL,
  `owner_user_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_pros_project` (`pros_project_id`),
  KEY `FK_project_department` (`dept_id`),
  KEY `FK_project_customer` (`customer_id`),
  CONSTRAINT `FK_project_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_project_department` FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;




DROP TABLE IF EXISTS `project_auditee_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_auditee_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_id` int(11) DEFAULT NULL,
  `score_id` int(11) DEFAULT NULL,
  `auditee_name` varchar(100) DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `lastmodified_by` varchar(10) DEFAULT NULL,
  `lastmodified_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3091 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `project_auditor_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_auditor_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `auditor_id` int(11) NOT NULL,
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=760 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `project_auditor_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_auditor_mapping` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(10) unsigned NOT NULL,
  `auditor_id` int(10) unsigned NOT NULL,
  `audit_date` datetime NOT NULL,
  `audit_complete` tinyint(1) NOT NULL DEFAULT '0',
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_prj_adtr_map_auditors` (`auditor_id`),
  KEY `FK_prj_adtr_map_projects` (`project_id`),
  CONSTRAINT `FK_prj_adtr_map_auditors` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_prj_adtr_map_projects` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=739 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `thi_group_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thi_group_score` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thi_score_id` int(10) unsigned NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  `assessment_group_id` int(10) unsigned NOT NULL,
  `score` decimal(10,0) NOT NULL,
  `created_by` varchar(10) NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_thi_group_score_thi_score` (`thi_score_id`),
  KEY `FK_thi_group_score_project` (`project_id`),
  KEY `FK_thi_group_score_assesment_group` (`assessment_group_id`),
  CONSTRAINT `FK_thi_group_score_assesment_group` FOREIGN KEY (`assessment_group_id`) REFERENCES `assesment_group` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_group_score_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_group_score_thi_score` FOREIGN KEY (`thi_score_id`) REFERENCES `thi_score` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5189 DEFAULT CHARSET=utf8 COMMENT='First level child table of Thi Score';
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `thi_line_item_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thi_line_item_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thi_group_score_id` int(10) unsigned NOT NULL,
  `thi_score_id` int(10) unsigned NOT NULL,
  `assesment_line_item_id` int(10) unsigned NOT NULL,
  `comments` varchar(1000) CHARACTER SET latin1 DEFAULT NULL,
  `created_by` varchar(10) CHARACTER SET latin1 NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) CHARACTER SET latin1 NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_thi_line_item_log_thi_group_score` (`thi_group_score_id`),
  KEY `FK_thi_line_item_log_thi_score` (`thi_score_id`),
  KEY `FK_thi_line_item_log_assesment_line_item` (`assesment_line_item_id`),
  CONSTRAINT `FK_thi_line_item_log_assesment_line_item` FOREIGN KEY (`assesment_line_item_id`) REFERENCES `assesment_line_item` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_line_item_log_thi_group_score` FOREIGN KEY (`thi_group_score_id`) REFERENCES `thi_group_score` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_line_item_log_thi_score` FOREIGN KEY (`thi_score_id`) REFERENCES `thi_score` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13425 DEFAULT CHARSET=utf8 COMMENT='THI Score''s second level detail table';
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `thi_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thi_score` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(10) unsigned NOT NULL,
  `auditor_id` int(10) unsigned NOT NULL,
  `audit_date` datetime NOT NULL,
  `audit_cycle_date` datetime NOT NULL,
  `overall_score` decimal(10,2) NOT NULL,
  `comments` varchar(1000) CHARACTER SET latin1 DEFAULT NULL,
  `recommendations` varchar(1000) CHARACTER SET latin1 DEFAULT NULL,
  `project_owner_id` varchar(10) CHARACTER SET latin1 NOT NULL,
  `assesment_type_id` int(10) unsigned NOT NULL,
  `created_by` varchar(10) CHARACTER SET latin1 NOT NULL,
  `created_on` datetime NOT NULL,
  `last_modified_by` varchar(10) CHARACTER SET latin1 NOT NULL,
  `last_modified_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_thi_score_project` (`project_id`),
  KEY `FK_thi_score_auditor` (`auditor_id`),
  KEY `FK_thi_score` (`assesment_type_id`),
  CONSTRAINT `FK_thi_score` FOREIGN KEY (`assesment_type_id`) REFERENCES `assesment_type` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_score_auditor` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_score_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=741 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='Thi Scoring Matser Table';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `thi_line_item_score`;

create table thi_line_item_score(
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thi_score_id` int(10) unsigned NOT NULL,
  `assesment_line_item_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_thi_line_item_log_thi_score` (`thi_score_id`),
  KEY `FK_thi_line_item_score_assesment_line_item` (`assesment_line_item_id`),
  CONSTRAINT `FK_thi_line_item_log_thi_score` FOREIGN KEY (`thi_score_id`) REFERENCES `thi_score` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_thi_line_item_score_assesment_line_item` FOREIGN KEY (`assesment_line_item_id`) REFERENCES `assesment_line_item` (`id`) ON UPDATE CASCADE,
)

CREATE TABLE `thi_line_item_score` (
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`thi_score_id` int(10) unsigned NOT NULL,
	`assesment_line_item_id` int(10) unsigned NOT NULL,
	`score` int(10) unsigned NOT NULL,
	PRIMARY KEY (`id`),
	KEY `FK_thi_line_item_score_thi_score` (`thi_score_id`),
	KEY `FK_thi_line_item_score_assesment_line_item` (`assesment_line_item_id`), 
	CONSTRAINT `FK_thi_line_item_score_thi_score` FOREIGN KEY (`thi_score_id`) REFERENCES `thi_score` (`id`) ON UPDATE CASCADE,
	CONSTRAINT `FK_thi_line_item_score_assesment_line_item` FOREIGN KEY (`assesment_line_item_id`) REFERENCES `assesment_line_item` (`id`) ON UPDATE CASCADE
);





CREATE TABLE `short_url` (
  `short_url_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `short_url_path` char(8) NOT NULL,
  `long_url` varchar(200) NOT NULL,
  PRIMARY KEY (`short_url_id`),
  UNIQUE KEY `short_url_path_UNIQUE` (`short_url_path`),
  KEY `long_url_IDX` (`long_url`)
);

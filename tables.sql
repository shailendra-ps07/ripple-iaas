CREATE TABLE `users` (
  `email` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL,
  `mobile_no` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `is_master` smallint(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`email`),
  UNIQUE KEY `mobile_no` (`mobile_no`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `virtual_machines` (
  `name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `operating_system` varchar(255) DEFAULT NULL,
  `ram` varchar(255) DEFAULT NULL,
  `cpu_cores` varchar(255) DEFAULT NULL,
  `hard_disk` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`name`),
  KEY `vm_FK_1` (`operating_system`),
  KEY `vm_FK_2` (`ram`),
  KEY `vm_FK_3` (`cpu_cores`),
  KEY `vm_FK_4` (`hard_disk`),
  KEY `vm_FK_5` (`user_id`),
  CONSTRAINT `vm_FK_1` FOREIGN KEY (`operating_system`) REFERENCES `operating_systems` (`name`),
  CONSTRAINT `vm_FK_2` FOREIGN KEY (`ram`) REFERENCES `rams` (`name`),
  CONSTRAINT `vm_FK_3` FOREIGN KEY (`cpu_cores`) REFERENCES `cpu_cores` (`name`),
  CONSTRAINT `vm_FK_4` FOREIGN KEY (`hard_disk`) REFERENCES `hard_disks` (`name`),
  CONSTRAINT `vm_FK_5` FOREIGN KEY (`user_id`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_auth` (
  `access_token_id` varchar(255) NOT NULL DEFAULT '',
  `user_id` varchar(255) NOT NULL DEFAULT '',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_access_utc` datetime NOT NULL,
  PRIMARY KEY (`access_token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rams` (
  `name` varchar(255) NOT NULL,
  `size` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `operating_systems` (
  `name` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `hard_disks` (
  `name` varchar(255) NOT NULL,
  `size` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cpu_cores` (
  `name` varchar(255) NOT NULL,
  `cores` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;iaas_2019-07-30.sql.gz
CREATE TABLE IF NOT EXISTS `fillin` (
  `quiz_id` int NOT NULL,
  `question_id` int NOT NULL,
  `email` varchar(60) NOT NULL,
  `answer` varchar(500) DEFAULT NULL,
  `fillin_date` date DEFAULT (curdate()),
  PRIMARY KEY (`quiz_id`,`question_id`,`email`)
);

CREATE TABLE IF NOT EXISTS `question` (
  `quiz_id` int NOT NULL,
  `question_id` int NOT NULL,
  `question` varchar(200) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `required` tinyint DEFAULT '0',
  `options` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`quiz_id`,`question_id`)
  );

  
  CREATE TABLE IF NOT EXISTS `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `published` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
  );


CREATE TABLE IF NOT EXISTS `user` (
  `email` varchar(100) NOT NULL,
  `password` varchar(60) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`)
  );

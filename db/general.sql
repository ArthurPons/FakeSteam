DROP TABLE IF EXISTS `historic`;
DROP TABLE IF EXISTS `user_owns_game`;
DROP TABLE IF EXISTS `rating`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `game_is_of_genre`;
DROP TABLE IF EXISTS `game_is_on_console`;

DROP TABLE IF EXISTS `game`;
DROP TABLE IF EXISTS `console`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `genre`;




CREATE TABLE `game` (
  `id_game` int(11) NOT NULL AUTO_INCREMENT,
  `title_game` varchar(45) NOT NULL,
  `price_game` float DEFAULT NULL,
  `picture_url_game` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id_game`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `console` (
  `id_console` int(11) NOT NULL AUTO_INCREMENT,
  `name_console` varchar(45) NOT NULL,
  `type_console` varchar(45) NOT NULL,
  PRIMARY KEY (`id_console`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username_user` varchar(45) NOT NULL,
  `pwd_user` varchar(250) NOT NULL,
  `salt_user` varbinary(2000) NOT NULL,
  `is_admin_user` boolean NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `id_genre` int(11) NOT NULL AUTO_INCREMENT,
  `name_genre` varchar(45) NOT NULL,
  PRIMARY KEY (`id_genre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game_is_of_genre` (
  `id_game_is_of_genre` int(11) NOT NULL,
  `fk_game_game_is_of_genre` int(11) NOT NULL,
  `fk_genre_game_is_of_genre` int(11) NOT NULL,
  PRIMARY KEY (`id_game_is_of_genre`),
  KEY `fk_game_game_is_of_genre_idx` (`fk_game_game_is_of_genre`),
  KEY `fk_genre_game_is_of_genre_idx` (`fk_genre_game_is_of_genre`),
  CONSTRAINT `fk_game_game_is_of_genre` FOREIGN KEY (`fk_game_game_is_of_genre`) REFERENCES `game` (`id_game`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_genre_game_is_of_genre` FOREIGN KEY (`fk_genre_game_is_of_genre`) REFERENCES `genre` (`id_genre`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game_is_on_console` (
  `fk_game` int(11) NOT NULL,
  `fk_console` int(11) NOT NULL,
  PRIMARY KEY (`fk_game`,`fk_console`),
  KEY `fk_console_idx` (`fk_console`),
  CONSTRAINT `fk_game` FOREIGN KEY (`fk_game`) REFERENCES `game` (`id_game`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_console` FOREIGN KEY (`fk_console`) REFERENCES `console` (`id_console`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historic` (
  `id_historic` int(11) NOT NULL AUTO_INCREMENT,
  `fk_game` int(11) NOT NULL,
  `fk_user` int(11) NOT NULL,
  `date_historic` date NOT NULL,
  PRIMARY KEY (`id_historic`),
  KEY `fk_game_idx` (`fk_game`),
  KEY `fk_user_idx` (`fk_user`),
  CONSTRAINT `fk_game_historic` FOREIGN KEY (`fk_game`) REFERENCES `game` (`id_game`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_historic` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_owns_game` (
  `id_user_owns_game` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user_own` int(11) NOT NULL,
  `fk_game_own` int(11) NOT NULL,
  PRIMARY KEY (`id_user_owns_game`),
  KEY `fk_user_own_idx` (`fk_user_own`),
  KEY `fk_game_own_idx` (`fk_game_own`),
  CONSTRAINT `fk_user_own` FOREIGN KEY (`fk_user_own`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_own` FOREIGN KEY (`fk_game_own`) REFERENCES `game` (`id_game`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `id_rating` int(11) NOT NULL AUTO_INCREMENT,
  `fk_game_rating` int(11) NOT NULL,
  `fk_user_rating` int(11) NOT NULL,
  `rating_rating` int(11) NOT NULL,
  PRIMARY KEY (`id_rating`),
  CONSTRAINT `fk_game_rating` FOREIGN KEY (`id_rating`) REFERENCES `game` (`id_game`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_rating` FOREIGN KEY (`id_rating`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;





/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id_comment` int(11) NOT NULL AUTO_INCREMENT,
  `fk_game_comment` int(11) NOT NULL,
  `fk_user_comment` int(11) NOT NULL,
  `message_comment` text,
  PRIMARY KEY (`id_comment`),
  KEY `fk_game_comment_idx` (`fk_game_comment`),
  KEY `fk_user_comment_idx` (`fk_user_comment`),
  CONSTRAINT `fk_game_comment` FOREIGN KEY (`fk_game_comment`) REFERENCES `game` (`id_game`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_comment` FOREIGN KEY (`fk_user_comment`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/* Insertions  */

/* USER (ajout doit etre manuel car non gestion des pwd en sql*/


/* GAME */
INSERT INTO game (picture_url_game, price_game, title_game) VALUES (".","15","The Binding of Isaac");
INSERT INTO game (picture_url_game, price_game, title_game) VALUES (".","26","Metal Slug");
INSERT INTO game (picture_url_game, price_game, title_game) VALUES (".","49.99","Planet Coaster");
INSERT INTO game (picture_url_game, price_game, title_game) VALUES (".","16","Medievil");
INSERT INTO game (picture_url_game, price_game, title_game) VALUES (".","25","God of War");


/* CONSOLE */
INSERT INTO console (name_console, type_console) VALUES ("PS3","Original");
INSERT INTO console (name_console, type_console) VALUES ("PS3","Slim");
INSERT INTO console (name_console, type_console) VALUES ("PS3","Super slim");

INSERT INTO console (name_console, type_console) VALUES ("Xbox 360","Slim");



/* GENRE */
INSERT INTO genre (name_genre) VALUES ("action");
INSERT INTO genre (name_genre) VALUES ("RPG");
INSERT INTO genre (name_genre) VALUES ("MOBA");
INSERT INTO genre (name_genre) VALUES ("Simulation");



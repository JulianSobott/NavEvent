-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Erstellungszeit: 02. Mai 2017 um 10:56
-- Server-Version: 5.6.26
-- PHP-Version: 7.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `navevent01`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `beacons`
--

CREATE TABLE `beacons` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `minor_id` int(11) NOT NULL,
  `pos_x` float NOT NULL,
  `pos_y` float NOT NULL,
  `description` varchar(1024) NOT NULL,
  `fk_map_id` int(11) NOT NULL,
  `fk_special` int(11) DEFAULT NULL,
  `fk_ordinary` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `beacons`
--

INSERT INTO `beacons` (`id`, `name`, `minor_id`, `pos_x`, `pos_y`, `description`, `fk_map_id`, `fk_special`, `fk_ordinary`) VALUES
(1, 'Entrancre/Exit', 1, 532, 446, 'Were you can go in and out.', 1, NULL, 1),
(2, 'Auditorium 1', 2, 395, 305, 'A very importatn lecture', 1, 1, NULL),
(3, 'Auditorium 2', 3, 400, 130, 'Also very important.', 1, 1, NULL),
(4, 'WC', 4, 395, 500, 'Your loo.', 1, NULL, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `maps`
--

CREATE TABLE `maps` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `major_id` int(11) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `img_file` varchar(256) NOT NULL,
  `fk_account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `maps`
--

INSERT INTO `maps` (`id`, `name`, `major_id`, `description`, `img_file`, `fk_account_id`) VALUES
(1, 'debug map 1', 1, 'This is a testing map. @€µÜöäé¥Йε←', 'debugmap1.png', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ordinary_places`
--

CREATE TABLE `ordinary_places` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `ordinary_places`
--

INSERT INTO `ordinary_places` (`id`, `name`) VALUES
(1, 'Exit'),
(2, 'WC');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `special_places`
--

CREATE TABLE `special_places` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `special_places`
--

INSERT INTO `special_places` (`id`, `name`) VALUES
(1, 'Auditorium');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `beacons`
--
ALTER TABLE `beacons`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `maps`
--
ALTER TABLE `maps`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `ordinary_places`
--
ALTER TABLE `ordinary_places`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `special_places`
--
ALTER TABLE `special_places`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `beacons`
--
ALTER TABLE `beacons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

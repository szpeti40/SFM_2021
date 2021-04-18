-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2021. Ápr 18. 14:53
-- Kiszolgáló verziója: 10.4.18-MariaDB
-- PHP verzió: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `hotel_sfm`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `guests`
--

CREATE TABLE `guests` (
  `id` int(11) NOT NULL,
  `firstname` varchar(100) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `lastname` varchar(100) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `postalcode` int(11) DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `arrival` datetime NOT NULL,
  `leaving` datetime NOT NULL,
  `r_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `receptionist`
--

CREATE TABLE `receptionist` (
  `id` int(11) NOT NULL,
  `firstname` varchar(100) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `lastname` varchar(100) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `username` varchar(20) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `PASSWORD` varchar(50) COLLATE utf8_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `receptionist`
--

INSERT INTO `receptionist` (`id`, `firstname`, `lastname`, `username`, `PASSWORD`) VALUES
(1, 'Cserepes', 'Virág', 'csvirag', 'jelszo'),
(2, 'db_admin', 'db_admin', 'admin', 'admin'),
(3, 'Sprint', 'Elek', 'selek', 'jelszo123');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `rooms`
--

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL,
  `receptionist_id` int(11) DEFAULT NULL,
  `balcony` tinyint(1) DEFAULT NULL,
  `extraBed` tinyint(1) DEFAULT NULL,
  `price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `guests`
--
ALTER TABLE `guests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `r_number` (`r_number`);

--
-- A tábla indexei `receptionist`
--
ALTER TABLE `receptionist`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- A tábla indexei `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `receptionist_id` (`receptionist_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `guests`
--
ALTER TABLE `guests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `receptionist`
--
ALTER TABLE `receptionist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `guests`
--
ALTER TABLE `guests`
  ADD CONSTRAINT `guests_ibfk_1` FOREIGN KEY (`r_number`) REFERENCES `rooms` (`id`);

--
-- Megkötések a táblához `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`receptionist_id`) REFERENCES `receptionist` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

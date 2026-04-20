-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-04-2026 a las 04:50:23
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mediateca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cd_audio`
--

CREATE TABLE `cd_audio` (
  `id_material` int(11) NOT NULL,
  `artista` varchar(100) NOT NULL,
  `duracion` varchar(20) NOT NULL,
  `genero` varchar(50) NOT NULL,
  `numero_canciones` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cd_audio`
--

INSERT INTO `cd_audio` (`id_material`, `artista`, `duracion`, `genero`, `numero_canciones`) VALUES
(8, 'Michael Jackson', '42:19', 'Pop', 9),
(9, 'Pink Floyd', '42:53', 'Rock Progresivo', 10),
(10, 'Miles Davis', '45:44', 'Jazz', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dvd`
--

CREATE TABLE `dvd` (
  `id_material` int(11) NOT NULL,
  `director` varchar(100) NOT NULL,
  `genero` varchar(50) NOT NULL,
  `duracion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dvd`
--

INSERT INTO `dvd` (`id_material`, `director`, `genero`, `duracion`) VALUES
(12, 'Christopher Nolan', 'Ciencia Ficción', '1:48:00'),
(13, 'Francis Ford Coppola', 'Drama / Crimen', '2:25:00'),
(14, 'John Lasseter', 'Animación', '1:21:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro`
--

CREATE TABLE `libro` (
  `id_material` int(11) NOT NULL,
  `autor` varchar(100) NOT NULL,
  `editorial` varchar(100) NOT NULL,
  `numero_paginas` int(11) NOT NULL,
  `isbn` varchar(30) NOT NULL,
  `anio_publicado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `libro`
--

INSERT INTO `libro` (`id_material`, `autor`, `editorial`, `numero_paginas`, `isbn`, `anio_publicado`) VALUES
(2, 'JRR Tolkien', 'Minotauro', 488, '9788445071408', 1954),
(3, 'Gabriel García Márquez', 'Editorial Sudamericana', 417, '9780307474728', 1967),
(4, 'JK Rowling', 'Salamandra', 254, '9788498384376', 1997),
(15, 'asdad', 'asdasd 111', 500, '782330033', 2026);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `material`
--

CREATE TABLE `material` (
  `id_material` int(11) NOT NULL,
  `codigo_interno` varchar(50) NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `material`
--

INSERT INTO `material` (`id_material`, `codigo_interno`, `titulo`, `unidades`) VALUES
(2, '001', 'El Señor de los Anillos: La Comunidad del Anillo', 5),
(3, '002', 'Cien Años de Soledad', 3),
(4, '003', 'Harry Potter y la Piedra Filosofal', 80),
(5, '001', 'National Geographic en Español', 4),
(6, '002', 'PC Magazine', 15),
(7, '003', 'Forbes', 5),
(8, '001', 'Thriller', 5),
(9, '002', 'The Dark Side of the Moon', 8),
(10, '003', 'Kind of Blue', 4),
(12, '001', 'Inception', 8),
(13, '002', 'El Padrino', 3),
(14, '003', 'Toy Story', 15),
(15, '004', 'Aasda', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `revista`
--

CREATE TABLE `revista` (
  `id_material` int(11) NOT NULL,
  `periodicidad` varchar(50) NOT NULL,
  `editorial` varchar(100) NOT NULL,
  `fecha_publicacion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `revista`
--

INSERT INTO `revista` (`id_material`, `periodicidad`, `editorial`, `fecha_publicacion`) VALUES
(5, 'Mensual', 'RBA Editores', '2023-10-01'),
(6, 'Bimestral', 'Ziff Davis', '2023-11-15'),
(7, 'Quincenal', 'Business Generators', '2026-04-17');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cd_audio`
--
ALTER TABLE `cd_audio`
  ADD PRIMARY KEY (`id_material`);

--
-- Indices de la tabla `dvd`
--
ALTER TABLE `dvd`
  ADD PRIMARY KEY (`id_material`);

--
-- Indices de la tabla `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`id_material`);

--
-- Indices de la tabla `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id_material`);

--
-- Indices de la tabla `revista`
--
ALTER TABLE `revista`
  ADD PRIMARY KEY (`id_material`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `material`
--
ALTER TABLE `material`
  MODIFY `id_material` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cd_audio`
--
ALTER TABLE `cd_audio`
  ADD CONSTRAINT `cd_audio_ibfk_1` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Filtros para la tabla `dvd`
--
ALTER TABLE `dvd`
  ADD CONSTRAINT `dvd_ibfk_1` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Filtros para la tabla `libro`
--
ALTER TABLE `libro`
  ADD CONSTRAINT `libro_ibfk_1` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Filtros para la tabla `revista`
--
ALTER TABLE `revista`
  ADD CONSTRAINT `revista_ibfk_1` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

DROP DATABASE IF EXISTS mediateca;
CREATE DATABASE mediateca;
USE mediateca;

CREATE TABLE `material` (
                            `id_material` int NOT NULL AUTO_INCREMENT,
                            `codigo_interno` varchar(50) NOT NULL,
                            `titulo` varchar(150) NOT NULL,
                            `unidades` int NOT NULL,
                            PRIMARY KEY (`id_material`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `libro` (
                         `id_material` int NOT NULL,
                         `autor` varchar(100) NOT NULL,
                         `editorial` varchar(100) NOT NULL,
                         `numero_paginas` int NOT NULL,
                         `isbn` varchar(30) NOT NULL,
                         `anio_publicado` int NOT NULL,
                         PRIMARY KEY (`id_material`),
                         FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`)
);

CREATE TABLE `revista` (
                           `id_material` int NOT NULL,
                           `periodicidad` varchar(50) NOT NULL,
                           `editorial` varchar(100) NOT NULL,
                           `fecha_publicacion` varchar(50) NOT NULL,
                           PRIMARY KEY (`id_material`),
                           FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`)
);

CREATE TABLE `dvd` (
                       `id_material` int NOT NULL,
                       `director` varchar(100) NOT NULL,
                       `genero` varchar(50) NOT NULL,
                       `duracion` varchar(20) NOT NULL,
                       PRIMARY KEY (`id_material`),
                       FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`)
);

CREATE TABLE `cd_audio` (
                            `id_material` int NOT NULL,
                            `artista` varchar(100) NOT NULL,
                            `duracion` varchar(20) NOT NULL,
                            `genero` varchar(50) NOT NULL,
                            `numero_canciones` int NOT NULL,
                            PRIMARY KEY (`id_material`),
                            FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`)
);
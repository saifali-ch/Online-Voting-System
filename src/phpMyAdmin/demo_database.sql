-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 02, 2020 at 10:54 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `voting`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `Name` varchar(25) NOT NULL,
  `CNIC` varchar(13) NOT NULL,
  `Password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`Name`, `CNIC`, `Password`) VALUES
('Saif Ali', '3110212345', '12345678');

-- --------------------------------------------------------

--
-- Table structure for table `candidates`
--

CREATE TABLE `candidates` (
  `Id` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Position` varchar(20) NOT NULL,
  `Votes` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `candidates`
--

INSERT INTO `candidates` (`Id`, `Name`, `Position`, `Votes`) VALUES
(1, 'Saif', 'Prime Minister', 20),
(2, 'Ali', 'Prime Minister', 43),
(3, 'Ashfaq', 'Prime Minister', 10),
(4, 'Amir', 'Prime Minister', 6),
(5, 'Saif', 'MNA', 10),
(6, 'Ali', 'MNA', 3),
(7, 'Ashfaq', 'MNA', 5),
(8, 'Amir', 'MNA', 20);

-- --------------------------------------------------------

--
-- Table structure for table `positions`
--

CREATE TABLE `positions` (
  `Id` int(11) NOT NULL,
  `PositionName` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `positions`
--

INSERT INTO `positions` (`Id`, `PositionName`) VALUES
(1, 'Prime Minister'),
(2, 'MNA'),
(3, 'MPA'),
(4, 'Chairman');

-- --------------------------------------------------------

--
-- Table structure for table `voters`
--

CREATE TABLE `voters` (
  `Name` varchar(25) NOT NULL,
  `CNIC` varchar(13) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `Status` varchar(5) NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `voters`
--

INSERT INTO `voters` (`Name`, `CNIC`, `Password`, `Status`) VALUES
('Ashfaq', '1', '1', 'true'),
('Amir', '2', '2', 'true'),
('Ali', '3', '3', 'true'),
('Saif Ali', '4', '4', 'false');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`CNIC`);

--
-- Indexes for table `candidates`
--
ALTER TABLE `candidates`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `positions`
--
ALTER TABLE `positions`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `voters`
--
ALTER TABLE `voters`
  ADD PRIMARY KEY (`CNIC`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `positions`
--
ALTER TABLE `positions`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

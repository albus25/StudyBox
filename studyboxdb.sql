-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2020 at 03:30 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studyboxdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblcoursedetail`
--

CREATE TABLE `tblcoursedetail` (
  `coursedetailID` int(3) NOT NULL,
  `studID` int(11) NOT NULL,
  `courseID` int(11) NOT NULL,
  `studName` varchar(50) NOT NULL,
  `courseTitle` varchar(50) NOT NULL,
  `courseDuration` varchar(7) NOT NULL,
  `courseStartDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblcoursedetail`
--

INSERT INTO `tblcoursedetail` (`coursedetailID`, `studID`, `courseID`, `studName`, `courseTitle`, `courseDuration`, `courseStartDate`) VALUES
(3, 1, 1, 'Albus', 'BTech IT', '4 Year', '2020-08-03'),
(6, 1, 2, 'Albus', 'BE Mechanical', '4 Year', '2020-08-03'),
(8, 2, 2, 'JohnWick', 'BE Mechanical', '4 Year', '2020-08-03');

-- --------------------------------------------------------

--
-- Table structure for table `tblstudent`
--

CREATE TABLE `tblstudent` (
  `studID` int(3) NOT NULL,
  `studName` varchar(50) NOT NULL,
  `studMob` varchar(11) NOT NULL,
  `studPass` varchar(500) NOT NULL,
  `studAge` int(3) NOT NULL,
  `studCity` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblstudent`
--

INSERT INTO `tblstudent` (`studID`, `studName`, `studMob`, `studPass`, `studAge`, `studCity`) VALUES
(1, 'Albus', '8141745326', '786', 22, 'Banglore'),
(2, 'JohnWick', '9825782279', 'Baba', 35, 'Mumbai');

-- --------------------------------------------------------

--
-- Table structure for table `tblstudybox`
--

CREATE TABLE `tblstudybox` (
  `courseID` int(3) NOT NULL,
  `courseCategory` varchar(50) NOT NULL,
  `courseTitle` varchar(50) NOT NULL,
  `courseDescription` varchar(500) NOT NULL,
  `courseDuration` varchar(7) NOT NULL,
  `courseStartDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblstudybox`
--

INSERT INTO `tblstudybox` (`courseID`, `courseCategory`, `courseTitle`, `courseDescription`, `courseDuration`, `courseStartDate`) VALUES
(1, 'Science', 'BTech IT', 'Learn computer programming and application development', '4 Year', '2020-08-03'),
(2, 'Science', 'BE Mechanical', 'Mechanical engineering is an engineering discipline that combines engineering physics and mathematics principles with materials science to design, analyze, manufacture, and maintain mechanical systems. It is one of the oldest and broadest of the engineering disciplines.', '4 Year', '2020-08-03');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblcoursedetail`
--
ALTER TABLE `tblcoursedetail`
  ADD PRIMARY KEY (`coursedetailID`);

--
-- Indexes for table `tblstudent`
--
ALTER TABLE `tblstudent`
  ADD PRIMARY KEY (`studID`);

--
-- Indexes for table `tblstudybox`
--
ALTER TABLE `tblstudybox`
  ADD PRIMARY KEY (`courseID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblcoursedetail`
--
ALTER TABLE `tblcoursedetail`
  MODIFY `coursedetailID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tblstudent`
--
ALTER TABLE `tblstudent`
  MODIFY `studID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tblstudybox`
--
ALTER TABLE `tblstudybox`
  MODIFY `courseID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 09, 2025 at 10:08 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `commandline`
--

-- --------------------------------------------------------

--
-- Table structure for table `command_actions`
--

CREATE TABLE `command_actions` (
  `action_id` int(11) NOT NULL,
  `action_name` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `is_active` char(1) DEFAULT 'Y',
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `command_execution_log`
--

CREATE TABLE `command_execution_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `command_id` int(11) DEFAULT NULL,
  `input_args` varchar(200) DEFAULT NULL,
  `output` varchar(200) DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `command_log`
--

CREATE TABLE `command_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `action_id` int(11) DEFAULT NULL,
  `object_id` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `command_objects`
--

CREATE TABLE `command_objects` (
  `object_id` int(11) NOT NULL,
  `object_name` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `is_active` char(1) DEFAULT 'Y',
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `role` enum('admin','user') NOT NULL,
  `status` varchar(20) DEFAULT 'active',
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_log`
--

CREATE TABLE `user_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `event_detail` varchar(50) DEFAULT NULL,
  `event_type` varchar(20) DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `command_actions`
--
ALTER TABLE `command_actions`
  ADD PRIMARY KEY (`action_id`),
  ADD UNIQUE KEY `action_name` (`action_name`);

--
-- Indexes for table `command_execution_log`
--
ALTER TABLE `command_execution_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `command_id` (`command_id`);

--
-- Indexes for table `command_log`
--
ALTER TABLE `command_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `action_id` (`action_id`),
  ADD KEY `object_id` (`object_id`);

--
-- Indexes for table `command_objects`
--
ALTER TABLE `command_objects`
  ADD PRIMARY KEY (`object_id`),
  ADD UNIQUE KEY `object_name` (`object_name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `user_log`
--
ALTER TABLE `user_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `command_actions`
--
ALTER TABLE `command_actions`
  MODIFY `action_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `command_execution_log`
--
ALTER TABLE `command_execution_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `command_log`
--
ALTER TABLE `command_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `command_objects`
--
ALTER TABLE `command_objects`
  MODIFY `object_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_log`
--
ALTER TABLE `user_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `command_execution_log`
--
ALTER TABLE `command_execution_log`
  ADD CONSTRAINT `command_execution_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `command_execution_log_ibfk_2` FOREIGN KEY (`command_id`) REFERENCES `command_actions` (`action_id`) ON DELETE CASCADE;

--
-- Constraints for table `command_log`
--
ALTER TABLE `command_log`
  ADD CONSTRAINT `command_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `command_log_ibfk_2` FOREIGN KEY (`action_id`) REFERENCES `command_actions` (`action_id`),
  ADD CONSTRAINT `command_log_ibfk_3` FOREIGN KEY (`object_id`) REFERENCES `command_objects` (`object_id`);

--
-- Constraints for table `user_log`
--
ALTER TABLE `user_log`
  ADD CONSTRAINT `user_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

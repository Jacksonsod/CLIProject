-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 09, 2025 at 10:44 AM
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

--
-- Dumping data for table `command_actions`
--

INSERT INTO `command_actions` (`action_id`, `action_name`, `description`, `is_active`, `created_at`) VALUES
(1, 'create', 'Create a new entity', 'Y', '2025-11-09 11:37:57'),
(2, 'delete', 'Delete an existing entity', 'Y', '2025-11-09 11:37:57'),
(3, 'list', 'List entities', 'Y', '2025-11-09 11:37:57'),
(4, 'switch', 'Switch active user', 'Y', '2025-11-09 11:37:57'),
(5, 'promote', 'Promote user to admin', 'Y', '2025-11-09 11:37:57'),
(6, 'login', 'Authenticate user', 'Y', '2025-11-09 11:37:57'),
(7, 'logout', 'End session', 'Y', '2025-11-09 11:37:57'),
(8, 'reset', 'Reset user password', 'Y', '2025-11-09 11:37:57'),
(9, 'change', 'Change user password', 'Y', '2025-11-09 11:37:57'),
(10, 'help', 'Show help menu', 'Y', '2025-11-09 11:37:57'),
(11, 'remove', 'Remove an entity or item', 'Y', '2025-11-09 11:37:57');

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
-- Table structure for table `command_map`
--

CREATE TABLE `command_map` (
  `command_id` int(11) NOT NULL,
  `action_id` int(11) NOT NULL,
  `object_id` int(11) NOT NULL,
  `command_alias` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `command_map`
--

INSERT INTO `command_map` (`command_id`, `action_id`, `object_id`, `command_alias`, `description`, `created_at`) VALUES
(1, 1, 1, 'create user', 'Register a new user', '2025-11-09 11:44:11'),
(2, 2, 1, 'delete user', 'Remove a user', '2025-11-09 11:44:11'),
(3, 3, 1, 'list users', 'List all users', '2025-11-09 11:44:11'),
(4, 4, 1, 'switch user', 'Switch active user', '2025-11-09 11:44:11'),
(5, 5, 1, 'promote user', 'Promote user to admin', '2025-11-09 11:44:11'),
(6, 6, 5, 'login', 'Authenticate and start session', '2025-11-09 11:44:11'),
(7, 7, 5, 'logout', 'End current session', '2025-11-09 11:44:11'),
(8, 8, 4, 'reset password', 'Admin resets user password', '2025-11-09 11:44:11'),
(9, 9, 4, 'change password', 'User changes own password', '2025-11-09 11:44:11'),
(10, 1, 2, 'create folder', 'Create a new folder', '2025-11-09 11:44:11'),
(11, 2, 2, 'delete folder', 'Delete a folder', '2025-11-09 11:44:11'),
(12, 3, 3, 'list files', 'List files in directory', '2025-11-09 11:44:11'),
(13, 1, 3, 'create file', 'Create a new file', '2025-11-09 11:44:11'),
(14, 2, 3, 'delete file', 'Delete a file', '2025-11-09 11:44:11'),
(15, 10, 6, 'help', 'Show help menu', '2025-11-09 11:44:11'),
(16, 11, 3, 'remove file', 'Remove a file from the system', '2025-11-09 11:44:11');

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

--
-- Dumping data for table `command_objects`
--

INSERT INTO `command_objects` (`object_id`, `object_name`, `description`, `is_active`, `created_at`) VALUES
(1, 'user', 'User account', 'Y', '2025-11-09 11:37:57'),
(2, 'folder', 'File system folder', 'Y', '2025-11-09 11:37:57'),
(3, 'file', 'File system file', 'Y', '2025-11-09 11:37:57'),
(4, 'password', 'User password', 'Y', '2025-11-09 11:37:57'),
(5, 'session', 'Login session', 'Y', '2025-11-09 11:37:57'),
(6, 'system', 'System help or info', 'Y', '2025-11-09 11:37:57');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('admin','user') NOT NULL,
  `status` varchar(20) DEFAULT 'active',
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password_hash`, `role`, `status`, `created_at`) VALUES
(1, 'jackson', '123', 'user', 'active', '2025-11-09 11:34:02');

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
-- Indexes for table `command_map`
--
ALTER TABLE `command_map`
  ADD PRIMARY KEY (`command_id`),
  ADD UNIQUE KEY `command_alias` (`command_alias`),
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
  MODIFY `action_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

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
-- AUTO_INCREMENT for table `command_map`
--
ALTER TABLE `command_map`
  MODIFY `command_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `command_objects`
--
ALTER TABLE `command_objects`
  MODIFY `object_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
-- Constraints for table `command_map`
--
ALTER TABLE `command_map`
  ADD CONSTRAINT `command_map_ibfk_1` FOREIGN KEY (`action_id`) REFERENCES `command_actions` (`action_id`),
  ADD CONSTRAINT `command_map_ibfk_2` FOREIGN KEY (`object_id`) REFERENCES `command_objects` (`object_id`);

--
-- Constraints for table `user_log`
--
ALTER TABLE `user_log`
  ADD CONSTRAINT `user_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

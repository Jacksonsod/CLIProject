-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2025 at 10:47 AM
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
(11, 'remove', 'Remove an entity or item', 'Y', '2025-11-09 11:37:57'),
(12, 'clear', 'Clear the terminal screen', 'Y', '2025-11-22 10:50:44'),
(13, 'date', 'Show current date', 'Y', '2025-11-22 10:50:44'),
(14, 'time', 'Show current time', 'Y', '2025-11-22 10:50:44'),
(15, 'uptime', 'Show application uptime', 'Y', '2025-11-22 10:50:44'),
(16, 'restart', 'Restart the application', 'Y', '2025-11-22 10:50:44'),
(17, 'shutdown', 'Shutdown the application', 'Y', '2025-11-22 10:50:44'),
(18, 'exit', 'Exit the application', 'Y', '2025-11-22 10:50:44');

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

--
-- Dumping data for table `command_execution_log`
--

INSERT INTO `command_execution_log` (`log_id`, `user_id`, `command_id`, `input_args`, `output`, `timestamp`) VALUES
(1, NULL, NULL, 'help', NULL, '2025-11-22 10:41:59'),
(2, NULL, NULL, 'cu', NULL, '2025-11-22 10:42:04'),
(3, NULL, NULL, 'logi', NULL, '2025-11-22 10:42:14'),
(4, NULL, NULL, 'login', NULL, '2025-11-22 10:42:17'),
(5, 5, NULL, 'date', NULL, '2025-11-22 10:42:28'),
(6, 5, NULL, 'log', NULL, '2025-11-22 10:42:31'),
(7, 5, NULL, 'time', NULL, '2025-11-22 10:43:43'),
(8, 5, NULL, 'logout', NULL, '2025-11-22 10:44:30'),
(9, NULL, NULL, 'system', NULL, '2025-11-22 10:45:45'),
(10, NULL, NULL, 'login', NULL, '2025-11-22 10:45:48'),
(11, 5, NULL, 'system', NULL, '2025-11-22 10:46:03'),
(12, NULL, NULL, 'll', NULL, '2025-11-22 10:53:44'),
(13, NULL, NULL, 'login', NULL, '2025-11-22 10:53:47'),
(14, NULL, NULL, 'lo', NULL, '2025-11-22 10:54:32'),
(15, NULL, 6, 'login', NULL, '2025-11-22 10:54:35'),
(16, NULL, 6, 'login', NULL, '2025-11-27 10:56:30'),
(17, 2, NULL, 'df testalii', NULL, '2025-11-27 10:57:08'),
(18, NULL, NULL, 'lgi', NULL, '2025-11-27 11:16:28'),
(19, NULL, NULL, 'u', NULL, '2025-11-27 11:26:12'),
(20, NULL, NULL, 'cu', NULL, '2025-11-27 11:26:16'),
(21, NULL, NULL, 'cu', NULL, '2025-11-27 11:26:38'),
(22, NULL, NULL, 'cu', NULL, '2025-11-27 11:26:49'),
(23, NULL, 6, 'login', NULL, '2025-11-27 11:26:55'),
(24, 6, NULL, 'log', NULL, '2025-11-27 11:27:10');

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
(16, 11, 3, 'remove file', 'Remove a file from the system', '2025-11-09 11:44:11'),
(17, 3, 6, 'history', 'Show command history', '2025-11-19 21:01:06'),
(18, 3, 6, 'log', 'View CLI logs', '2025-11-19 21:01:06'),
(19, 3, 6, 'status', 'Show current session status', '2025-11-19 21:01:06'),
(20, 3, 6, 'memory', 'Show JVM memory usage', '2025-11-19 21:01:06'),
(21, 12, 6, 'clear', 'Clear the terminal screen', '2025-11-22 10:50:44'),
(22, 13, 6, 'date', 'Show current date', '2025-11-22 10:50:44'),
(23, 14, 6, 'time', 'Show current time', '2025-11-22 10:50:44'),
(24, 15, 6, 'uptime', 'Show application uptime', '2025-11-22 10:50:44'),
(25, 16, 6, 'restart', 'Restart the application', '2025-11-22 10:50:44'),
(26, 17, 6, 'shutdown', 'Shutdown the application', '2025-11-22 10:50:44'),
(27, 18, 6, 'exit', 'Exit the application', '2025-11-22 10:50:44');

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
(1, 'jackson', '123', 'user', 'active', '2025-11-09 11:34:02'),
(2, 'jacksonb', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'user', 'active', '2025-11-21 09:39:57'),
(3, 'yves', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'user', 'active', '2025-11-21 12:10:18'),
(4, 'edison', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'user', 'active', '2025-11-21 12:23:19'),
(5, 'meek', '16a0a2e664cff4e61bb46f89adad749641cd4aa3ce384cc5bf0400137824b59a', 'user', 'active', '2025-11-22 10:35:41'),
(6, 'shyaka', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'user', 'active', '2025-11-27 11:26:45');

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
-- Dumping data for table `user_log`
--

INSERT INTO `user_log` (`log_id`, `user_id`, `event_detail`, `event_type`, `timestamp`) VALUES
(1, 5, 'Login successful', 'LOGIN', '2025-11-22 10:42:24'),
(2, 5, 'Logged out', 'LOGOUT', '2025-11-22 10:44:30'),
(3, 5, 'Login successful', 'LOGIN', '2025-11-22 10:45:56'),
(4, 2, 'Login successful', 'LOGIN', '2025-11-27 10:56:38'),
(5, 6, 'User created', 'CREATE_USER', '2025-11-27 11:26:45'),
(6, 6, 'Login successful', 'LOGIN', '2025-11-27 11:27:03');

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
  MODIFY `action_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `command_execution_log`
--
ALTER TABLE `command_execution_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `command_map`
--
ALTER TABLE `command_map`
  MODIFY `command_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `command_objects`
--
ALTER TABLE `command_objects`
  MODIFY `object_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user_log`
--
ALTER TABLE `user_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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

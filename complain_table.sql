/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50142
Source Host           : localhost:3306
Source Database       : ems

Target Server Type    : MYSQL
Target Server Version : 50142
File Encoding         : 65001

Date: 2016-06-06 18:27:19
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `complain_table`
-- ----------------------------
DROP TABLE IF EXISTS `complain_table`;
CREATE TABLE `complain_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobType` varchar(255) DEFAULT NULL,
  `acceptTime` datetime DEFAULT NULL,
  `timeOut` datetime DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `complainPhone` varchar(255) DEFAULT NULL,
  `contactPhone` varchar(255) DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `faultSite` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `community` varchar(255) DEFAULT NULL,
  `worker` varchar(255) DEFAULT NULL,
  `faultType` varchar(255) DEFAULT NULL,
  `faultCause` varchar(255) DEFAULT NULL,
  `disposeStatus` varchar(255) DEFAULT NULL,
  `untreatedCause` varchar(255) DEFAULT NULL,
  `feedbackTime` datetime DEFAULT NULL,
  `finishTime` datetime DEFAULT NULL,
  `handleTime` varchar(255) DEFAULT NULL,
  `overTimeCause` varchar(255) DEFAULT NULL,
  `rejectTime` datetime DEFAULT NULL,
  `rejectFinishTime` datetime DEFAULT NULL,
  `facility` varchar(255) DEFAULT NULL,
  `port` tinyint(255) DEFAULT NULL,
  `complainCause` varchar(255) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of complain_table
-- ----------------------------
INSERT INTO complain_table VALUES ('1', '1', '2016-06-06 18:04:28', '2016-06-06 18:04:30', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2016-06-06 18:21:46', '2016-06-06 18:04:48', '1', '1', '2016-06-06 18:04:14', '2016-06-06 18:04:10', '1', '1', '1', '1');
INSERT INTO complain_table VALUES ('2', '2', '2016-06-06 18:22:07', '2016-06-06 18:22:09', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2016-06-06 18:22:18', '2016-06-06 18:22:22', '2', '2', '2016-06-06 18:22:26', '2016-06-06 18:22:29', '2', '2', '2', '2');

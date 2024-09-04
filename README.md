# SPRING-REACT-MYSQL
2024-08-02 npm install axios
2024-08-03 npm i react-daum-postcode
2024-08-26 npm i dayjs

# query
# DCL 
GRANT SELECT, INSERT, UPDATE, DELETE 
ON board.* 
TO 'developer'@'*';

# DDL
CREATE TABLE BOARD
(
  BOARD_NUMBER   INT         NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  TITLE          TEXT        NOT NULL COMMENT '게시물 제목',
  CONTENT        TEXT        NOT NULL COMMENT '게시물 내용',
  WRITE_DATETIME DATETIME    NOT NULL COMMENT '게시물 작성 날짜 및 시간',
  FAVORITE_COUNT INT         NOT NULL DEFAULT 0 COMMENT '게시물 좋아요 수',
  COMMENT_COUNT  INT         NOT NULL DEFAULT 0 COMMENT '게시물 댓글 수',
  VIEW_COUNT     INT         NOT NULL DEFAULT 0 COMMENT '게시물 조회수',
  WRITER_EMAIL   VARCHAR(50) NOT NULL COMMENT '게시물 작성자 이메일',
  PRIMARY KEY (BOARD_NUMBER)
) COMMENT '게시물';

CREATE TABLE COMMENT
(
  COMMENT_NUMBER INT         NOT NULL COMMENT '댓글 번호',
  CONTENT        TEXT        NOT NULL COMMENT '댓글 내용',
  WRITE_DATETIME DATETIME    NOT NULL COMMENT '작성 날짜 및 시간',
  USER_EMAIL     VARCHAR(50) NOT NULL COMMENT '사용자 이메일',
  BOARD_NUMBER   INT         NOT NULL COMMENT '게시물 번호',
  PRIMARY KEY (COMMENT_NUMBER)
) COMMENT '댓글 테이블';

CREATE TABLE FAVORITE
(
  USER_EMAIL   VARCHAR(50) NOT NULL COMMENT '사용자 이메일',
  BOARD_NUMBER INT         NOT NULL COMMENT '게시물 번호',
  PRIMARY KEY (USER_EMAIL, BOARD_NUMBER)
) COMMENT '좋아요 테이블';

CREATE TABLE IMAGE
(
  BOARD_NUMBER INT  NOT NULL COMMENT '게시물 번호',
  IMAGE        TEXT NOT NULL COMMENT '게시물 이미지 URL'
) COMMENT '게시물 이미지 테이블';

CREATE TABLE SEARCH_LOG
(
  SEQUENCE      INT     NOT NULL AUTO_INCREMENT COMMENT '시퀀스',
  SEARCH_WORD   TEXT    NOT NULL COMMENT '검색어',
  RELATION_WORD TEXT    NULL     COMMENT '관련 검색어',
  RELATION      BOOLEAN NOT NULL COMMENT '관련 검색어 여부',
  PRIMARY KEY (SEQUENCE)
) COMMENT '검색 기록 테이블';

CREATE TABLE USER
(
  EMAIL          VARCHAR(50)  NOT NULL COMMENT '사용자 이메일',
  PASSWORD       VARCHAR(100) NOT NULL COMMENT '사용자 비밀번호',
  NICKNAME       VARCHAR(20)  NOT NULL COMMENT '사용자 닉네임',
  TEL_NUMBER     VARCHAR(15)  NOT NULL COMMENT '사용자 휴대전화번호',
  ADDRESS        TEXT         NOT NULL COMMENT '사용자 주소',
  ADDRESS_DETAIL TEXT         NULL     COMMENT '사용자 상세 주소',
  PROFILE_IMAGE  TEXT         NULL     COMMENT '사용자 프로필 사진 URL',
  PRIMARY KEY (EMAIL)
) COMMENT '사용자 테이블';

ALTER TABLE IMAGE
  ADD CONSTRAINT FK_BOARD_TO_IMAGE
    FOREIGN KEY (BOARD_NUMBER)
    REFERENCES BOARD (BOARD_NUMBER);

ALTER TABLE BOARD
  ADD CONSTRAINT FK_USER_TO_BOARD
    FOREIGN KEY (WRITER_EMAIL)
    REFERENCES USER (EMAIL);

ALTER TABLE COMMENT
  ADD CONSTRAINT FK_USER_TO_COMMENT
    FOREIGN KEY (USER_EMAIL)
    REFERENCES USER (EMAIL);

ALTER TABLE COMMENT
  ADD CONSTRAINT FK_BOARD_TO_COMMENT
    FOREIGN KEY (BOARD_NUMBER)
    REFERENCES BOARD (BOARD_NUMBER);

ALTER TABLE FAVORITE
  ADD CONSTRAINT FK_USER_TO_FAVORITE
    FOREIGN KEY (USER_EMAIL)
    REFERENCES USER (EMAIL);

ALTER TABLE FAVORITE
  ADD CONSTRAINT FK_BOARD_TO_FAVORITE
    FOREIGN KEY (BOARD_NUMBER)
    REFERENCES BOARD (BOARD_NUMBER);

CREATE USER 'developer'@'*' IDENTIFIED BY 'P!ssw0rd';

SELECT * FROM BOARD_LIST_VIEW LIMIT 100;

CREATE VIEW board_list_view AS SELECT
B.board_number AS board_number,
B.title AS title,
B.content AS content,
I.image AS title_image,
B.view_count AS view_count,
B.favorite_count AS favorite_count,
B.comment_count AS comment_count,
B.write_datetime AS write_datetime,
U.email AS writer_email,
U.nickname AS writer_nickname,
U.profile_image AS writer_profile_image
FROM board AS B
INNER JOIN user AS U
ON B.writer_email = U.email
LEFT JOIN (SELECT board_number, ANY_VALUE(image) AS image FROM image GROUP BY board_number) AS I
ON B.board_number = I.board_number;

ALTER TABLE `image` ADD COLUMN `sequence` INT PRIMARY KEY AUTO_INCREMENT COMMENT '이미지 번호';

# DML
-- Active: 1699164539170@@127.0.0.1@3306@board
-- 회원가입
INSERT INTO USER VALUES('email@email.com', 'Pissw0rd', 'nickname', '01012345678', '부산광역시 부산진구', '롯데백화점', null);

-- 로그인
SELECT * FROM user WHERE email = 'email@email.com';

-- 게시물 작성
INSERT INTO BOARD(title, content, write_datetime, favorite_count, comment_count, view_count, writer_email)
VALUES ('제목입니다', '내용입니다.', sysdate(), 0, 0, 0, 'email@email.com');


INSERT INTO image VALUES (1, 'url');

-- 댓글 작성
INSERT INTO comment(content, WRITE_DATETIME, user_email, board_number)
VALUES ('반갑습니다.', '2023-08-20 20:57', 'email@email.com', 1);

UPDATE board SET comment_count = comment_count + 1 WHERE board_number = 1;

-- 좋아요
INSERT INTO favorite VALUES ('email@email.com', 1);

UPDATE board SET favorite_count = favorite_count + 1 WHERE board_number = 1;

DELETE FROM favorite WHERE user_email = 'email@email.com' AND board_number = 1;

UPDATE board SET favorite_count = favorite_count - 1 WHERE board_number = 1;


-- 게시물
UPDATE BOARD SET title = '수정 제목입니다.', content = '수정 내용입니다.' WHERE board_number = 1;

DELETE from image WHERE board_number = 1;
INSERT INTO image VALUES (1, 'url');

-- 게시물 삭제
DELETE FROM comment WHERE board_number = 1;

DELETE FROM favorite WHERE board_number = 1;

DELETE FROM board WHERE board_number = 1;

-- 상세 게시물 불러오기
SELECT 
    B.board_number AS board_number,
    B.title AS title,
    B.content AS content,
    B.write_datetime AS writeDatetime,
    B.writer_email AS writerEmail,
    U.nickname AS writerNickname,
    U.profile_image AS writerProfileImage
FROM board AS B
INNER JOIN user AS U
ON B.writer_email = U.email
WHERE board_number = 9;

SELECT image 
FROM image
WHERE board_number = 9;

SELECT 
    U.email AS email,
    U.nickname AS nickname,
    U.profile_image AS profileImage
FROM favorite AS F
INNER JOIN user AS U
ON F.user_email = U.email
WHERE F.board_number = 1;

SELECT 
    U.nickname AS nickname,
    U.profile_image AS profile_image,
    C.write_datetime AS write_datetime,
    C.content AS content
FROM comment AS C
INNER JOIN user AS U
ON C.user_email = U.email
WHERE C.board_number = 1
ORDER BY C.write_datetime DESC;


-- 최신게시물 리스트 불러오기
SELECT * 
FROM board_list_view
ORDER BY write_datetime DESC
LIMIT 0, 5;

-- 검색어 리스트 불러오기
SELECT * 
FROM board_list_view
WHERE title LIKE '%제목%' OR content LIKE '%제목%'
ORDER BY write_datetime DESC
LIMIT 0, 5;

-- 주간 상위3
SELECT * 
FROM board_list_view
WHERE write_datetime BETWEEN '2023-11-19 15:38:00' AND '2023-11-26 15:39:40'
ORDER BY favorite_count DESC, comment_count DESC, view_count DESC, write_datetime DESC
LIMIT 3;


--특정 유저 게시물 리스트 불러오기
SELECT * 
FROM board_list_view
WHERE writer_email = 'email@email.com'
ORDER BY write_datetime DESC;

-- 인기게시물 리스트
SELECT search_word, count(search_word) AS count
FROM search_log
WHERE relation IS FALSE
GROUP BY search_word
ORDER BY count desc
LIMIT 15;

-- 관련 검색어 리스트
SELECT relation_word, count(relation_word) AS count
FROM search_log
WHERE search_word = '검색어'
GROUP BY relation_word
ORDER BY count DESC
LIMIT 15;

-- 유저 정보 불러오기 / 로그인 유저 정보 불러오기
SELECT *
FROM user
WHERE email = 'email@email.com';

-- 닉네임 수정
UPDATE user SET nickname = '수정 닉네임' WHERE email = 'email@email.com';

-- 프로필 이미지 수정
UPDATE user SET profile_image = 'url2' WHERE email = 'email@email.com';

-- 회원가입
INSERT INTO `user` VALUES ('email@email.com', 'P!ssw0rd', 'nickname', '010123456789', '부산광역시 부산진구', '롯데백화점', null);

-- 로그인
SELECT * FROM `user` WHERE `EMAIL` = 'email@email.com';

SELECT * FROM comment;

// DB생성 0904 쿼리
-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        8.0.35 - MySQL Community Server - GPL
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- board 데이터베이스 구조 내보내기
DROP DATABASE IF EXISTS `board`;
CREATE DATABASE IF NOT EXISTS `board` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `board`;

-- 테이블 board.board 구조 내보내기
DROP TABLE IF EXISTS `board`;
CREATE TABLE IF NOT EXISTS `board` (
  `BOARD_NUMBER` int NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  `TITLE` text NOT NULL COMMENT '게시물 제목',
  `CONTENT` text NOT NULL COMMENT '게시물 내용',
  `WRITE_DATETIME` datetime NOT NULL COMMENT '게시물 작성 날짜 및 시간',
  `FAVORITE_COUNT` int NOT NULL DEFAULT '0' COMMENT '게시물 좋아요 수',
  `COMMENT_COUNT` int NOT NULL DEFAULT '0' COMMENT '게시물 댓글 수',
  `VIEW_COUNT` int NOT NULL DEFAULT '0' COMMENT '게시물 조회수',
  `WRITER_EMAIL` varchar(50) NOT NULL COMMENT '게시물 작성자 이메일',
  PRIMARY KEY (`BOARD_NUMBER`),
  KEY `FK_USER_TO_BOARD` (`WRITER_EMAIL`),
  CONSTRAINT `FK_USER_TO_BOARD` FOREIGN KEY (`WRITER_EMAIL`) REFERENCES `user` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='게시물 테이블';

-- 테이블 데이터 board.board:~19 rows (대략적) 내보내기
INSERT INTO `board` (`BOARD_NUMBER`, `TITLE`, `CONTENT`, `WRITE_DATETIME`, `FAVORITE_COUNT`, `COMMENT_COUNT`, `VIEW_COUNT`, `WRITER_EMAIL`) VALUES
	(10, 'test', 'test', '2024-08-18 16:36:04', 1, 8, 14, 'email3@email3.com'),
	(11, 'test', 'test', '2024-08-18 16:36:14', 0, 0, 5, 'email3@email3.com'),
	(12, 'test', 'test', '2024-08-18 16:36:36', 0, 0, 1, 'email3@email3.com'),
	(13, 'test', 'test', '2024-08-18 16:37:51', 0, 0, 0, 'email3@email3.com'),
	(14, 'test', 'test', '2024-08-18 16:37:53', 0, 0, 1, 'email3@email3.com'),
	(15, 'test', 'test', '2024-08-18 16:38:10', 0, 0, 1, 'email3@email3.com'),
	(16, 'test', 'test', '2024-08-18 16:38:34', 0, 0, 1, 'email3@email3.com'),
	(17, 'test', 'test', '2024-08-18 16:38:41', 0, 0, 1, 'email3@email3.com'),
	(18, 'test', 'test', '2024-08-18 16:39:19', 0, 0, 1, 'email3@email3.com'),
	(19, 'test', 'test', '2024-08-18 16:43:38', 0, 0, 0, 'email3@email3.com'),
	(20, 'test', 'test', '2024-08-18 16:44:27', 0, 0, 1, 'email3@email3.com'),
	(21, 'test', 'test', '2024-08-18 16:45:07', 0, 0, 0, 'email3@email3.com'),
	(22, 'test', 'test', '2024-08-18 16:45:27', 0, 0, 0, 'email3@email3.com'),
	(23, 'test', 'test', '2024-08-18 16:45:34', 0, 0, 0, 'email3@email3.com'),
	(24, 'test', 'test', '2024-08-18 16:59:51', 0, 0, 1, 'test@email.com'),
	(25, '수정', '1231232', '2024-08-27 21:41:15', 1, 5, 12, 'email3@email3.com'),
	(26, 'test11', 'tetete', '2024-09-03 21:13:10', 0, 0, 0, 'email3@email3.com'),
	(27, '2', '2123\n\n123\n12\n4\n124\n12\n5\n12\n312\n3\n1245\n12\n5\n12\n', '2024-09-03 21:16:03', 0, 0, 0, 'email3@email3.com'),
	(28, '2', '2', '2024-09-03 21:25:20', 0, 0, 0, 'email3@email3.com');

-- 뷰 board.board_list_view 구조 내보내기
DROP VIEW IF EXISTS `board_list_view`;
-- VIEW 종속성 오류를 극복하기 위해 임시 테이블을 생성합니다.
CREATE TABLE `board_list_view` (
	`board_number` INT NOT NULL COMMENT '게시물 번호',
	`title` TEXT NOT NULL COMMENT '게시물 제목' COLLATE 'utf8mb4_0900_ai_ci',
	`content` TEXT NOT NULL COMMENT '게시물 내용' COLLATE 'utf8mb4_0900_ai_ci',
	`title_image` MEDIUMTEXT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`view_count` INT NOT NULL COMMENT '게시물 조회수',
	`favorite_count` INT NOT NULL COMMENT '게시물 좋아요 수',
	`comment_count` INT NOT NULL COMMENT '게시물 댓글 수',
	`write_datetime` DATETIME NOT NULL COMMENT '게시물 작성 날짜 및 시간',
	`writer_email` VARCHAR(1) NOT NULL COMMENT '게시물 작성자 이메일' COLLATE 'utf8mb4_0900_ai_ci',
	`writer_nickname` VARCHAR(1) NOT NULL COMMENT '사용자 닉네임' COLLATE 'utf8mb4_0900_ai_ci',
	`writer_profile_image` TEXT NULL COMMENT '사용자 프로필 사진 URL' COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=MyISAM;

-- 테이블 board.comment 구조 내보내기
DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `COMMENT_NUMBER` int NOT NULL AUTO_INCREMENT COMMENT '댓글 번호',
  `CONTENT` text NOT NULL COMMENT '댓글 내용',
  `WRITE_DATETIME` datetime NOT NULL COMMENT '작성 날짜 및 시간',
  `USER_EMAIL` varchar(50) NOT NULL COMMENT '사용자 이메일',
  `BOARD_NUMBER` int NOT NULL COMMENT '게시물 번호',
  PRIMARY KEY (`COMMENT_NUMBER`),
  KEY `FK_USER_TO_COMMENT` (`USER_EMAIL`),
  KEY `FK_BOARD_TO_COMMENT` (`BOARD_NUMBER`),
  CONSTRAINT `FK_BOARD_TO_COMMENT` FOREIGN KEY (`BOARD_NUMBER`) REFERENCES `board` (`BOARD_NUMBER`),
  CONSTRAINT `FK_USER_TO_COMMENT` FOREIGN KEY (`USER_EMAIL`) REFERENCES `user` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='댓글 테이블';

-- 테이블 데이터 board.comment:~13 rows (대략적) 내보내기
INSERT INTO `comment` (`COMMENT_NUMBER`, `CONTENT`, `WRITE_DATETIME`, `USER_EMAIL`, `BOARD_NUMBER`) VALUES
	(7, '1', '2024-08-27 21:42:46', 'email3@email3.com', 25),
	(8, '2', '2024-08-27 21:42:47', 'email3@email3.com', 25),
	(9, '3', '2024-08-27 21:42:49', 'email3@email3.com', 25),
	(10, '4', '2024-08-27 21:42:54', 'email3@email3.com', 25),
	(11, '1', '2024-09-03 20:57:07', 'email3@email3.com', 10),
	(12, '2', '2024-09-03 20:57:09', 'email3@email3.com', 10),
	(13, '3', '2024-09-03 20:57:12', 'email3@email3.com', 10),
	(14, '4', '2024-09-03 20:57:13', 'email3@email3.com', 10),
	(15, '5', '2024-09-03 20:57:16', 'email3@email3.com', 10),
	(16, '6', '2024-09-03 20:57:38', 'email3@email3.com', 10),
	(17, '7', '2024-09-03 20:57:40', 'email3@email3.com', 10),
	(18, '8', '2024-09-03 20:57:47', 'email3@email3.com', 10),
	(19, '5', '2024-09-03 21:13:58', 'email3@email3.com', 25);

-- 테이블 board.favorite 구조 내보내기
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE IF NOT EXISTS `favorite` (
  `USER_EMAIL` varchar(50) NOT NULL COMMENT '사용자 이메일',
  `BOARD_NUMBER` int NOT NULL COMMENT '게시물 번호',
  PRIMARY KEY (`USER_EMAIL`,`BOARD_NUMBER`),
  KEY `FK_BOARD_TO_FAVORITE` (`BOARD_NUMBER`),
  CONSTRAINT `FK_BOARD_TO_FAVORITE` FOREIGN KEY (`BOARD_NUMBER`) REFERENCES `board` (`BOARD_NUMBER`),
  CONSTRAINT `FK_USER_TO_FAVORITE` FOREIGN KEY (`USER_EMAIL`) REFERENCES `user` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='좋아요 테이블';

-- 테이블 데이터 board.favorite:~2 rows (대략적) 내보내기
INSERT INTO `favorite` (`USER_EMAIL`, `BOARD_NUMBER`) VALUES
	('email3@email3.com', 10),
	('email3@email3.com', 25);

-- 테이블 board.image 구조 내보내기
DROP TABLE IF EXISTS `image`;
CREATE TABLE IF NOT EXISTS `image` (
  `BOARD_NUMBER` int NOT NULL COMMENT '게시물 번호',
  `IMAGE` text NOT NULL COMMENT '게시물 이미지 URL',
  `sequence` int NOT NULL AUTO_INCREMENT COMMENT '이미지 번호',
  PRIMARY KEY (`sequence`),
  KEY `FK_BOARD_TO_IMAGE` (`BOARD_NUMBER`),
  CONSTRAINT `FK_BOARD_TO_IMAGE` FOREIGN KEY (`BOARD_NUMBER`) REFERENCES `board` (`BOARD_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='게시물 이미지 테이블';

-- 테이블 데이터 board.image:~7 rows (대략적) 내보내기
INSERT INTO `image` (`BOARD_NUMBER`, `IMAGE`, `sequence`) VALUES
	(24, 'http://localhost:4000/file/13aee621-012a-4920-bddd-c3011a9977fc.png', 3),
	(24, 'http://localhost:4000/file/97b5b487-38d1-4c2a-b04e-38c897a98d1f.png', 4),
	(26, 'http://localhost:4000/file/9cfa030e-4665-4053-89db-695e1025d05d.png', 7),
	(26, 'http://localhost:4000/file/961f56e7-bb8b-46b2-b97f-b82cda597338.png', 8),
	(26, 'http://localhost:4000/file/fb6b4a7f-6a4e-49be-8cd4-117ef5c20c86.png', 9),
	(28, 'http://localhost:4000/file/71fa1c41-a433-4ded-8f8f-8ff31b063b79.png', 10),
	(25, 'http://localhost:4000/file/5834465c-03cc-4d2d-8c88-1ae20842325f.png', 12);

-- 테이블 board.search_log 구조 내보내기
DROP TABLE IF EXISTS `search_log`;
CREATE TABLE IF NOT EXISTS `search_log` (
  `SEQUENCE` int NOT NULL AUTO_INCREMENT COMMENT '시퀀스',
  `SEARCH_WORD` text NOT NULL COMMENT '검색어',
  `RELATION_WORD` text COMMENT '관련 검색어',
  `RELATION` tinyint(1) NOT NULL COMMENT '관련 검색어 여부',
  PRIMARY KEY (`SEQUENCE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='검색 기록 테이블';

-- 테이블 데이터 board.search_log:~0 rows (대략적) 내보내기

-- 테이블 board.user 구조 내보내기
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `EMAIL` varchar(50) NOT NULL COMMENT '사용자 이메일',
  `PASSWORD` varchar(100) NOT NULL COMMENT '사용자 비밀번호',
  `NICKNAME` varchar(20) NOT NULL COMMENT '사용자 닉네임',
  `TEL_NUMBER` varchar(15) NOT NULL COMMENT '사용자 휴대전화번호',
  `ADDRESS` text NOT NULL COMMENT '사용자 주소',
  `ADDRESS_DETAIL` text COMMENT '사용자 상세 주소',
  `PROFILE_IMAGE` text COMMENT '사용자 프로필 사진 URL',
  `agreed_personal` tinyint(1) DEFAULT NULL COMMENT '개인정보동의여부',
  PRIMARY KEY (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사용자 테이블';

-- 테이블 데이터 board.user:~3 rows (대략적) 내보내기
INSERT INTO `user` (`EMAIL`, `PASSWORD`, `NICKNAME`, `TEL_NUMBER`, `ADDRESS`, `ADDRESS_DETAIL`, `PROFILE_IMAGE`, `agreed_personal`) VALUES
	('email@com.com', '$2a$10$BT5LRjybaMXB/i.1tzE9k.9jxVGev1Be50/MCxnIjTrIKNdsI2kJO', '1234', '123412341234', '1234', '', NULL, 1),
	('email@email.com', 'Pissw0rd', '수정 닉네임', '01012345678', '부산광역시 부산진구', '롯데백화점', 'ㅕ기2', NULL),
	('email3@email3.com', '$2a$10$S44bAM1dT9vxMTsyFwzCM.AbDcbLyqgLZPW6wLBvINfLsTxFdJt8.', '닉네임ㅋㅋㅋㅋ', '01012349876', '경기 성남시 분당구 대왕판교로 477', '1동', NULL, 1),
	('test@email.com', '$2a$10$WhzGZmZaDobzVvnrmt9LuuBvCbU7rTdm2esMerPZaEE4NXnny5czi', 'nick', '01012341234', '대구 수성구 고산로 123', '1234', NULL, 1);

-- 임시 테이블을 제거하고 최종 VIEW 구조를 생성
DROP TABLE IF EXISTS `board_list_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `board`.`board_list_view` AS select `b`.`BOARD_NUMBER` AS `board_number`,`b`.`TITLE` AS `title`,`b`.`CONTENT` AS `content`,`i`.`image` AS `title_image`,`b`.`VIEW_COUNT` AS `view_count`,`b`.`FAVORITE_COUNT` AS `favorite_count`,`b`.`COMMENT_COUNT` AS `comment_count`,`b`.`WRITE_DATETIME` AS `write_datetime`,`b`.`WRITER_EMAIL` AS `writer_email`,`u`.`NICKNAME` AS `writer_nickname`,`u`.`PROFILE_IMAGE` AS `writer_profile_image` from ((`board`.`board` `b` join `board`.`user` `u` on((`b`.`WRITER_EMAIL` = `u`.`EMAIL`))) left join (select `board`.`image`.`BOARD_NUMBER` AS `board_number`,any_value(`board`.`image`.`IMAGE`) AS `image` from `board`.`image` group by `board`.`image`.`BOARD_NUMBER`) `i` on((`b`.`BOARD_NUMBER` = `i`.`board_number`)));

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

# SPRING-REACT-MYSQL
2024-08-02 npm install axios
2024-08-03 npm i react-daum-postcode

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
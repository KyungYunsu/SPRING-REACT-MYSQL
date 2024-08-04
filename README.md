# SPRING-REACT-MYSQL
2024-08-02 npm install axios
2024-08-03 npm i react-daum-postcode

# query
DCL 
GRANT SELECT, INSERT, UPDATE, DELETE 
ON board.* 
TO 'developer'@'*';

DDL
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
-- 회원가입
INSERT INTO `user` VALUES ('email@email.com', 'P!ssw0rd', 'nickname', '010123456789', '부산광역시 부산진구', '롯데백화점', null);

-- 로그인
SELECT * FROM `user` WHERE `EMAIL` = 'email@email.com';


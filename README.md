book sql 
use book;

-- 사용자 테이블
CREATE TABLE users (
    user_id 			VARCHAR(50) AUTO_INCREMENT PRIMARY KEY COMMENT "사용자 아이디",
    user_name 			VARCHAR(50) NOT NULL UNIQUE COMMENT "사용자 이름",
    user_pwd 			VARCHAR(255) NOT NULL COMMENT "사용자 비밀번호",
    user_role 			ENUM('ADMIN', 'USER') NOT NULL COMMENT "관리자, 사용자 역할",
    user_created_at 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "사용자 생성일",
    user_updated_at 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT "사용자 수정일"
) ENGINE = INNODB;


-- 도서 테이블
CREATE TABLE books (
    book_code 			INT AUTO_INCREMENT PRIMARY KEY COMMENT "도서 아이디",
    book_title 			VARCHAR(255) NOT NULL COMMENT "도서 제목",
    book_author 		VARCHAR(255) NOT NULL COMMENT "도서 저자",
    book_publisher 		VARCHAR(255) NOT NULL COMMENT "도서 출판사",
    book_genre 			VARCHAR(100) NOT NULL COMMENT "도서 장르",
    book_status 		ENUM('AVAILABLE', 'RESERVED', 'BORROWED') NOT NULL COMMENT "도서 상태",
    book_created_at 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "도서 등록일",
    book_updated_at 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT "도서 수정일"
) ENGINE = INNODB;

-- 대여 및 반납 기록 테이블
CREATE TABLE borrow_records (
    borrow_code 		INT AUTO_INCREMENT PRIMARY KEY COMMENT "고유한 대여코드 ",
    user_id 			INT COMMENT "대여자 아이디",
    book_code 			INT COMMENT "대여한 도서 코드",
    borrow_date 		DATE NOT NULL COMMENT "대여일",
    due_date 			DATE NOT NULL COMMENT "반납 예정일",
    return_date 		DATE COMMENT "실제 반납일",
    br_created_at 			TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "도서요청 등록일",
    br_updated_at 			TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT "도서요청 수정일",
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_code) REFERENCES books(book_code)
) ENGINE = INNODB;

-- 도서 요청 테이블
CREATE TABLE requests (
    request_id 				INT AUTO_INCREMENT PRIMARY KEY COMMENT "고유한 요청 아이디",
    user_id 				INT COMMENT "요청자 아이디",
    book_title 				VARCHAR(255) NOT NULL COMMENT "요청 도서 제목",
    book_author 			VARCHAR(255) NOT NULL COMMENT "요청 도서 작가",
    book_publisher 			VARCHAR(255) NOT NULL COMMENT "요청 도서 출판사",
    requests_status 		ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL COMMENT "요청 상태",
    created_at 				TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "도서 요청일",
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE = INNODB;

-- 베스트셀러 테이블
CREATE TABLE best_sellers (
    best_seller_id 		INT AUTO_INCREMENT PRIMARY KEY COMMENT "고유한 베스트셀러 아이디",
    book_code 			INT COMMENT "도서 코드",
    borrow_count 		INT NOT NULL COMMENT "대여 횟수",
    period 				ENUM('WEEK', 'MONTH', 'YEAR') NOT NULL COMMENT "기간(주/월/연)",
    created_at 			TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "생성일",
    FOREIGN KEY (book_code) REFERENCES books(book_code)
) ENGINE = INNODB;

--  카테고리 테이블
CREATE TABLE categories (
    category_id 			INT AUTO_INCREMENT PRIMARY KEY COMMENT "고유한 카테고리 아이디",
    category_name 			VARCHAR(100) NOT NULL COMMENT "카테고리 이름",
    created_at 				TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "생성일"
) ENGINE = INNODB;

--  도서-카테고리 연결 테이블
CREATE TABLE book_category (
    book_code 		INT COMMENT "고유한 도서코드",
    category_id 	INT COMMENT "카테고리 아이디",
    created_at 		TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT "생성일",
    PRIMARY KEY (book_code, category_id),
    FOREIGN KEY (book_code) REFERENCES books(book_code),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
) ENGINE = INNODB;

SELECT * FROM users;
SELECT * FROM books;
SELECT * FROM borrow_records;
SELECT * FROM requests;
SELECT * FROM best_sellers;
SELECT * FROM categories;
SELECT * FROM book_category;

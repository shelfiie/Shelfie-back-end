CREATE TABLE tb_badge (
                          badge_id VARCHAR(255) NOT NULL,
                          user_id VARCHAR(255)  NOT NULL,
                          badge_image VARCHAR(255) NOT NULL,
                          badge_name VARCHAR(255) NOT NULL,
                          badge_description TEXT NOT NULL,
                          badge_created_at TIMESTAMP(6) NOT NULL,
                          PRIMARY KEY (badge_id)
);

CREATE TABLE tb_book (
                         book_id VARCHAR(255) NOT NULL,
                         book_google_id VARCHAR(255) NOT NULL UNIQUE,
                         book_isbn_10 VARCHAR(255) NOT NULL UNIQUE,
                         book_isbn_13 VARCHAR(255) NOT NULL UNIQUE,
                         book_title VARCHAR(255) NOT NULL,
                         book_created_at TIMESTAMP(6) NOT NULL,
                         PRIMARY KEY (book_id)
);


CREATE TABLE tb_friend (
                           friend_id VARCHAR(255) NOT NULL,
                           user_id_a BIGINT NOT NULL,
                           user_id_b BIGINT NOT NULL,
                           friendship_request_status VARCHAR(255) NOT NULL CHECK (friendship_request_status IN ('ACEITO','PENDENTE','RECUSADO')),
                           friendship_request_accepted TIMESTAMP(6),
                           friendship_request_date TIMESTAMP(6) NOT NULL,
                           PRIMARY KEY (friend_id)
);

CREATE TABLE tb_like (
                         like_id VARCHAR(255) NOT NULL,
                         review_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         like_created_at TIMESTAMP(6) NOT NULL,
                         PRIMARY KEY (like_id)
);

CREATE TABLE tb_my_books (
                             my_books_id VARCHAR(255) NOT NULL,
                             book_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             my_books_status VARCHAR(255) NOT NULL CHECK (my_books_status IN ('LIDO','LENDO','QUERO_LER','ABANDONADO')),
                             my_books_created_at TIMESTAMP(6) NOT NULL,
                             my_books_enable BOOLEAN NOT NULL,
                             PRIMARY KEY (my_books_id)
);

CREATE TABLE tb_reading_progress (
                                     reading_progress_id VARCHAR(255) NOT NULL,
                                     my_books_id BIGINT NOT NULL,
                                     reading_progress_page INTEGER NOT NULL,
                                     reading_progress_commentary TEXT,
                                     reading_progress_created_at TIMESTAMP(6) NOT NULL,
                                     PRIMARY KEY (reading_progress_id)
);

CREATE TABLE tb_report (
                           report_id VARCHAR(255) NOT NULL,
                           user_id BIGINT NOT NULL,
                           review_id BIGINT NOT NULL,
                           report_status VARCHAR(255) NOT NULL CHECK (report_status IN ('PENDENTE','RESOLVIDO','RECUSADO')),
                           report_created_at TIMESTAMP(6) NOT NULL,
                           PRIMARY KEY (report_id)
);

CREATE TABLE tb_review (
                           review_id VARCHAR(255) NOT NULL,
                           my_books_id BIGINT NOT NULL,
                           review_rating FLOAT4,
                           review_review TEXT,
                           review_enabled BOOLEAN,
                           review_created_at TIMESTAMP(6) NOT NULL,
                           PRIMARY KEY (review_id)
);

CREATE TABLE tb_user (
                         user_id VARCHAR(255) NOT NULL,
                         user_name VARCHAR(255) NOT NULL,
                         user_username VARCHAR(255) NOT NULL UNIQUE,
                         user_email VARCHAR(255) NOT NULL UNIQUE,
                         user_password VARCHAR(255) NOT NULL,
                         user_image VARCHAR(255),
                         my_books_enable BOOLEAN NOT NULL,
                         user_role VARCHAR(255) NOT NULL CHECK (user_role IN ('ROLE_ADMIN','ROLE_READER')),
                         user_created_at TIMESTAMP(6) NOT NULL,
                         PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS tb_badge ADD CONSTRAINT tb_badge_fk1 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_friend ADD CONSTRAINT tb_friend_fk1 FOREIGN KEY (user_id_a) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_friend ADD CONSTRAINT tb_friend_fk2 FOREIGN KEY (user_id_b) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_like ADD CONSTRAINT tb_like_fk1 FOREIGN KEY (review_id) REFERENCES tb_review;
ALTER TABLE IF EXISTS tb_like ADD CONSTRAINT tb_like_fk2 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_my_books ADD CONSTRAINT tb_my_books_fk1 FOREIGN KEY (book_id) REFERENCES tb_book;
ALTER TABLE IF EXISTS tb_my_books ADD CONSTRAINT tb_my_books_fk2 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_reading_progress ADD CONSTRAINT tb_reading_progress_fk1 FOREIGN KEY (my_books_id) REFERENCES tb_my_books;
ALTER TABLE IF EXISTS tb_report ADD CONSTRAINT tb_report_fk1 FOREIGN KEY (review_id) REFERENCES tb_review;
ALTER TABLE IF EXISTS tb_report ADD CONSTRAINT tb_report_fk2 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_review ADD CONSTRAINT tb_review_fk1 FOREIGN KEY (my_books_id) REFERENCES tb_my_books;

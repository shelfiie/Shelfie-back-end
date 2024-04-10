CREATE TABLE tb_book (
                         book_id UUID NOT NULL,
                         book_google_id VARCHAR(255) NOT NULL UNIQUE,
                         book_isbn_10 VARCHAR(255) NOT NULL UNIQUE,
                         book_isbn_13 VARCHAR(255) NOT NULL UNIQUE,
                         book_title VARCHAR(255) NOT NULL,
                         book_created_at TIMESTAMP(6) NOT NULL,
                         book_pages INTEGER NOT NULL,
                         PRIMARY KEY (book_id)
);
ALTER TABLE IF EXISTS tb_review ADD CONSTRAINT tb_my_books_fk2 FOREIGN KEY (book_id) REFERENCES tb_book;

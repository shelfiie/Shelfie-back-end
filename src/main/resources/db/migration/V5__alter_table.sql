ALTER TABLE IF EXISTS tb_my_books ADD CONSTRAINT tb_my_books_fk1 FOREIGN KEY (book_id) REFERENCES tb_book;
ALTER TABLE IF EXISTS tb_my_books ADD CONSTRAINT tb_my_books_fk2 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE IF EXISTS tb_reading_progress ADD CONSTRAINT tb_reading_progress_fk1 FOREIGN KEY (my_books_id) REFERENCES tb_my_books;
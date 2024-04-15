CREATE TABLE tb_reading_progress (
                                     reading_progress_id UUID NOT NULL,
                                     my_books_id UUID NOT NULL,
                                     reading_progress_page INTEGER NOT NULL,
                                     reading_progress_commentary TEXT,
                                     reading_progress_created_at TIMESTAMP(6) NOT NULL,
                                     PRIMARY KEY (reading_progress_id)
);


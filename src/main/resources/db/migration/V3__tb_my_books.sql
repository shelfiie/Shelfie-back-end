CREATE TABLE tb_my_books (
                             my_books_id UUID NOT NULL,
                             book_id UUID NOT NULL,
                             user_id UUID NOT NULL,
                             my_books_status VARCHAR(255) NOT NULL CHECK (my_books_status IN ('LIDO','LENDO','QUERO_LER','ABANDONADO')),
                             my_books_created_at TIMESTAMP(6) NOT NULL,
                             my_books_enabled BOOLEAN NOT NULL,
                             my_books_favorite BOOLEAN NOT NULL,
                             UNIQUE(book_id, user_id),
                             PRIMARY KEY (my_books_id)
);


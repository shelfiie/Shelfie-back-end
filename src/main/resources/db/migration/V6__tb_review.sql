CREATE TABLE tb_review (
               review_id UUID NOT NULL,
               my_books_id UUID NOT NULL,
               review_rating FLOAT4,
               review_review TEXT,
               review_created_at TIMESTAMP(6) NOT NULL,
               review_enabled BOOLEAN NOT NULL,
               PRIMARY KEY (review_id)
);
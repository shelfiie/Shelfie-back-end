CREATE TABLE tb_like (
                     like_id UUID NOT NULL,
                     review_id UUID NOT NULL,
                     user_id UUID NOT NULL,
                     like_created_at TIMESTAMP(6) NOT NULL,
                     review_enabled BOOLEAN NOT NULL,
                     PRIMARY KEY (like_id)
);



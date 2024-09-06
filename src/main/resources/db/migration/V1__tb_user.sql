CREATE TABLE tb_user (
                         user_id UUID NOT NULL,
                         user_name VARCHAR(255) NOT NULL,
                         user_nickname VARCHAR(255) NOT NULL UNIQUE,
                         user_email VARCHAR(255) NOT NULL UNIQUE,
                         user_password VARCHAR(255) NOT NULL,
                         user_image VARCHAR(255),
                         user_enabled BOOLEAN NOT NULL,
                         user_role VARCHAR(255) NOT NULL CHECK (user_role IN ('ROLE_ADMIN','ROLE_READER')),
                         user_created_at TIMESTAMP(6) NOT NULL,
                         PRIMARY KEY (user_id)
);


CREATE TABLE tb_roles (
    role_id BIGINT NOT NULL,
    role_user VARCHAR(255) NOT NULL CHECK (role_user IN ('ADMIN','READER')),
    role_created_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (role_id)
);

ALTER TABLE IF EXISTS tb_user ADD COLUMN role_id BIGINT NOT NULL;
ALTER TABLE IF EXISTS tb_user ADD CONSTRAINT tb_user_fk1 FOREIGN KEY (role_id) REFERENCES tb_roles;

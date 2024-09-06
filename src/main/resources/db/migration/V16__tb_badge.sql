CREATE TABLE tb_badge (
                           badge_id UUID NOT NULL,
                           badge_name VARCHAR(255) NOT NULL,
                           badge_image VARCHAR(1000) NOT NULL,
                           badge_description VARCHAR(1000),
                           badge_updated_at TIMESTAMP(6) NOT NULL,
                           badge_created_at TIMESTAMP(6) NOT NULL,
                           PRIMARY KEY (badge_id)
);

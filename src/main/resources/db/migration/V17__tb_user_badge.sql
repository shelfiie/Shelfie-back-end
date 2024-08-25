CREATE TABLE tb_user_badge (
                               user_badge_id UUID NOT NULL,
                               user_id UUID NOT NULL,
                               badge_id UUID NOT NULL,
                               user_badge_paginometer VARCHAR(255) NOT NULL CHECK (user_badge_paginometer IN ('NONE','HUNDRED_PAGES','THOUSAND_PAGES','LOT_OF_PAGES')),
                               user_badge_book VARCHAR(255) NOT NULL CHECK (user_badge_book IN ('NONE','BEGINNER','INTERMEDIATE','ADVANCED','EXPERT')),
                               user_badge_read_progression VARCHAR(255) NOT NULL CHECK (user_badge_read_progression IN ('NONE','READER','BOOKWORM','BIBLIOPHILE','BIBLIOMANIAC')),
                               user_badge_created_at TIMESTAMP(6) NOT NULL,
                               user_badge_updated_at TIMESTAMP(6) NOT NULL,
                               PRIMARY KEY (user_badge_id)
);
ALTER TABLE tb_user ADD COLUMN user_badge_id UUID;
ALTER TABLE tb_user_badge ADD CONSTRAINT tb_user_badge_fk1 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE tb_user_badge ADD CONSTRAINT tb_user_badge_fk2 FOREIGN KEY (badge_id) REFERENCES tb_badge;
ALTER TABLE tb_user ADD CONSTRAINT tb_user_badge_fk1 FOREIGN KEY (user_badge_id) REFERENCES tb_user_badge;
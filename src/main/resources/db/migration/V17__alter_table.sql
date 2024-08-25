ALTER TABLE IF EXISTS tb_user ADD COLUMN
    user_badge_paginometer VARCHAR(255) NOT NULL CHECK (user_badge_paginometer IN ('NONE','HUNDRED_PAGES','THOUSAND_PAGES','LOT_OF_PAGES')
    );

ALTER TABLE IF EXISTS tb_user ADD COLUMN
    user_badge_book VARCHAR(255) NOT NULL CHECK (user_badge_book IN ('NONE','READER','BOOKWORM','BIBLIOPHILE','BIBLIOMANIAC')
    );

ALTER TABLE IF EXISTS tb_user ADD COLUMN
    user_badge_review VARCHAR(255) NOT NULL CHECK (user_badge_review IN ('NONE','NOVICE','CRITIC','EXPERT','CONNOISSEUR')
    );
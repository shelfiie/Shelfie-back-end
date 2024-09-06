CREATE TABLE tb_report (
                           report_id UUID NOT NULL,
                           user_id UUID NOT NULL,
                           review_id UUID NOT NULL,
                           report_status VARCHAR(255) NOT NULL CHECK (report_status IN ('PENDENTE','RESOLVIDO','RECUSADO')),
                           report_created_at TIMESTAMP(6) NOT NULL,
                           PRIMARY KEY (report_id)
);

ALTER TABLE tb_report ADD CONSTRAINT tb_report_fk1 FOREIGN KEY (user_id) REFERENCES tb_user;
ALTER TABLE tb_report ADD CONSTRAINT tb_report_fk2 FOREIGN KEY (review_id) REFERENCES tb_review;
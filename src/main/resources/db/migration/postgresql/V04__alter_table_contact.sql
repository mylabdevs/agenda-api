ALTER TABLE contact ADD COLUMN user_id BIGINT NOT NULL;
ALTER TABLE contact ADD CONSTRAINT fk_contact_user_id FOREIGN KEY (user_id) references users(id);
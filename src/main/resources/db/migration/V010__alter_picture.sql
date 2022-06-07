BEGIN;

ALTER TABLE IF EXISTS player RENAME COLUMN full_name TO picture;

ALTER TABLE IF EXISTS player DROP COLUMN profile_picture;

END;
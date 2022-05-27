BEGIN;

ALTER TABLE IF EXISTS public.player ALTER COLUMN password_hash TYPE varchar(200);

END;
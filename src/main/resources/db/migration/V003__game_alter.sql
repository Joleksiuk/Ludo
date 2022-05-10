BEGIN;


ALTER TABLE IF EXISTS public.game
ALTER COLUMN name TYPE character varying;

END;
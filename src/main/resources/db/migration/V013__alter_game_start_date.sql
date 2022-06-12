BEGIN;

ALTER TABLE IF EXISTS public.game
ALTER COLUMN start_date TYPE timestamp ;

END;
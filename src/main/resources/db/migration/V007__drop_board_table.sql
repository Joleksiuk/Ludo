BEGIN;

ALTER TABLE IF EXISTS public.game DROP COLUMN IF EXISTS board_id;

DROP TABLE IF EXISTS public.board;

ALTER TABLE IF EXISTS public.game ADD COLUMN IF NOT EXISTS board_code varchar(50) NOT NULL DEFAULT 'standard';

END;
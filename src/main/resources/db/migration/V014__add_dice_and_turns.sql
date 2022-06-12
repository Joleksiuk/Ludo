BEGIN;

ALTER TABLE IF EXISTS public.game
    ADD COLUMN IF NOT EXISTS current_dice_value smallint NOT NULL DEFAULT 1;

ALTER TABLE IF EXISTS public.player_game
    ADD COLUMN IF NOT EXISTS next_player_id integer;

ALTER TABLE IF EXISTS public.player_game
    ADD FOREIGN KEY (next_player_id)
        REFERENCES public.player (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

END;
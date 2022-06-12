BEGIN;

ALTER TABLE IF EXISTS public.game
    ADD COLUMN IF NOT EXISTS turn_player_id integer;

UPDATE public.game
    SET turn_player_id = player_game.player_id
        FROM public.player_game
        WHERE player_game.game_id = game.id;

DELETE FROM public.game
    WHERE game.turn_player_id IS NULL;

ALTER TABLE IF EXISTS public.game
    ALTER COLUMN turn_player_id SET NOT NULL;

ALTER TABLE IF EXISTS public.game
    ADD FOREIGN KEY (turn_player_id)
        REFERENCES public.player (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

END;
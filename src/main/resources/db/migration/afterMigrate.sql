BEGIN;

-----------------------------------------
-- TEST DATA FOR player
-----------------------------------------
INSERT INTO public.player(id, nickname)
VALUES (1, 'ann'), (2,'bea'), (3,'charlie'), (4,'dominic')
ON CONFLICT DO NOTHING;

-----------------------------------------
-- TEST DATA FOR game
-----------------------------------------
INSERT INTO public.game(id, name, board_code, turn_player_id)
VALUES (1, 'test game on standard board', 'standard', 1)
ON CONFLICT DO NOTHING;

INSERT INTO public.game(id, name, board_code, turn_player_id)
VALUES (2, 'test game on small board', 'small', 1)
ON CONFLICT DO NOTHING;

-----------------------------------------
-- TEST DATA FOR player_game
-----------------------------------------
INSERT INTO public.player_game(player_id, game_id, player_colour, next_player_id)
VALUES (1, 1, 'red', 2), (2, 1, 'green', 3), (3, 1, 'blue', 4), (4, 1, 'yellow', 1),
       (1, 2, 'red', 2), (2, 2, 'green', 1)
ON CONFLICT DO NOTHING;

-----------------------------------------
-- TEST DATA FOR pawn
-----------------------------------------
INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 1, false, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 2, false, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 3, false, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 4, true, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 1, false, 0, id, 2 FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 2, false, 0, id, 2 FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 3, false, 0, id, 2  FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, player_id, game_id)
SELECT 4, true, 0, id, 2 FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;
END;

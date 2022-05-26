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
INSERT INTO public.game(id, name, board_code)
VALUES (1, 'test game on standard board', 'standard')
ON CONFLICT DO NOTHING;

INSERT INTO public.game(id, name, board_code)
VALUES (2, 'test game on small board', 'small')
ON CONFLICT DO NOTHING;

-----------------------------------------
-- TEST DATA FOR player_game
-----------------------------------------
INSERT INTO public.player_game(user_id, game_id, player_colour)
VALUES (1, 1, 'red'), (2, 1, 'green'), (3,1,'blue'), (4,1,'yellow'),
       (1, 2, 'red'), (2, 2, 'green')
ON CONFLICT DO NOTHING;

-----------------------------------------
-- TEST DATA FOR pawn
-----------------------------------------
INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 1, false, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 2, false, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 3, false, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 4, true, 0, id, 1 FROM player WHERE player.id IN (1, 2, 3, 4)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 1, false, 0, id, 2 FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 2, false, 0, id, 2 FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 3, false, 0, id, 2  FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO public.pawn(number, did_start, progress, user_id, game_id)
SELECT 4, true, 0, id, 2 FROM player WHERE player.id IN (1, 2)
ON CONFLICT DO NOTHING;
END;

BEGIN;

ALTER TABLE IF EXISTS public.player_friend
    RENAME COLUMN first_user_id TO first_player_id;

ALTER TABLE IF EXISTS public.player_friend
    RENAME COLUMN second_user_id TO second_player_id;

ALTER TABLE IF EXISTS public.player_friend_invite
    RENAME COLUMN inviting_user_id TO inviting_player_id;

ALTER TABLE IF EXISTS public.player_friend_invite
    RENAME COLUMN invited_user_id TO invited_player_id;

ALTER TABLE IF EXISTS public.player_game
    RENAME COLUMN user_id TO player_id;

ALTER TABLE IF EXISTS public.player_game_invite
    RENAME COLUMN inviting_user_id TO inviting_player_id;

ALTER TABLE IF EXISTS public.player_game_invite
    RENAME COLUMN invited_user_id TO invited_player_id;

ALTER TABLE IF EXISTS public.game
    RENAME COLUMN winner_user_id TO winner_player_id;

ALTER TABLE IF EXISTS public.player
    ADD CONSTRAINT player_nickname_unique UNIQUE (nickname);

ALTER TABLE IF EXISTS public.pawn
    RENAME COLUMN user_id TO player_id;

END;
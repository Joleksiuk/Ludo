BEGIN;

ALTER TABLE public."user"
    RENAME TO player;

ALTER TABLE user_friend
    RENAME TO player_friend;

ALTER TABLE user_game_invite
    RENAME TO player_game_invite;

ALTER TABLE user_game
    RENAME TO player_game;

ALTER TABLE user_friend_invite
    RENAME TO player_friend_invite;

END;
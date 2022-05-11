BEGIN;


CREATE TABLE IF NOT EXISTS public.game
(
    id bigint NOT NULL,
    name character varying[] NOT NULL,
    PRIMARY KEY (id)
);
END;
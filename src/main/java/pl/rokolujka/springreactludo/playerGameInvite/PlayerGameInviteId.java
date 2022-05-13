package pl.rokolujka.springreactludo.playerGameInvite;

import java.io.Serializable;

public class PlayerGameInviteId implements Serializable {
    private Integer inviting_user_id;
    private Integer invited_user_id;
    private Integer game_id;
}

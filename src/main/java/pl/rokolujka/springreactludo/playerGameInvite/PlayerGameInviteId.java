package pl.rokolujka.springreactludo.playerGameInvite;

import java.io.Serializable;

public class PlayerGameInviteId implements Serializable {
    private Integer invitingUserId;
    private Integer invitedUserId;
    private Integer gameId;
}

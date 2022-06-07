package pl.rokolujka.springreactludo.playerGameInvite;

import java.io.Serializable;

public class PlayerGameInviteId implements Serializable {
    private Integer invitingPlayerId;
    private Integer invitedPlayerId;
    private Integer gameId;
}

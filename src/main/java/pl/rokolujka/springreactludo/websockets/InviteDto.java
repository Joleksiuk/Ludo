package pl.rokolujka.springreactludo.websockets;

import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;

public class InviteDto {

    private String message;
    private PlayerFriendInvite friendInvite;

    public PlayerFriendInvite getfriendInvite() {
        return friendInvite;
    }

    public void setFriendInvite(PlayerFriendInvite friendInvite) {
        this.friendInvite = friendInvite;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


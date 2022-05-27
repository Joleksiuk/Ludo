import { PlayerFriendInvite } from "../../data-interfaces";

export class FriendInviteDto {
    playerFriendInvite: PlayerFriendInvite;
    message: String;

    constructor(playerFriendInvite, message){
        this.playerFriendInvite = playerFriendInvite;
        this.message = message;
    }
}

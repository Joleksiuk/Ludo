import { PlayerFriendInvite } from "../../../data-interfaces";



export class FriendInviteDto {
    playerFriendInvite: PlayerFriendInvite;
    message: String;

    constructor(playerFriendInvite: PlayerFriendInvite, message: String){
        this.playerFriendInvite = playerFriendInvite;
        this.message = message;
    }
}
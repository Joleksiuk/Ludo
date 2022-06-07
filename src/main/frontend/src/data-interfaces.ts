export interface Game {
    id: number,
    name: string,
    boardCode: string
}

export interface Board {
    code: string,
    name: string,
    maxPlayers: number,
}

export interface Player{
    id: number,
    nickname: string,
    fullName: string,
}

export interface PlayerFriend{
    firstUserId: number,
    secondUserId: number,
}

export interface  PlayerGameInvite{
    invitingUserId: number,
    invitedUserId: number,
    gameId: number,
}

export class PlayerFriendInvite{
    invitingUserId:number;
    invitedUserId: number;
    PlayerFriendInvite(invitingUserId:number, invitedUserId: number){
        this.invitedUserId=invitedUserId;
        this.invitingUserId=invitingUserId;
    }
}

export interface Field {
    color: string,
    empty: boolean,
    id: number
}

export interface PawnInfo {
    color: string,
    fieldId: number,
}

export interface AuthState{
    nickname: string,
    accessToken: string,
}

export interface GameStatusMessage {
    diceValue: number
}
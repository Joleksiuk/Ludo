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

export interface PlayerFriendInvite{
    invitingUserId:number,
    invitedUserId: number,
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

export class chatMessageDto{

    user: any;
    message: any;

    constructor(user:any,message:any){
        this.user=user;
        this.message=message;
    }
}



export interface AuthState{
    nickname: string,
    accessToken: string,
}
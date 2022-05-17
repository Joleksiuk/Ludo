export interface Game {
    id: number,
    name: string, 
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
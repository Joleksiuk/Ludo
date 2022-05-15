export interface Game {
    id: number,
    name: string, 
}

export interface Player{
    id: number,
    nickname: string,
    full_name: string,
}

export interface PlayerFriend{
    first_user_id: number,
    second_user_id: number,
}

export interface  PlayerGameInvite{
    inviting_user_id: number,
    invited_user_id: number,
    game_id: number,
}

export interface PlayerFriendInvite{
    inviting_user_id:number,
    invited_user_id: number,
}
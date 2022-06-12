import { Color } from "@mui/material";

export interface Game {
    id: number,
    name: string,
    boardCode: string,
    diceThrownInTurn: boolean,
    turnPlayerId: number
}

export interface Board {
    code: string,
    name: string,
    maxPlayers: number,
}

export interface Player{
    id: number,
    nickname: string,
    picture: string,
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

export class PawnInfo {
    color: string;
    fieldId: number;
    number: number;

    constructor(color: string, fieldId: number, number: number) {
        this.color = color;
        this.fieldId = fieldId;
        this.number = number;
    }

    makeMove(move: BoardMove) {
        if (move.fromFieldId === this.fieldId) {
            this.fieldId = move.toFieldId;
        }
    }
}

export interface AuthState{
    nickname: string,
    accessToken: string,
}

export interface BoardMove {
    fromFieldId: number,
    toFieldId: number
}
import axios from '../axios'
import React, { useEffect, useState } from 'react'
import { Board, Game } from '../data-interfaces'

export default function AddGameForm() {
    const [boards, setBoards] = useState<Board[]>(new Array<Board>())

    const [gameName, setGameName] = useState<string>();
    const [gameBoardCode, setGameBoardCode] = useState<string>();

    const renderBoardField = (board: Board) => {
        return (
            <div key={board.code}>
                <label htmlFor={board.code}>{board.name}</label>
                <input type="radio" id={board.code} checked={board.code === gameBoardCode} onChange={() => setGameBoardCode(board.code)}></input>
            </div>
        )
    }
    useEffect(() => {
        axios.get<Board[]>('boards')
            .then(response => setBoards(response.data))
    }, [])

    const handleSubmit = (event: React.FormEvent): void => {
        event.preventDefault();
        axios.post<Game>('game', { name: gameName, boardCode: gameBoardCode });

    }

    return (
        <form onSubmit={event => handleSubmit(event)}>
            <div>
                <input type="text" placeholder="Game name" id="name" onChange={(e) => setGameName(e.target.value)}></input>
            </div>
            <div>
                {
                    boards.map(board => renderBoardField(board))
                }
            </div>
            <div>
                <button type="submit">Add game</button>
            </div>
        </form >
    )
}

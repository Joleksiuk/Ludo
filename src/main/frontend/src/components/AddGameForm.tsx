import axios from '../axios'
import React, { useEffect, useState } from 'react'
import { Board, Game } from '../data-interfaces'

export default function AddGameForm() {
    const [boards, setBoards] = useState<Board[]>(new Array<Board>())

    const [gameName, setGameName] = useState<string>();
    const [gameBoardId, setGameBoardId] = useState<number>();

    const renderBoardField = (board: Board) => {
        return (
            <div key={board.id}>
                <label htmlFor={board.id.toString()}>{board.name}</label>
                <input type="radio" id={board.id.toString()} checked={board.id === gameBoardId} onChange={() => setGameBoardId(board.id)}></input>
            </div>
        )
    }
    useEffect(() => {
        axios.get<Board[]>('boards')
            .then(response => setBoards(response.data))
    }, [])

    const handleSubmit = (event: React.FormEvent): void => {
        event.preventDefault();
        axios.post<Game>('game', { name: gameName, boardId: gameBoardId });

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

import React, {useState, useEffect} from 'react'
import axios from '../axios'
import { Game } from '../data-interfaces'

export default function GameList() {
    const [games, setGames] = useState(new Array<Game>())

    useEffect(() => {
        axios.get<Game[]>('games')
        .then(response => {
            setGames(response.data);
        })
        .catch(error => console.log(error))
    }, []);
  return (
    <div>
        <ul>
            {
                games.map(game => <li key={(game.id)}>{game.name}</li>)
            }
        </ul>
    </div>
  )
}
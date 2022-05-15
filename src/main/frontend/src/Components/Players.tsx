import React, {useState, useEffect} from 'react'
import axios from 'axios'
import {Player} from '../data-interfaces'

export default function PlayerList() {
    const [players, setplayer] = useState(new Array<Player>())

    useEffect(() => {
        axios.get<Player[]>('http://localhost:8080/players')
            .then(response => {
                setplayer(response.data);
            })
            .catch(error => console.log(error))
    }, []);
    return (
        <div>
            <ul>
                <h2>All players</h2>
                {
                    players.map(player=>{
                        return(
                            <div>{player.id}  --  {player.nickname}</div>
                        )
                    })
                }
            </ul>
        </div>
    )
}
import React, {useState, useEffect} from 'react'
import axios from '../axios'
import { PlayerGameInvite } from '../data-interfaces'

export default function PlayerGameInviteList() {
    const [playerGameInvites, setplayerGameInvites] = useState(new Array<PlayerGameInvite>())

    useEffect(() => {
        axios.get<PlayerGameInvite[]>('player_game_invites')
            .then(response => {
                setplayerGameInvites(response.data);
            })
            .catch(error => console.log(error))
    }, []);
    return (
        <div>
            <ul>
                <h3>All Game Invites</h3>
                {
                    playerGameInvites.map(playerGameInvite=>{
                        return(
                            <div>User {playerGameInvite.invited_user_id} invited {playerGameInvite.invited_user_id} To the game {playerGameInvite.game_id}</div>
                        )
                    })
                }
            </ul>
        </div>
    )
}
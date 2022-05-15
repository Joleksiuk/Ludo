import React, {useState, useEffect} from 'react'
import axios from 'axios'
import { PlayerFriendInvite } from '../data-interfaces'

export default function PlayerFriendInviteList() {
    const [playerFriendInvites, setplayerFriendInvites] = useState(new Array<PlayerFriendInvite>())

    useEffect(() => {
        axios.get<PlayerFriendInvite[]>('http://localhost:8080/player_friend_invites')
            .then(response => {
                setplayerFriendInvites(response.data);
            })
            .catch(error => console.log(error))
    }, []);
    return (
        <div>
            <ul>
                <h3>All Friend Invites</h3>
                {
                    playerFriendInvites.map(playerFriendInvite=>{
                        return(
                            <div>User {playerFriendInvite.invited_user_id}  invited  {playerFriendInvite.invited_user_id} </div>
                        )
                    })
                }
            </ul>
        </div>
    )
}
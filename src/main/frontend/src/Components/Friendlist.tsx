import React, {useState, useEffect} from 'react'
import axios from '../axios'
import { PlayerFriend } from '../data-interfaces'

export default function FriendList() {
  const [playersFriends, setplayerFriends] = useState(new Array<PlayerFriend>())

  useEffect(() => {
    axios.get<PlayerFriend[]>('player_friends')
        .then(response => {
            setplayerFriends(response.data);
        })
        .catch(error => console.log(error))
  }, []);
  return (
      <div>
        <ul>
            <h3>All Friendships</h3>
            {
            playersFriends.map(playerFriend=>{
                return(
                    <div>{playerFriend.first_user_id} Is Friend with {playerFriend.second_user_id} </div>
                )
            })
            }
        </ul>
      </div>
  )
}
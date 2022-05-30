import { Button, List, ListItem, ListItemText, ListSubheader, Box } from '@mui/material';
import React, {useState, useEffect, useRef, useContext} from 'react'
import axios from '../axios'
import { Player } from '../data-interfaces'
import authService from '../services/auth.service';
import SockJsClient from 'react-stomp';
import { WebSocketContext } from './WebSocketProvider';


export default function FriendablePlayersList() {

  const [friendablePlayersList, setfriendablePlayersList] = useState(new Array<Player>());
  const stompClientRef =useContext(WebSocketContext);


 
  useEffect(() => {
    const loggedPlayer = authService.getCurrentPlayer();
    const httpGetRequest ='players/suggest_friends/'+loggedPlayer.id; 
    console.log(httpGetRequest);
    axios.get<Player[]>(httpGetRequest)
        .then(response => {
            setfriendablePlayersList(response.data);
        })
        .catch(error => console.log(error))
  }, []);

  useEffect(()=>{
    
  },[friendablePlayersList]);

  const handleSendFriendRequest=(event, suggestedPlayer: Player)=>{
    friendablePlayersList.splice(friendablePlayersList.indexOf(suggestedPlayer),1);
    const msg = JSON.stringify({
                        invitingPlayerId: authService.getCurrentPlayer().id,
                        invitedPlayerId: suggestedPlayer.id
                    });
    stompClientRef.current.sendMessage('/topic/javainuse', msg);
  }

  const suggestedPlayerFriendList = friendablePlayersList.map((suggestedPlayerFriend: Player, index) =>
      <ListItem key={index}>
          <ListItemText primary={`${suggestedPlayerFriend.id}`}/>
          <Button onClick={(event) => handleSendFriendRequest(event,suggestedPlayerFriend)} >Send friend request</Button>
      </ListItem>
  )
  
  return (
    <Box>

        <List subheader={<ListSubheader>Players you could invite to friends:</ListSubheader>}>
            {suggestedPlayerFriendList}
        </List>
    </Box>
  )
}
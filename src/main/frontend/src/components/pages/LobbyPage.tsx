import { Box, Button, List, ListItem, ListItemButton, ListItemText, ListSubheader } from '@mui/material';
import axios from '../../axios'
import React, { useEffect, useState } from 'react'
import { Route, useNavigate, useParams } from 'react-router-dom'
import { Player } from '../../data-interfaces';

export default function LobbyPage() {

  const {id} = useParams();

  const [invitedPlayers, setInvitedPlayers] = useState<Player[]>();
  const [gamePlayers, setGamePlayers] = useState<Player[]>();
  const [readyPlayers, setReadyPlayers]=useState<Player[]>();

  const navigate = useNavigate();

  useEffect(() => {
    axios.get<Player[]>("lobby/"+id).then((response) => {setGamePlayers(response.data);}).catch((error) => console.log(error));
    axios.get<Player[]>("lobby/invited/"+id).then((response) => {setInvitedPlayers(response.data);}).catch((error) => console.log(error));
  }, []);

  const handlePlayerSelect=(event,player:Player)=>{
    navigate("/profile/"+player.id);
  }

  return (
    <div>
      <h3>This is the lobby of game : {id}</h3>

      <Box>
      <List subheader={<ListSubheader>Game Players</ListSubheader>}>
      {gamePlayers?.map((player) => (
        <ListItem key={player.id}>
          <ListItemButton onClick={(event) => handlePlayerSelect(event,player)}>
            <ListItemText>{player.nickname}</ListItemText>
          </ListItemButton>
        </ListItem>
      ))}
      </List>
      </Box>

      <Box>
      <List subheader={<ListSubheader>Invited Players</ListSubheader>}>
      {invitedPlayers?.map((player) => (
        <ListItem key={player.id}>
          <ListItemButton onClick={(event) => handlePlayerSelect(event,player)}>
            <ListItemText>{player.nickname}</ListItemText>
          </ListItemButton>
        </ListItem>
      ))}
      </List>
      </Box>
      
    </div>
 ); 
}

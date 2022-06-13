import {
  Alert,
  Avatar,
  Button,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  ListSubheader,
  Snackbar,
} from "@mui/material";
import React, { useState, useEffect } from "react";
import ludoAxios from "../ludo-axios";
import { Player, PlayerFriendInvite } from "../data-interfaces";
import authService from "../services/auth.service";

export default function PlayerFriendInviteList() {
  const [invitingPlayers, setInvitingPlayers] = useState(
    new Array<Player>()
  );

  useEffect(() => {
    if(!authService.isPlayerLoggedIn()) {
      return;
    }
    ludoAxios
      .get<Player[]>("player_friend_invites/" + authService.getCurrentPlayer().id)
      .then((response) => {
        setInvitingPlayers(response.data);
      })
      .catch((error) => console.log(error));
  },[]);
  

  const handleAccept=(event, player)=>{
    const playerFriendInvite = new PlayerFriendInvite(player.id, authService.getCurrentPlayer().id) 
    ludoAxios.put("player_friend_invites/accept", playerFriendInvite
    )
    .catch((error) => console.log(error));
    refreshInviteList(player);
    
  }

  const handleDecline=(event, player)=>{
    const playerFriendInvite = new PlayerFriendInvite(player.id, authService.getCurrentPlayer().id) 
    ludoAxios.put("player_friend_invites/decline",playerFriendInvite)
    .catch((error) => console.log(error));
    refreshInviteList(player);
  }

  const refreshInviteList=(player:Player)=>{
    invitingPlayers.splice(
      invitingPlayers.indexOf(player),
      1
    );
    setInvitingPlayers(invitingPlayers);
  }
  

  return (
    <div>
      <List subheader={<ListSubheader>Friend invites</ListSubheader>}>
        {invitingPlayers.map((player) => {
          return (
            <ListItem>
              <ListItemAvatar>
                <Avatar></Avatar>
              </ListItemAvatar>
              <ListItemText>{player.nickname}</ListItemText>
                <Button onClick={(event) => handleAccept(event,player)} >Accept</Button>
                <Button onClick={(event) => handleDecline(event,player)} >Decline</Button>
            </ListItem>
          );
        })}
      </List>
    </div>
  );
}

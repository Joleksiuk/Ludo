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

interface PlayerFriendInviteListProps {
  onAccept: () => void
}

export default function PlayerFriendInviteList(props: PlayerFriendInviteListProps) {
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
    props.onAccept() 
  }

  const handleDecline=(event, player)=>{
    const playerFriendInvite = new PlayerFriendInvite(player.id, authService.getCurrentPlayer().id) 
    ludoAxios.put("player_friend_invites/decline",playerFriendInvite)
    .catch((error) => console.log(error));
    refreshInviteList(player);
  }

  const refreshInviteList=(player:Player)=>{
    setInvitingPlayers(invitingPlayers => {
      invitingPlayers.splice(
        invitingPlayers.indexOf(player),
        1);
        return [...invitingPlayers]

    });
  }
  

  return (
    <div>
      <List subheader={<ListSubheader>Friend invites</ListSubheader>}>
        {invitingPlayers.map((player, index) => {
          return (
            <ListItem key={index}>
              <ListItemAvatar>
                <Avatar src={player.picture}></Avatar>
              </ListItemAvatar>
              <ListItemText>{player.nickname}</ListItemText>
                <Button onClick={(event) => handleAccept(event,player)} >Accept</Button>
                <Button onClick={(event) => handleDecline(event,player)} >Decline</Button>
            </ListItem>
          );
        })}
      {invitingPlayers.length === 0 &&
        <ListItem>
            <ListItemText>No new invites</ListItemText>
        </ListItem>
      }
      </List>
    </div>
  );
}

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
import axios from "../axios";
import { PlayerFriendInvite } from "../data-interfaces";

export default function PlayerFriendInviteList() {
  const [playerFriendInvites, setplayerFriendInvites] = useState(
    new Array<PlayerFriendInvite>()
  );



  useEffect(() => {
    axios
      .get<PlayerFriendInvite[]>("player_friend_invites")
      .then((response) => {
        setplayerFriendInvites(response.data);
      })
      .catch((error) => console.log(error));
  },);

  const handleAccept=(event, playerFriendInvite)=>{
    axios.put("player_friend_invites/accept",playerFriendInvite)
    .catch((error) => console.log(error));
    
  }

  const handleDecline=(event, playerFriendInvite)=>{
    axios.put("player_friend_invites/decline",playerFriendInvite)
    .catch((error) => console.log(error));
  }

  return (
    <div>
      <List subheader={<ListSubheader>Friend invites</ListSubheader>}>
        {playerFriendInvites.map((playerFriendInvite) => {
          return (
            <ListItem key={playerFriendInvite.invitingUserId.toString()+playerFriendInvite.invitedUserId.toString()}>
              <ListItemAvatar>
                <Avatar></Avatar>
              </ListItemAvatar>
              <ListItemText>{playerFriendInvite.invitedUserId}</ListItemText>
                <Button onClick={(event) => handleAccept(event,playerFriendInvite)} >Accept</Button>
                <Button onClick={(event) => handleDecline(event,playerFriendInvite)} >Decline</Button>
            </ListItem>
          );
        })}
      </List>
    </div>
  );
}

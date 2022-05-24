import {
  Avatar,
  Button,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  ListSubheader,
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
  }, []);
  return (
    <div>
      <List subheader={<ListSubheader>Friend invites</ListSubheader>}>
        {playerFriendInvites.map((playerFriendInvite) => {
          return (
            <ListItem key={playerFriendInvite.invitingUserId}>
              <ListItemAvatar>
                <Avatar></Avatar>
              </ListItemAvatar>
              <ListItemText>{playerFriendInvite.invitedUserId}</ListItemText>
              <Button>Accept</Button>
              <Button>Decline</Button>
            </ListItem>
          );
        })}
      </List>
    </div>
  );
}

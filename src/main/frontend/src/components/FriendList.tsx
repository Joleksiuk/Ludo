import {
  Avatar,
  Box,
  Button,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  ListSubheader,
} from "@mui/material";
import React, { useState, useEffect } from "react";
import ludoAxios from "../ludo-axios";
import { Player } from "../data-interfaces";
import authService from "../services/auth.service";

export default function FriendlistToInviteToGame() {
  const [friendList, setfriendList] = useState(new Array<Player>());

  useEffect(() => {
    const loggedPlayer = authService.getCurrentPlayer();
    if (loggedPlayer === null) return;
    ludoAxios
      .get<Player[]>("players/friends/" + loggedPlayer.id)
      .then((response) => {
        setfriendList(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const friendsPlayerList = friendList.map((friendPlayer: Player, index) => (
    <ListItem key={index}>
      <ListItemAvatar>
        <Avatar src={friendPlayer.picture}></Avatar>
      </ListItemAvatar>
      <ListItemText primary={friendPlayer.nickname} />
    </ListItem>
  ));
  return (
    <Box>
      <List subheader={<ListSubheader>Friends</ListSubheader>}>
        {friendsPlayerList}
      </List>
    </Box>
  );
}
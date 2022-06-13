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
import { Player, PlayerGameInvite } from "../data-interfaces";
import authService from "../services/auth.service";
import { useStompClient } from "react-stomp-hooks";

interface GameInvitingProps {
    gameId: number
}

export default function FriendlistToInviteToGame(props: GameInvitingProps) {
  const [friendList, setfriendList] = useState(new Array<Player>());

  const stompClient = useStompClient()

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

  const handleInvite = (player: Player) => {
    stompClient.publish({
        destination: '/app/invite/game',
        body: JSON.stringify(new PlayerGameInvite(authService.getCurrentPlayer().id,
        player.id, props.gameId))
    })
  }

  const friendsPlayerList = friendList.map((friendPlayer: Player, index) => (
    <ListItem key={index}>
      <ListItemAvatar>
        <Avatar src={friendPlayer.picture}></Avatar>
      </ListItemAvatar>
      <ListItemText primary={friendPlayer.nickname} />
      <Button variant='outlined' onClick={() => handleInvite(friendPlayer)}>Send invite</Button>
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

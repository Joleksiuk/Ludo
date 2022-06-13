import {
    Button,
    List,
    ListItem,
    ListItemText,
    ListSubheader,
    Box, ListItemAvatar, Avatar,
} from "@mui/material";
import React, { useState, useEffect} from "react";
import { useStompClient } from "react-stomp-hooks";
import ludoAxios from "../ludo-axios";
import { Player, PlayerFriendInvite } from "../data-interfaces";
import authService from "../services/auth.service";

export default function FriendablePlayersList() {
  const [friendablePlayersList, setfriendablePlayersList] = useState(
    new Array<Player>()
  );
  const stompClient = useStompClient();


  useEffect(() => {
    const loggedPlayer = authService.getCurrentPlayer();
    if (loggedPlayer === null) return;

    const httpGetRequest = "players/suggest_friends/" + loggedPlayer.id;
    ludoAxios
      .get<Player[]>(httpGetRequest)
      .then((response) => {
        setfriendablePlayersList(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const handleSendFriendRequest = (event, suggestedPlayer: Player) => {
    friendablePlayersList.splice(
      friendablePlayersList.indexOf(suggestedPlayer),
      1
    );
    const msg = JSON.stringify({
      invitingPlayerId: authService.getCurrentPlayer().id,
      invitedPlayerId: suggestedPlayer.id,
    });
    stompClient.publish({destination: "/app/invite/friend", body: msg});

    ludoAxios.post<PlayerFriendInvite>("/player_friend_invites", JSON.parse(msg)).catch((error) => console.log(error));
  };

  const suggestedPlayerFriendList = friendablePlayersList.map(
    (suggestedPlayerFriend: Player, index) => (
      <ListItem key={index}>
          <ListItemAvatar>
              <Avatar
                  src={suggestedPlayerFriend.picture}
              >
              </Avatar>
          </ListItemAvatar>
        <ListItemText primary={suggestedPlayerFriend.nickname} />
        <Button
          onClick={(event) =>
            handleSendFriendRequest(event, suggestedPlayerFriend)
          }
        >
          Send friend request
        </Button>
      </ListItem>
    )
  );

  return (
    <Box>
      <List
        subheader={
          <ListSubheader>Invite players to friends</ListSubheader>
        }
      >
        {suggestedPlayerFriendList}
      </List>
    </Box>
  );
}

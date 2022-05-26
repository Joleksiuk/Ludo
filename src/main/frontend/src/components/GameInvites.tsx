import { Button, List, ListItem, ListItemText } from "@mui/material";
import React, { useState, useEffect } from "react";
import axios from "../axios";
import { PlayerGameInvite } from "../data-interfaces";

export default function PlayerGameInviteList() {
  const [playerGameInvites, setplayerGameInvites] = useState(
    new Array<PlayerGameInvite>()
  );

  useEffect(() => {
    axios
      .get<PlayerGameInvite[]>("player_game_invites")
      .then((response) => {
        setplayerGameInvites(response.data);
      })
      .catch((error) => console.log(error));
  }, []);
  return (
    <List>
      {playerGameInvites.map((playerGameInvite) => {
        return (
          <ListItem>
            <ListItemText>
              {playerGameInvite.invitedUserId} invited{" "}
              {playerGameInvite.invitedUserId} To the game{" "}
              {playerGameInvite.gameId}
            </ListItemText>
            <Button>Accept</Button>
            <Button>Decline</Button>
          </ListItem>
        );
      })}
    </List>
  );
}

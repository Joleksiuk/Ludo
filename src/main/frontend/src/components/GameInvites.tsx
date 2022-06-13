import {
  Button,
  List,
  ListItem,
  ListItemText,
  ListSubheader,
  Typography,
} from "@mui/material";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useStompClient } from "react-stomp-hooks";
import { GameAndPlayer, PlayerGameInvite } from "../data-interfaces";
import ludoAxios from "../ludo-axios";
import authHeader from "../services/auth.header";
import authService from "../services/auth.service";

export default function PlayerGameInviteList() {
  const [playerGameInvites, setPlayerGameInvites] = useState(
    new Array<GameAndPlayer>()
  );

  const stompClient = useStompClient();
  const navigate = useNavigate();

  useEffect(() => {
    if (!authService.isPlayerLoggedIn()) {
      return;
    }
    ludoAxios
      .get<GameAndPlayer[]>("player-game-invites/" + authService.getCurrentPlayer().id)
      .then((response) => {
        setPlayerGameInvites(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const handleAccept = (gameAndPlayer: GameAndPlayer) => {
    stompClient.publish({
      destination: "/app/invite/game/accept",
      body: JSON.stringify(gameAndPlayerToInvite(gameAndPlayer)),
      headers: authHeader(),
    });
    deleteInvite(gameAndPlayer)
    navigate(`/lobby/${gameAndPlayer.game.id}`)
  };

  const handleDecline = (gameAndPlayer: GameAndPlayer) => {
    ludoAxios.put(
      "player-game-invites/decline",
      gameAndPlayerToInvite(gameAndPlayer)
    ).then(() => deleteInvite(gameAndPlayer)
    );
  };

  const deleteInvite = (gameAndPlayer: GameAndPlayer) => {
      setPlayerGameInvites(invites => {
        const i = invites.indexOf(gameAndPlayer);
        invites.splice(i, 1);
        return [...invites]
      })
  }

  const gameAndPlayerToInvite = (
    gameAndPlayer: GameAndPlayer
  ): PlayerGameInvite => {
    return new PlayerGameInvite(
      gameAndPlayer.player.id,
      authService.getCurrentPlayer().id,
      gameAndPlayer.game.id
    );
  };

  return (
    <List subheader={<ListSubheader>Game invites</ListSubheader>}>
      {playerGameInvites.map((gameAndPlayer) => {
        return (
          <ListItem>
            <ListItemText>
              {gameAndPlayer.player.nickname} invited you to the game{" "}
              {gameAndPlayer.game.name}
            </ListItemText>
            <Button onClick={() => handleAccept(gameAndPlayer)}>Accept</Button>
            <Button onClick={() => handleDecline(gameAndPlayer)}>
              Decline
            </Button>
          </ListItem>
        );
      })}
      {playerGameInvites.length === 0 && (
        <ListItem>
          <ListItemText>No new invites</ListItemText>
        </ListItem>
      )}
    </List>
  );
}

import { Button, Snackbar, SnackbarContent } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useStompClient } from "react-stomp-hooks";
import { GameAndPlayer, PlayerGameInvite } from "../../data-interfaces";
import ludoAxios from "../../ludo-axios";
import authHeader from "../../services/auth.header";
import authService from "../../services/auth.service";

interface GameInviteNotificationProps {
  gameAndPlayer: GameAndPlayer;
}

export default function GameInviteNotification(
  props: GameInviteNotificationProps
) {
  const [snackbarOpen, setSnackbarOpen] = useState<boolean>(true);
  const stompClient = useStompClient();
  const navigate = useNavigate()

  const action = (
    <>
      <Button onClick={() => handleAccept()}>Accept</Button>
      <Button onClick={() => handleDecline()}>Decline</Button>
    </>
  );

  const gameAndPlayerToInvite = (): PlayerGameInvite => {
    return new PlayerGameInvite(
      props.gameAndPlayer.player.id,
      authService.getCurrentPlayer().id,
      props.gameAndPlayer.game.id
    );
  };

  const handleAccept = () => {
    stompClient.publish({
      destination: "/app/invite/game/accept",
      body: JSON.stringify(gameAndPlayerToInvite()),
      headers: authHeader(),
    });
    setSnackbarOpen(false);
    navigate(`/lobby/${props.gameAndPlayer.game.id}`)
  };

  const handleDecline = () => {
    ludoAxios
      .put("player-game-invites/decline", gameAndPlayerToInvite())
      .then(() => {
        setSnackbarOpen(false);
      });
  };

  const handleSnackbarClose = (event) => {
    setSnackbarOpen(false);
  };

  return (
    <Snackbar
      open={snackbarOpen}
      onClose={handleSnackbarClose}
      key={"bottomright"}
    >
      <SnackbarContent
        message={//'Game invite received'
          props.gameAndPlayer.player.nickname +
          " invited you to the " +
          props.gameAndPlayer.game.name +
          " game!"
        }
        action={action}
      />
    </Snackbar>
  );
}

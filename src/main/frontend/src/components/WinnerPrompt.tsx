import { Box, Button, Stack, Typography } from "@mui/material";
import React from "react";
import { Player } from "../data-interfaces";
import authService from "../services/auth.service";

interface WinnerPromptProps {
  player: Player;
  onButtonClick: () => void;
}

export default function WinnerPrompt(props: WinnerPromptProps) {
  const getWinMessage = () => {
    return (authService.getCurrentPlayer().id === props.player.id
      ? "You"
      : "Player " + props.player.nickname) + " won!";
  };

  return (
    <Box sx={{ margin: "20px", display:'flex', justifyContent:'center' }}>
      <Stack>
        <Typography variant="h3">{getWinMessage()}</Typography>
        <Button variant="contained" onClick={props.onButtonClick}>
          GG!
        </Button>
      </Stack>
    </Box>
  );
}

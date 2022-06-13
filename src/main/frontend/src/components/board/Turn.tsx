import { Avatar, Grid, Paper, Stack, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useEffect, useState } from "react";
import { useSubscription } from "react-stomp-hooks";
import { Player } from "../../data-interfaces";
import ludoAxios from "../../ludo-axios";

interface TurnProps {
  gameId: number;
  onTurnChange: (player: Player) => void;
}
export default function Turn(props: TurnProps) {
  const [currentPlayer, setCurrentPlayer] = useState<Player>();

  useEffect(() => {
    ludoAxios
      .get<Player>(`games/${props.gameId}/turn`)
      .then((response) => response.data)
      .then((player) => setCurrentPlayer(player));
  }, []);

  useSubscription(`/topic/game-turn.${props.gameId}`, (message) =>
    handleTurnChange(JSON.parse(message.body))
  );

  const handleTurnChange = (player: Player) => {
    setCurrentPlayer(player);
    props.onTurnChange(player);
  }

  return (
    <Paper sx={{margin: '10px', padding: '5px'}}>
      <Stack justifyContent={'center'}>
        <Typography variant='h6'>Current turn:</Typography>
        {currentPlayer !== undefined && (
          <Grid container alignItems='center' justifyContent={'center'}>
            <Grid item xs={3}>
                <Avatar src={currentPlayer.picture}></Avatar>
            </Grid>
            <Grid item xs={2}>
                <Typography>{currentPlayer.nickname}</Typography>
            </Grid>
          </Grid>
        )}
      </Stack>
    </Paper>
  );
}

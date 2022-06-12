import { Box, Button, Grid, Paper, Stack, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import Board from "../board/Board";
import { useStompClient, useSubscription } from "react-stomp-hooks";
import { useParams } from "react-router-dom";
import Dice from "../Dice";
import Turn from "../Turn";
import authService from "../../services/auth.service";
import { Game, Player } from "../../data-interfaces";
import ludoAxios from "../../ludo-axios";

export default function GamePage() {

  const {id } = useParams();

  const [diceValue, setDiceValue] = React.useState<number>(1);
  const stompClient = useStompClient();

  const [diceRollEnabled, setDiceRollEnabled] = useState<boolean>(false)

  useEffect(() => {
    ludoAxios.get<Game>(`games/${gameId.current}`)
    .then(response => response.data)
    .then(game => setDiceRollEnabled(!game.diceThrownInTurn && authService.isPlayerLoggedIn() && authService.getCurrentPlayer().id === game.turnPlayerId))
  }, [])

  const toggleDiceButton = (playerTurnParam: Player): void => {
      if (!authService.isPlayerLoggedIn()) {
        setDiceRollEnabled(false);
      }

      setDiceRollEnabled(playerTurnParam.id === authService.getCurrentPlayer().id)
  }

  const invertDiceButtonEnabled = () => {
    setDiceRollEnabled(enabled => !enabled)
  }

  return (
      <Box sx={{ padding: "20px" }}>
        <Grid container spacing={0}>
          <Grid item xs={9}>
            <Board gameId={parseInt(id)}></Board>
          </Grid>
          <Grid item xs={2}>
            <Paper sx={{ height: "100%" }}>
              <Stack spacing={2}>
                <Turn gameId={parseInt(id)} onTurnChange={toggleDiceButton}></Turn>
                <Dice gameId={parseInt(id)} onDiceRoll={invertDiceButtonEnabled} enabled={diceRollEnabled}></Dice>
              </Stack>
            </Paper>
          </Grid>
        </Grid>
      </Box>
  );
}

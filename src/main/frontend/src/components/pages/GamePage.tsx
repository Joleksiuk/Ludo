import { Box, Button, Grid, Paper, Stack, Typography } from "@mui/material";
import React from "react";
import Board from "../board/Board";
import { GameIdContext } from "../GameIdProvider";
import { useStompClient, useSubscription } from "react-stomp-hooks";
import { GameStatusMessage } from "../../data-interfaces";
import { useParams } from "react-router-dom";

export default function GamePage() {

  const {id } = useParams();

  const [diceValue, setDiceValue] = React.useState<number>(1);
  const stompClient = useStompClient();

  const handleRoll = (event: React.MouseEvent) => {
    event.preventDefault();
    if (stompClient && gameIdDefined()) {
      stompClient.publish({destination: `/app/game/${id}/dice`});
    } 
  };

  const handleStatusUpdate = (message: GameStatusMessage) => {
    setDiceValue(message.diceValue);
  }

  const gameIdDefined = () => id !== null;

  useSubscription(gameIdDefined() ? [`/topic/game.${id}`] : [], (message) => handleStatusUpdate(JSON.parse(message.body)))

  return (
      <Box sx={{ padding: "20px" }}>
        <Grid container spacing={0}>
          <Grid item xs={9}>
            <Board gameId={parseInt(id)}></Board>
          </Grid>
          <Grid item xs={2}>
            <Paper sx={{ height: "100%" }}>
              <Stack alignItems="center" spacing={2}>
                <Button
                  type="button"
                  variant="contained"
                  onClick={(event) => handleRoll(event)}
                >
                  Roll the dice
                </Button>
                <Typography>
                  You rolled: {diceValue}, game id: {id}
                </Typography>
                <Box>
                  <Typography>Chat or other</Typography>
                </Box>
              </Stack>
            </Paper>
          </Grid>
        </Grid>
      </Box>
  );
}

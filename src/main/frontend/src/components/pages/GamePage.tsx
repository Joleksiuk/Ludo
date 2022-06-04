import { Box, Button, Grid, Paper, Stack, Typography } from "@mui/material";
import React from "react";
import Board from "../board/Board";
import { GameIdContext } from "../GameIdProvider";
import { useStompClient, useSubscription } from "react-stomp-hooks";
import { GameStatusMessage } from "../../data-interfaces";

export default function GamePage() {
  const [diceValue, setDiceValue] = React.useState<number>(1);
  const gameId = React.useContext(GameIdContext);
  const stompClient = useStompClient();

  const handleRoll = (event: React.MouseEvent) => {
    event.preventDefault();
    if (stompClient && gameIdDefined()) {
      stompClient.publish({destination: `/app/game/${gameId.current}/dice`});
    } 
  };

  const handleStatusUpdate = (message: GameStatusMessage) => {
    setDiceValue(message.diceValue);
  }

  const gameIdDefined = () => gameId.current !== null;

  useSubscription(gameIdDefined() ? [`/topic/game.${gameId.current}`] : [], (message) => handleStatusUpdate(JSON.parse(message.body)))

  return (
      <Box sx={{ padding: "20px" }}>
        <Grid container spacing={0}>
          <Grid item xs={9}>
            <Board gameId={gameId.current}></Board>
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
                  You rolled: {diceValue}, game id: {gameId.current}
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

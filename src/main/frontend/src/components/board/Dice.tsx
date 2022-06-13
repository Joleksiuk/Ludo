import { Box, Button, Grid, Paper, Stack, Typography } from "@mui/material";
import React, { useEffect } from "react";
import { useStompClient, useSubscription } from "react-stomp-hooks";
import ludoAxios from "../../ludo-axios";
import authHeader from "../../services/auth.header";

interface DiceProps {
  gameId: number;
  enabled: boolean;
  onDiceRoll: () => void;
}

export default function Dice(props: DiceProps) {
  const [diceValue, setDiceValue] = React.useState<number>(1);
  const stompClient = useStompClient();

  const diceFaces = [
    "https://upload.wikimedia.org/wikipedia/commons/2/2c/Alea_1.png",
    "https://upload.wikimedia.org/wikipedia/commons/b/b8/Alea_2.png",
    "https://upload.wikimedia.org/wikipedia/commons/2/2f/Alea_3.png",
    "https://upload.wikimedia.org/wikipedia/commons/8/8d/Alea_4.png",
    "https://upload.wikimedia.org/wikipedia/commons/5/55/Alea_5.png",
    "https://upload.wikimedia.org/wikipedia/commons/f/f4/Alea_6.png",
  ];

  const handleRoll = (event: React.MouseEvent) => {
    event.preventDefault();
    if (stompClient && props.gameId !== null) {
      stompClient.publish({
        destination: `/app/game/${props.gameId}/dice`,
        headers: authHeader(),
      });
    }
    props.onDiceRoll();
  };

  useEffect(() => {
    ludoAxios
      .get(`games/${props.gameId}/dice`)
      .then((response) => setDiceValue(response.data));
  }, []);

  useSubscription(
    props.gameId !== null ? [`/topic/game-dice.${props.gameId}`] : [],
    (message) => setDiceValue(JSON.parse(message.body))
  );

  return (
    <Box sx={{ padding: '5px' }}>
      <Grid container justifyContent={'center'} alignItems='center' spacing={0.5}>
        <Grid item xs={6}> 
          <Button
            type="button"
            variant="contained"
            disabled={!props.enabled}
            onClick={(event) => handleRoll(event)}
          >
            Roll the dice
          </Button>
        </Grid>
        <Grid item xs={2}>
          <Box
            component="img"
            src={diceFaces[diceValue - 1]}
            sx={{ height: 50, width: 50 }}
          ></Box>
        </Grid>
      </Grid>
    </Box>
  );
}

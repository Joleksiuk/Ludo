import axios from "../ludo-axios";
import React, { useEffect, useState } from "react";
import { Board, Game } from "../data-interfaces";
import Button from "@mui/material/Button";
import {
  TextField,
  Radio,
  FormControlLabel,
  RadioGroup,
  FormControl,
  Stack,
  Typography,
  FormLabel,
} from "@mui/material";

interface AddGameFormProps {
  onSave: () => void
}

export default function AddGameForm(props: AddGameFormProps) {
  const [boards, setBoards] = useState<Board[]>(new Array<Board>());

  const [gameName, setGameName] = useState<string>();
  const [gameBoardCode, setGameBoardCode] = useState<string>();

  const renderBoardField = (board: Board) => {
    return (
      <FormControlLabel
        key={board.code}
        control={
          <Radio
            checked={board.code === gameBoardCode}
            onChange={() => setGameBoardCode(board.code)}
          />
        }
        label={board.name}
      />
    );
  };
  useEffect(() => {
    axios.get<Board[]>("boards").then((response) => setBoards(response.data));
  }, []);

  const handleSubmit = (event: React.FormEvent): void => {
    event.preventDefault();
    axios.post<Game>("games", { name: gameName, boardCode: gameBoardCode });
    props.onSave();
  };

  return (
    <FormControl sx={{margin: '20px'}}>
      <Stack spacing={2}>
      <Typography variant='h6' component='h2'>Add game</Typography>
      <TextField
        type="text"
        label="Game name"
        id="name"
        onChange={(e) => setGameName(e.target.value)}
      ></TextField>
      <FormLabel>Board type</FormLabel>
      <RadioGroup>{boards.map((board) => renderBoardField(board))}</RadioGroup>
      <Button onClick={event => handleSubmit(event)} variant="contained">
        Add game
      </Button>
      </Stack>
    </FormControl>
  );
}

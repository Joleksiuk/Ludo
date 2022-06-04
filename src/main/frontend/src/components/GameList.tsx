import { Box, List, ListItem, ListItemButton, ListItemText, ListSubheader } from "@mui/material";
import React, { useState, useEffect, useContext } from "react";
import { Navigate } from "react-router-dom";
import axios from "../axios";
import { Game } from "../data-interfaces";
import { GameIdContext } from "./GameIdProvider";

export default function GameList() {
  const [games, setGames] = useState(new Array<Game>());
  const gameId = useContext(GameIdContext)
  const [redirectToGame, setRedirectToGame] = useState<boolean>(false)

  const handleGameSelect = (clickedGame: Game) => {
    gameId.current = clickedGame.id;
    setRedirectToGame(true);
  }

  useEffect(() => {
    axios
      .get<Game[]>("games")
      .then((response) => {
        setGames(response.data);
      })
      .catch((error) => console.log(error));
  }, []);
  return (
    <Box>
      <List subheader={<ListSubheader>My games</ListSubheader>}>
        {games.map((game) => (
          <ListItem key={game.id}>
            <ListItemButton onClick={() => handleGameSelect(game)}>
              <ListItemText>{game.name}</ListItemText>
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      { redirectToGame ? <Navigate to='/game'/> : <></> }
    </Box>
  );
}

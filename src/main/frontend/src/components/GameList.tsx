import { Box, Button, List, ListItem, ListItemButton, ListItemText, ListSubheader } from "@mui/material";
import React, { useState, useEffect, useContext } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import axios from "../axios";
import { Game } from "../data-interfaces";
import { GameIdContext } from "./GameIdProvider";

export default function GameList() {
  const [games, setGames] = useState(new Array<Game>());
  const gameId = useContext(GameIdContext)
  const [redirectToGame, setRedirectToGame] = useState<boolean>(false)

  const navigate = useNavigate();

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


  const navigateToLobby=(game:Game)=>{
    navigate("/lobby/"+game.id);
  }

  return (
    <Box>
      <List subheader={<ListSubheader>My games</ListSubheader>}>
        {games.map((game) => (
          <ListItem key={game.id}>
            <ListItemButton onClick={() => handleGameSelect(game)}>
              <ListItemText>{game.name}</ListItemText>
            </ListItemButton>
            <Button onClick={() => navigateToLobby(game)}>Go to lobby</Button>
          </ListItem>
        ))}
      </List>
      
      {/* { redirectToGame ? <Navigate to='/game'/> : <></> } */}
    </Box>
  );
}

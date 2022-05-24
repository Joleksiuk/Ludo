import { List, ListItem, ListItemButton, ListItemText, ListSubheader } from "@mui/material";
import React, { useState, useEffect } from "react";
import axios from "../axios";
import { Game } from "../data-interfaces";

export default function GameList() {
  const [games, setGames] = useState(new Array<Game>());

  useEffect(() => {
    axios
      .get<Game[]>("games")
      .then((response) => {
        setGames(response.data);
      })
      .catch((error) => console.log(error));
  }, []);
  return (
    <div>
      <List subheader={<ListSubheader>My games</ListSubheader>}>
        {games.map((game) => (
          <ListItem key={game.id}>
            <ListItemButton href="/game">
              <ListItemText>{game.name}</ListItemText>
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </div>
  );
}

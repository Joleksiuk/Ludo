import {
  Avatar,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  ListSubheader,
} from "@mui/material";
import React, { useState, useEffect } from "react";
import axios from "../axios";
import { Player } from "../data-interfaces";

export default function PlayerList() {
  const [players, setplayer] = useState(new Array<Player>());

  useEffect(() => {
    axios
      .get<Player[]>("players")
      .then((response) => {
        setplayer(response.data);
      })
      .catch((error) => console.log(error));
  }, []);
  return (
    <>
      <List subheader={<ListSubheader>All players</ListSubheader>}>
        {players.map((player) => {
          return (
            <ListItem key={player.id}>
              <ListItemAvatar>
                <Avatar></Avatar>
              </ListItemAvatar>
              <ListItemText>{player.nickname}</ListItemText>
            </ListItem>
          );
        })}
      </List>
    </>
  );
}

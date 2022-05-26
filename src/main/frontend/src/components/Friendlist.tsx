import { List, ListItem, ListItemAvatar, ListItemText, ListSubheader } from "@mui/material";
import React, { useState, useEffect } from "react";
import axios from "../axios";
import { PlayerFriend } from "../data-interfaces";

export default function FriendList() {
  const [playersFriends, setplayerFriends] = useState(
    new Array<PlayerFriend>()
  );

  useEffect(() => {
    axios
      .get<PlayerFriend[]>("player_friends")
      .then((response) => {
        setplayerFriends(response.data);
      })
      .catch((error) => console.log(error));
  }, []);
  return (
    <List subheader={<ListSubheader>Friend list</ListSubheader>}>
      {playersFriends.map((playerFriend) => {
        return (
          <ListItem>
            <ListItemAvatar></ListItemAvatar>
            <ListItemText>
              {playerFriend.firstUserId} Is Friend with{" "}
              {playerFriend.secondUserId}{" "}
            </ListItemText>
          </ListItem>
        );
      })}
    </List>
  );
}

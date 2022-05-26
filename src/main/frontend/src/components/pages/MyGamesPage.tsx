import React from "react";
import AddGameForm from "../AddGameForm";
import GameList from "../GameList";

export default function MyGamesPage() {
  return (
    <>
      <GameList></GameList>
      <AddGameForm></AddGameForm>
    </>
  );
}

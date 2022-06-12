import React from "react";
import AddGameForm from "../AddGameForm";
import GameList from "../GameList";
import {Navigate} from "react-router-dom";
import RedirectToLogin from "../RedirectToLogin";
import authService from "../../services/auth.service";

export default function MyGamesPage() {
  return (
    <>
    {authService.isPlayerLoggedIn() 
      ? <GameList></GameList>
      : <Navigate to ='/redirect'/>}
    </>
  );
}

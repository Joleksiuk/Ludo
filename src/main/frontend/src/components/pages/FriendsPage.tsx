import React from "react";
import FriendablePlayersList from "../FriendablePlayerList";
import PlayerFriendInviteList from "../PlayerFriendInviteList";
import {Navigate} from "react-router-dom";
import PlayerList from "../PlayersList";
import RedirectToLogin from "../RedirectToLogin";
import authService from "../../services/auth.service";

export default function FriendsPage() {
  return (
  <>
    {authService.isPlayerLoggedIn() ? (
        <>
          <PlayerList></PlayerList>
          <PlayerFriendInviteList></PlayerFriendInviteList>
          <FriendablePlayersList></FriendablePlayersList>
        </>):
    <Navigate to ='/redirect'/>}
  </>
  );
}

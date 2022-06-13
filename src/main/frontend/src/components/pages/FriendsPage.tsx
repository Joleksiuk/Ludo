import React, { useState } from "react";
import FriendablePlayersList from "../FriendablePlayerList";
import PlayerFriendInviteList from "../PlayerFriendInviteList";
import {Navigate} from "react-router-dom";
import PlayerList from "../PlayersList";
import RedirectToLogin from "../RedirectToLogin";
import authService from "../../services/auth.service";
import FriendList from "../FriendList";

export default function FriendsPage() {

  const [toggle, setToggle] = useState<boolean>(false)

  return (
  <>
    {authService.isPlayerLoggedIn() ? (
        <>
          <PlayerFriendInviteList onAccept={() => setToggle(!toggle)}></PlayerFriendInviteList>
          <FriendList></FriendList>
          <FriendablePlayersList></FriendablePlayersList>
        </>):
    <Navigate to ='/redirect'/>}
  </>
  );
}

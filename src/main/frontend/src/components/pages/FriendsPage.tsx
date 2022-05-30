import React from "react";
import FriendablePlayersList from "../FriendablePlayerList";
import FriendList from "../FriendList"
import PlayerFriendInviteList from "../PlayerFriendInviteList";

import PlayerList from "../PlayersList";

export default function FriendsPage() {
  return (
    <>
      <PlayerList></PlayerList>
      <PlayerFriendInviteList></PlayerFriendInviteList>
      <FriendList></FriendList>
      <FriendablePlayersList></FriendablePlayersList>
    </>
  );
}

import React from "react";
import FriendablePlayersList from "../FriendablePlayerList";
import PlayerFriendInviteList from "../PlayerFriendInviteList";

import PlayerList from "../PlayersList";

export default function FriendsPage() {
  return (
    <>
      <PlayerList></PlayerList>
      <PlayerFriendInviteList></PlayerFriendInviteList>
      <FriendablePlayersList></FriendablePlayersList>
    </>
  );
}

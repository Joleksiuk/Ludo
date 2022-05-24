import React from "react";
import PlayerFriendInviteList from "../PlayerFriendInviteList";
import FriendList from "../FriendList";
import PlayerList from "../PlayersList";

export default function FriendsPage() {
  return (
    <>
      <PlayerList></PlayerList>
      <PlayerFriendInviteList></PlayerFriendInviteList>
      <FriendList></FriendList>
    </>
  );
}

import React from "react";
import FriendList from "../Friendlist";
import PlayerFriendInviteList from "../PlayerFriendInviteList";

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

import React, { useState } from "react";
import { useSubscription } from "react-stomp-hooks";
import { PlayerFriendInvite } from "../data-interfaces";
import authService from "../services/auth.service";
import FriendInviteNotification from "./FriendInviteNotification";

export default function NotificationStack() {
  const [friendRequestNotifications, setFriendRequestNotifications] = useState<
    PlayerFriendInvite[]
  >([]);

  const handleMessage = (msg) => {
    setFriendRequestNotifications((previous) => [...previous, msg]);
  };

  useSubscription(
    authService.isPlayerLoggedIn()
      ? [`/topic/invite.friend.${authService.getCurrentPlayer().id}`]
      : [],
    (msg) => handleMessage(msg)
  );
  return (
    <>
      {friendRequestNotifications.map((playerFriendInvite, index) => (
        <FriendInviteNotification
          key={index}
          playerFriendInvite={playerFriendInvite}
        ></FriendInviteNotification>
      ))}
    </>
  );
}

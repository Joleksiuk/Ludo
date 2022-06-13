import React, { useState } from "react";
import { useSubscription } from "react-stomp-hooks";
import { GameAndPlayer, PlayerFriendInvite, PlayerGameInvite } from "../../data-interfaces";
import authService from "../../services/auth.service";
import GameInviteNotification from "./GameInviteNotification";
import FriendInviteNotification from "./FriendInviteNotification";

export default function NotificationStack() {
  const [friendRequestNotifications, setFriendRequestNotifications] = useState<
    PlayerFriendInvite[]
  >([]);

  const [gameInvites, setGameInvites] = useState<GameAndPlayer[]>([])

  const handleFriendInvite = (msg) => {
    setFriendRequestNotifications((previous) => [...previous, msg]);
  };

  const handleGameInvite = (gameInvite: GameAndPlayer) => {
    console.log(gameInvite)
    setGameInvites([...gameInvites,gameInvite])
  }

  useSubscription(
    authService.isPlayerLoggedIn()
      ? [`/topic/invite.friend.${authService.getCurrentPlayer().id}`]
      : [],
    (msg) => handleFriendInvite(msg)
  );

  useSubscription(
    authService.isPlayerLoggedIn()
      ? [`/topic/invite.game.${authService.getCurrentPlayer().id}`]
      : [],
    (msg) => handleGameInvite(JSON.parse(msg.body))
  );
  return (
    <>
      {friendRequestNotifications.map((playerFriendInvite, index) => (
        <FriendInviteNotification
          key={index}
          playerFriendInvite={playerFriendInvite}
        ></FriendInviteNotification>
      ))}
      {gameInvites.map((invite, inx) => (
       <GameInviteNotification key={inx} gameAndPlayer={invite}/>
      ))}
    </>
  );
}

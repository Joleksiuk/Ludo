import { Button, Snackbar, SnackbarContent } from "@mui/material";
import axios from "axios";
import { useState } from "react";
import { PlayerFriendInvite } from "../data-interfaces";
import authService from "../services/auth.service";

interface FriendNotificationProps {
  playerFriendInvite: PlayerFriendInvite;
}

export default function FriendInviteNotification(
  props: FriendNotificationProps
) {
  const [snackbarOpen, setSnackbarOpen] = useState<boolean>(true);

  const handleAccept = (event) => {
    axios.put<PlayerFriendInvite>("player_friend_invites/accept", props.playerFriendInvite).catch((error) => console.log(error));
    setSnackbarOpen(false);
  };

  const handleDecline = (event) => {
    axios.put<PlayerFriendInvite>("player_friend_invites/decline", props.playerFriendInvite).catch((error) => console.log(error));
    setSnackbarOpen(false);
  };

  const handleSnackbarClose = (event) => {
    setSnackbarOpen(false);
  };

  const action = (
    <>
      <Button onClick={(event) => handleAccept(event)}>Accept</Button>
      <Button onClick={(event) => handleDecline(event)}>Decline</Button>
    </>
  );

  return (
    <>
      <Snackbar
        open={snackbarOpen}
        onClose={handleSnackbarClose}
        key={"bottomright"}
      >
        <SnackbarContent message={"You received an invite!"} action={action} />
      </Snackbar>
    </>
  );
}

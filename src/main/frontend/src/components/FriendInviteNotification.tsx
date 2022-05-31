import { Button, Snackbar, SnackbarContent } from "@mui/material";
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
    setSnackbarOpen(false);
  };

  const handleDecline = (event) => {
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

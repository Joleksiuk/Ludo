import { Button, Snackbar, SnackbarContent } from "@mui/material";
import axios from "axios";
import { useState } from "react";
import { PlayerFriendInvite } from "../../data-interfaces";
import authService from "../../services/auth.service";

interface FriendNotificationProps {
  playerFriendInvite: PlayerFriendInvite;
}

export default function FriendInviteNotification(
  props: FriendNotificationProps
) {
  const [snackbarOpen, setSnackbarOpen] = useState<boolean>(true);

  const handleSnackbarClose = (event) => {
    setSnackbarOpen(false);
  };

  return (
    <>
      <Snackbar
        open={snackbarOpen}
        onClose={handleSnackbarClose}
        key={"bottomright"}
      >
        <SnackbarContent message={"You received a friend invite!"}/>
      </Snackbar>
    </>
  );
}

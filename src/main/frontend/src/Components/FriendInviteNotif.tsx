import { Button, Container, Divider, List, ListItem, ListItemText, Paper, Snackbar, SnackbarContent, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { Fragment, useEffect, useRef, useState } from "react";
import { PlayerFriendInvite } from "../data-interfaces";

interface FriendNotificationProps {
    playerFriendInvite: PlayerFriendInvite;
}

export default function FriendInviteNotification(props: FriendNotificationProps){

    
    const [playerFriendInvite, setPlayerFriendInvite] = useState<PlayerFriendInvite>();
    const [snackbarOpen, setSnackbarOpen]=useState<boolean>(true);


    const handleAccept = (event) => {

        setSnackbarOpen(false);
    }

    const handleDecline =(event) =>{
        setSnackbarOpen(false);
    }

    const handleSnackbarClose=(event)=>{
        setSnackbarOpen(false);
    }

    const action = (
        <><Button onClick={(event) => handleAccept(event)}>Accept</Button>
        <Button onClick={(event) => handleDecline(event)}>Decline</Button></>
      );



    return (
        <Snackbar open={snackbarOpen} onClose={handleSnackbarClose} key={"bottomright"}>
            <SnackbarContent  message={'You received an invite!'} action = {action}/>             
        </Snackbar>
    )
}
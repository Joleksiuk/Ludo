import { Button, Container, Divider, List, ListItem, ListItemText, Paper, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { Fragment, useEffect, useRef, useState } from "react";
import { FriendInviteDto } from "./Models/FriendInviteDto";
import { PlayerFriendInvite } from "../../data-interfaces";

export default function FriendInviteNotification(){

    const webSocket = useRef(null);
    const [friendRequests, setFriendRequest] = useState([]);
    const [playerFriendInvite, setPlayerFriendInvite] = useState<PlayerFriendInvite>();
    const [message, setMessage] = useState('');

    useEffect(() => {
        console.log('Opening WebSocket');
        webSocket.current = new WebSocket('ws://localhost:8080/friend_request');
        const openWebSocket = () => {
            webSocket.current.onopen = (event) => {
                console.log('Open:', event);
            }
            webSocket.current.onclose = (event) => {
                console.log('Close:', event);
            }
        }
        openWebSocket();
    }, []);

    useEffect(() => {
        console.log('Received friend invite');
        webSocket.current.onmessage = (event) => {
            const friendInviteDto = JSON.parse(event.data);
            console.log('Friend request:', friendInviteDto);
            setFriendRequest(currentFriendRequests=> [...currentFriendRequests, {
                playerFriendInvite: friendInviteDto.playerFriendInvite,
                message: friendInviteDto.message
            }]);
        }
    }, [friendRequests]);

    useEffect(() => {
        console.log('Use effect has been activated');
    }, [webSocket]);


    const handleAccept = (event, playerFriendInvite) => {
        //Send friend invite positive answer to websocket and react on backend
       friendRequests.splice(friendRequests.indexOf(playerFriendInvite),1);
    }
    const handleDecline =(event, playerFriendInvite) =>{
        //Send friend invite negative answer to websocket and ract on backend
        friendRequests.splice(friendRequests.indexOf(playerFriendInvite),1);
    }

    const sendFriendRequest = () => {
        if(playerFriendInvite && message) {
            console.log('Friend request has been sent!');
            webSocket.current.send(
                JSON.stringify(new FriendInviteDto(playerFriendInvite, message))
            );
            setMessage('');
        }
    };

    const listFriendRequests = friendRequests.map((friendInviteDto, index) => 
        <ListItem key={index}>
            <ListItemText primary={`${friendInviteDto.playerFriendInvite.invitingUserId}: ${friendInviteDto.message}`}/>
            <Button onClick={(event) => handleAccept(event,friendInviteDto)} >Accept</Button>
            <Button onClick={(event) => handleDecline(event,friendInviteDto)} >Decline</Button>

        </ListItem>
    );

    return (
        <Fragment>
            <Container>
                <Paper elevation={5}>
                    <Box p={3}>
                        <Typography variant="h4" gutterBottom>
                            Friend Invitations
                        </Typography>
                        <Divider />
                            <List id="chat-window-messages">
                                {listFriendRequests}
                            </List>   
                            <Button onClick ={sendFriendRequest}> </Button>       
                    </Box>
                </Paper>
            </Container>
        </Fragment>
    );
}
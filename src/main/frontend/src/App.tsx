import React, { useEffect, useRef, useState } from 'react';
import './App.css';
import LudoAppBar from './components/layout/LudoAppBar';
import { Box, Container} from '@mui/material';
import {
  BrowserRouter,
  Routes,
  Route,
} from 'react-router-dom';
import HomePage from './components/pages/HomePage';
import LobbyPage from './components/pages/LobbyPage';
import GamePage from './components/pages/GamePage';
import LoginPage from './components/pages/LoginPage';
import MyGamesPage from './components/pages/MyGamesPage';
import FriendsPage from './components/pages/FriendsPage';
import RegisterPage from './components/pages/RegisterPage';
import NotFoundPage from './components/pages/NotFoundPage';
import FriendInviteNotification from './components/FriendInviteNotif';
import { WebSocketContext } from './components/WebSocketProvider';
import SockJSClient from "react-stomp";
import { PlayerFriendInvite } from './data-interfaces';


function App() {

  const stompNotificationClient = useRef(null);
  const [friendRequestNotifications, setFriendRequestNotifications]=useState<PlayerFriendInvite[]>([]);

  useEffect(()=>{
    
  },[friendRequestNotifications]);

  const handleMessage=(msg)=>{

    setFriendRequestNotifications(previous => [... previous, msg]);
  }
  
  return (    
    <BrowserRouter>

      <SockJSClient url='http://localhost:8080/websocketApp' topics={['/topic/javainuse']}
            onMessage={handleMessage}
            onConnect={()=>console.log("Socket connected!")}
            onDisconnect={()=>console.log("Socket disconnected!")}
            ref={ stompNotificationClient } />

      <WebSocketContext.Provider value={ stompNotificationClient }>
        <LudoAppBar title='Ludo'></LudoAppBar>
        <Box sx={{height: 100}}></Box>
        <Container maxWidth='xl'>
          <Routes>
            <Route path='/' element ={<HomePage />}/>
            <Route path='/game' element={<GamePage />}/>
            <Route path='/login' element={<LoginPage/>}/>
            <Route path='/my-games' element={<MyGamesPage/>}/>
            <Route path='/friends' element={<FriendsPage/>}/>
            <Route path='/register' element={<RegisterPage />}/>
            <Route path='/lobby' element={<LobbyPage />}/>
            <Route path="*" element ={<NotFoundPage />} />
          </Routes>
        </Container>
        {
          friendRequestNotifications.map((playerFriendInvite, index) => 
          <FriendInviteNotification playerFriendInvite={playerFriendInvite}></FriendInviteNotification>)
        }

      </WebSocketContext.Provider>
    </BrowserRouter>
  );
}

export default App;

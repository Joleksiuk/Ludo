import React, { useRef } from "react";
import "./App.css";
import LudoAppBar from "./components/layout/LudoAppBar";
import { Box, Container } from "@mui/material";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "./components/pages/HomePage";
import LobbyPage from "./components/pages/LobbyPage";
import GamePage from "./components/pages/GamePage";
import LoginPage from "./components/pages/LoginPage";
import MyGamesPage from "./components/pages/MyGamesPage";
import FriendsPage from "./components/pages/FriendsPage";
import RegisterPage from "./components/pages/RegisterPage";
import NotFoundPage from "./components/pages/NotFoundPage";
import ProfilePage from "./components/pages/ProfilePage";
import { GameIdContext } from "./components/GameIdProvider";
import { StompSessionProvider } from "react-stomp-hooks";
import NotificationStack from "./components/notifications/NotificationStack";
import RedirectToLogin from "./components/RedirectToLogin";
import GameInviteNotification from "./components/notifications/GameInviteNotification";

function App() {
  const gameId = useRef<number>();

  return (
    <StompSessionProvider url={"http://localhost:8080/websocketApp"}>
        <BrowserRouter>
          <LudoAppBar title="Ludo"></LudoAppBar>
          <Box sx={{ height: 100 }}></Box>
          <Container maxWidth="xl">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/game/:id" element={<GamePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/my-games" element={<MyGamesPage />} />
              <Route path="/friends" element={<FriendsPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/lobby/:id" element={<LobbyPage />} />
              <Route path="/profile" element={<ProfilePage />} />
              <Route path="*" element={<NotFoundPage />} />
              <Route path="/redirect" element={<RedirectToLogin />} />
            </Routes>
          </Container>
          <NotificationStack></NotificationStack>
        </BrowserRouter>
    </StompSessionProvider>
  );
}

export default App;

import React from 'react';
import './App.css';
import AddGameForm from './components/AddGameForm';
import GameList from './components/GameList';
import FriendList from "./components/Friendlist";
import PlayerGameInviteList from "./components/GameInvites";
import PlayerFriendInviteList from "./components/FriendInvites";
import PlayerList from "./components/Players";
import Login from "./components/Login";

function App() {
  return (
    
    <div className="App">
        <img src = "https://c.tenor.com/B8rKEjDfDjwAAAAd/cat-clown.gif"></img>
        <Login></Login>
        <GameList></GameList>
        <AddGameForm></AddGameForm>
        <PlayerList></PlayerList>
        <PlayerFriendInviteList></PlayerFriendInviteList>
        <FriendList></FriendList>
        <PlayerGameInviteList></PlayerGameInviteList>

    </div>
  );
}

export default App;

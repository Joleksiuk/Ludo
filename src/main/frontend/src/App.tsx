import React from 'react';
import './App.css';
import AddGameForm from './components/AddGameForm';
import GameList from './components/GameList';
import FriendList from "./components/Friendlist";
import PlayerGameInviteList from "./components/GameInvites";
import PlayerFriendInviteList from "./components/FriendInvites";
import PlayerList from "./components/Players";

function App() {
  return (
    <div className="App">
        <img src = "https://c.tenor.com/B8rKEjDfDjwAAAAd/cat-clown.gif" alt="smieszny kotek"></img>
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

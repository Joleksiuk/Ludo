import React, { Fragment } from 'react';
import './App.css';
import AddGameForm from './components/AddGameForm';
import GameList from './components/GameList';
import FriendList from "./components/Friendlist";
import PlayerGameInviteList from "./components/GameInvites";
import PlayerFriendInviteList from "./components/FriendInvites";
import PlayerList from "./components/Players";
import Chat from './components/chat/Chat';

function App() {
  return (
    <div className="App">
      <Chat></Chat>
      <AddGameForm></AddGameForm>
      <PlayerList></PlayerList>
      <PlayerFriendInviteList></PlayerFriendInviteList>
      <FriendList></FriendList>
      <PlayerGameInviteList></PlayerGameInviteList>  
      <img src = "https://c.tenor.com/B8rKEjDfDjwAAAAd/cat-clown.gif" alt="smieszny kotek"></img>    
    </div>
  );
}

export default App;

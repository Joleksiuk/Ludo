import React from 'react';
import './App.css';
import AddGameForm from './components/AddGameForm';
import GameList from './components/GameList';
import FriendList from "./components/Friendlist";
import PlayerGameInviteList from "./components/GameInvites";
import PlayerFriendInviteList from "./components/FriendInvites";
import PlayerList from "./components/Players";
import Board from './components/board/Board';

function App() {
  return (
    <div className="App">
        <GameList></GameList>
        <AddGameForm></AddGameForm>
        <PlayerList></PlayerList>
        <PlayerFriendInviteList></PlayerFriendInviteList>
        <FriendList></FriendList>
        <PlayerGameInviteList></PlayerGameInviteList>
        <Board gameId={1}></Board>
    </div>
  );
}

export default App;

import React from 'react'
import Chat from '../WebSockets/Chat'
import FriendInviteNotif from '../WebSockets/FriendInviteNotif'

export default function LobbyPage() {
  return (
    <div> 
      LobbyPage
      <FriendInviteNotif></FriendInviteNotif>
      <Chat></Chat>
    </div>
    
  )
}

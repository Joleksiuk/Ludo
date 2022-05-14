import React from 'react'

export default function Login(){
    const handleMessage = (event: MessageEvent) => {
        console.log(event)
    }

    function onClick () {
        window.location.href = "https://discord.com/api/oauth2/authorize?client_id=974726911223808080&redirect_uri=http%3A%2F%2Flocalhost%3A3000&response_type=code&scope=identify"
        window.addEventListener('message', handleMessage, false)
    }

    return(
        <div>
            <label htmlFor="username">Nickname</label>
            <input placeholder="Nickname"/>
            <button>Login</button>
            <button onClick={onClick}>Login with Discord</button>
        </div>
    );
}
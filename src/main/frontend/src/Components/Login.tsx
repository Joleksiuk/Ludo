import React, {useRef, useState, useEffect, useContext} from 'react'
import {Player, AuthState} from '../data-interfaces'
import AuthContext from '../context/AuthProvider'
import axios from '../axios'

const LOGIN_URL = '/auth'

export default function Login(){
    const {setAuth}= useContext(AuthContext);

    const [success, setSuccess] = useState<boolean>();
    const [nickname, setnickname] =  useState<string>()

    const handleMessage = (event: MessageEvent) => {
            console.log(event)
        }

    function onClick () {
        window.location.href = "https://discord.com/api/oauth2/authorize?client_id=974726911223808080&redirect_uri=http%3A%2F%2Flocalhost%3A3000&response_type=code&scope=identify"
        window.addEventListener('message', handleMessage, false)
    }

    const handleSubmit = (event: React.FormEvent): void => {
        event.preventDefault();
        axios.post(LOGIN_URL, JSON.stringify({ nickname}), { headers: {'Content-Type': 'application/json',Accept: 'application/json',},}, )
        .then(response => {
            console.log(JSON.stringify(response?.data));
            const accessToken = response.data.accessToken;
            const roles = response.data.roles;
            setAuth({nickname:nickname, accessToken:accessToken});
        })
        .catch(error => console.log(error))

    }

    return(
        <form onSubmit={event => handleSubmit(event)}>
            <div>
                    <label htmlFor="username">Nickname</label>
                    <input type="text" placeholder="Nickname" id="nickname" onChange={(e) => setnickname(e.target.value)}></input>
                    <button type="submit">Login</button>
                    <button onClick={onClick}>Login with Discord</button>
            </div>
        </form>
    );
}
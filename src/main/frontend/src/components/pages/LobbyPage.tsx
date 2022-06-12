import { Avatar, Button, Card, CardHeader, Grid, List, ListItem, ListItemAvatar, ListItemButton, ListItemText, ListSubheader, CardContent, Box } from '@mui/material';
import axios from '../../axios'
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Game, GamePlayer, Lobby, LobbyStatusMessage, Player } from '../../data-interfaces';
import SendIcon from '@mui/icons-material/Send'
import { useStompClient, useSubscription } from 'react-stomp-hooks';
import authService from '../../services/auth.service';
import Countdown from 'react-countdown';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import './LobbyPage.css';

export default function LobbyPage() {

  const { id } = useParams();
  const navigate = useNavigate();
  const stompClient = useStompClient();

  const [invitedPlayers, setInvitedPlayers] = useState<Player[]>();
  const [gameStarted, setGameStarted] = useState<boolean>(true);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [timerStart, setTimerStart] = useState<boolean>(false);
  const [colorList, setColorList] = useState<string[]>(["red", "green", "blue", "yellow"]);
  const [lobby, setLobby]=useState<Lobby>();

  const [lobbyPlayerList, setLobbyPlayerList] = useState<Player[]>([]);
  const [lobbyMap, setLobbyMap]=useState<Map<string,string>>();

  const gameIdDefined = () => id !== null;

  useSubscription(`/topic/game-start.lobby.${id}`, (message) => handleStartGameWebSocketMessage(JSON.parse(message.body)));
  useSubscription(`/topic/color-pick.lobby.${id}`, (message) => handleColorPickWebSocketMessage(JSON.parse(message.body)));

  const handleColorPickWebSocketMessage=(message:LobbyStatusMessage)=>{

    updateLobbyPlayerColor(message.color,message.playerId);
  }

  const handleStartGameWebSocketMessage=(message:LobbyStatusMessage)=>{

    setGameStarted(true);
    setTimerStart(true);
    setModalOpen(true);

  }

  //updating lobby players list when a player changes color
  const updateLobbyPlayerColor=(color:string, playerId:number)=>{

    setLobbyMap(new Map(lobbyMap.set(playerId.toString(),color)));

  }

  const getMapColorToPlayer=():Map<string,string>=>{
    return  new Map(Object.entries(lobby.mapColorToPlayer));
  }

  //rendering color pick buttons
  const renderColorList = (player: Player) => {
    return (
    <Grid container spacing={0}>
      {colorList.map((color) =>
        <Grid item xs>
          {lobbyMap?.get(player.id.toString()) === color
            ?
            <div className='picked'>
              <Button onClick={(event) => authService.isPlayerLoggedIn()&& handleColorPick(color, player)}><div className={'circle ' + color}></div></Button>
            </div>
            :<div>
              <Button onClick={(event) => authService.isPlayerLoggedIn()&& handleColorPick(color, player)}><div className={'circle ' + color}></div></Button>
            </div>
          }
        </Grid>)}
    </Grid>
    )
    }


  //loading data from API at site reload
  useEffect(() => {
    axios.get<Player[]>("lobby/invited/" + id)
      .then((response) => { setInvitedPlayers(response.data); })
      .catch((error) => console.log(error));

    axios.get<Game>("games/" + id)
      .then((response) => { handleGameData(response.data) })
      .catch((error) => console.log(error));

      axios.get<Lobby>("lobby/"+id)
      .then((response)=>{ 
        setLobby(response.data); console.log(response.data);
        const map = new Map(Object.entries(response.data.mapColorToPlayer));
        setLobbyMap(map);
        setLobbyPlayerList(response.data.players);
      })
      .catch((error)=>console.log(error));
      setIsLoading(false);

  }, []);


  const handleGameData = (game: Game) => {
    if (game.startDate === null || game.startDate === undefined) {
      setGameStarted(false);
    }
  }

  const handleStartGame = (event) => {
    axios.put<number>("games/" + id + "/start").catch((error) => console.log(error));

    setGameStarted(true);
    setTimerStart(true);
    setModalOpen(true);

    if (stompClient && gameIdDefined()) {
      stompClient.publish({ destination: `/app/lobby/${id}/game-start` });
    }
  }


  const handleColorPick = (color: string, player: Player) => {

    if(player.id==authService.getCurrentPlayer().id){

      updateLobbyPlayerColor(color,player.id);

      const msg = JSON.stringify({
        playerId: player.id,
        gameId: id,
        playerColour: color,
      });
  
     if (stompClient && gameIdDefined()) {
        stompClient.publish({ destination: `/app/lobby/${id}/color-picked`, body: msg });
     }
    }
  }

  const handleGoToGame = (event) => {
    navigate("/game/" + id);
  }

  const renderer = ({ hours, minutes, seconds, completed }) => {
    if (completed) {
      navigate("/game/" + id);
    } else {
      return <span>{seconds}</span>;
    }
  };

  const [modalOpen, setModalOpen] = useState(false);
  const handleModalOpen = () => setModalOpen(true);
  const handleModalClose = () => setModalOpen(false);

  const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };

  return (
    <div>
      <h3>This is the lobby of game : {id}</h3>

      {gameStarted && !isLoading
        ? <Button variant="contained" endIcon={<SendIcon />} size="large" onClick={(event) => handleGoToGame(event)}>Go to game</Button>
        : <Button variant="contained" color="success" size="large" onClick={(event) => handleStartGame(event)}>Start Game</Button>
      }

      <Box sx={{ m: "2rem" }} />
      <Grid container spacing={3}>

        <Grid item xs>
          <Card>
            <CardHeader title="Game Players"></CardHeader>
            <CardContent>
              <List>               
                {lobby?.players.map((player)=>(
                  <ListItem key={player.id+"1"}>
                  <Grid container spacing={3}>

                    <ListItemButton>
                      <Grid item xs>
                        <ListItemAvatar>
                          <Avatar src={player.picture}></Avatar>
                        </ListItemAvatar>
                      </Grid>
                      <Grid item xs>
                        <Typography>{player.nickname}</Typography>
                      </Grid>
                    </ListItemButton>

                    <Grid item xs={8}>
                      {renderColorList(player)}
                    </Grid>

                  </Grid>
                  <Box sx={{ m: "2rem" }} />
                </ListItem>
                ))}
              </List>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs>
          <Card>
            <CardHeader title="Invited players"></CardHeader>
            <CardContent>
              <List>
                {invitedPlayers?.map((player) => (
                  <ListItem key={player.id+"2"}>
                    <Grid container spacing={3}>

                      <ListItemButton>
                        <Grid item xs>
                          <ListItemAvatar>
                            <Avatar src={player.picture}></Avatar>
                          </ListItemAvatar>
                        </Grid>
                        <Grid item xs>
                          <Typography>{player.nickname}</Typography>
                        </Grid>
                      </ListItemButton>

                    </Grid>
                  </ListItem>
                ))}
              </List>
            </CardContent>
          </Card>
        </Grid>

      </Grid>

      <Modal
        open={modalOpen}
        onClose={handleModalClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            The game will start in:
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            <Countdown
              autoStart={timerStart}
              date={Date.now() + 5000}
              renderer={renderer}
            />
          </Typography>
        </Box>
      </Modal>

    </div>
  );
}

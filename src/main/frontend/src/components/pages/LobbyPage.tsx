import { Avatar, Button, Card, CardHeader, Grid, List, ListItem, ListItemAvatar, ListItemButton, ListItemText, ListSubheader, CardContent, Box, SnackbarContent, Snackbar } from '@mui/material';
import axios from '../../ludo-axios'
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Game, GamePlayer, Lobby, LobbyModel, LobbyStatusMessage, Player } from '../../data-interfaces';
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
  const [game, setGame] = useState<Game>();
  const [colorList, setColorList] = useState<string[]>(["red", "green", "blue", "yellow"]);
  const [snackbarOpen, setSnackbarOpen] = useState<boolean>(false)
  const [snackbarMessage, setSnackbarMessage] = useState<string>('')

  const [lobbyModels,setLobbyModels]=useState<LobbyModel[]>([]);

  const gameIdDefined = () => id !== null;

  useSubscription(`/topic/game-start.lobby.${id}`, (message) => handleStartGameWebSocketMessage(JSON.parse(message.body)));
  useSubscription(`/topic/color-pick.lobby.${id}`, (message) => handleColorPickWebSocketMessage(JSON.parse(message.body)));

  const handleColorPickWebSocketMessage=(message:LobbyStatusMessage)=>{

    updateLobbyPlayerColor(message.playerColour,message.playerId);
  }

  const handleStartGameWebSocketMessage=(message:LobbyStatusMessage)=>{

    setGameStarted(true);
    setTimerStart(true);
    setModalOpen(true);

  }

  //updating lobby players list when a player changes color
  const updateLobbyPlayerColor=(color:string, playerId:number)=>{


    setLobbyModels((previousModels)=>{
      const lobbyModel= previousModels.filter(x=>x.playerId ==playerId)[0];
      const index =  previousModels.indexOf(lobbyModel);
      lobbyModel.color =color;
      const newArray = previousModels;
      newArray[index] = lobbyModel;
      return [... newArray];
    });

  }


  //rendering color pick buttons
  const renderColorList = (player:LobbyModel) => {
    return (
    <Grid container alignItems='center' justifyContent={'center'} spacing={0}>
      {colorList.map((color) =>
        <Grid item xs>
          {player.color === color
            ?
            <div className='picked'>
              <Button onClick={(event) => authService.isPlayerLoggedIn()&& handleColorPick(color, player.playerId)}><div className={'circle ' + color}></div></Button>
            </div>
            :<div>
              <Button onClick={(event) => authService.isPlayerLoggedIn()&& handleColorPick(color, player.playerId)}><div className={'circle ' + color}></div></Button>
            </div>
          }
        </Grid>)}
    </Grid>
    )
    }

    const handleJoin = (player: Player) => {
        const i = invitedPlayers.indexOf(player)
        const players = [...invitedPlayers]
        players.splice(i, 1)
        setInvitedPlayers(players)
    }

    useSubscription(`/topic/game.join.${id}`, message => handleJoin(JSON.parse(message.body)))


  //loading data from API at site reload
  useEffect(() => {
    axios.get<Player[]>("lobby/invited/" + id)
      .then((response) => { setInvitedPlayers(response.data); })
      .catch((error) => console.log(error));

    axios.get<Game>("games/" + id)
      .then((response) => { handleGameData(response.data) })
      .catch((error) => console.log(error));


      axios.get<LobbyModel[]>("lobby/models/"+id)
      .then((response)=>setLobbyModels(response.data))
      .catch((error)=>console.log(error));

      axios.get<string[]>("lobby/colors/"+id)
      .then((response)=>setColorList(response.data))
      .catch((error)=>console.log(error));
      
      setIsLoading(false);

  }, [invitedPlayers]);

  const showErrorInSnackbar = (err: string) => {
    setSnackbarMessage(err)
    setSnackbarOpen(true)
  }

  const handleGameData = (game: Game) => {
    if (game.startDate === null || game.startDate === undefined) {
      setGameStarted(false);
      setGame(game);
    }
  }

  const handleStartGame = (event) => {

    var startGame :boolean =true;
    if(lobbyModels.length !== colorList.length){
      startGame=false;
      showErrorInSnackbar("Wrong number of players!")
    }
    lobbyModels.forEach(x=>{
      if(x.color==="blank" || x.color==="null"){
        showErrorInSnackbar("Not everyone chose their color!")
        startGame=false;
      }
    })

    const selectedColors = [...lobbyModels.map(model => model.color)]
    const uniqueSelectedColors = [...selectedColors.filter((value, index, self) => self.indexOf(value) === index)]
    if (selectedColors.length !== uniqueSelectedColors.length) {
      startGame = false;
      showErrorInSnackbar("Colors are not unique!")
    }

    if(startGame){

      setGameStarted(true);
      setTimerStart(true);
      setModalOpen(true);
  
      const msg = JSON.stringify(lobbyModels);
      if (stompClient && gameIdDefined()) {
        stompClient.publish({ destination: `/app/lobby/${id}/game-start`, body: msg});
      }
    }  
  }


  const handleColorPick = (color: string, playerId: number) => {


    if(playerId==authService.getCurrentPlayer().id){

      const msg = JSON.stringify({
        playerId: playerId,
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
      <h3>This is the lobby of game : {game?.name}</h3>

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
                {lobbyModels?.map((player)=>(
                  <ListItem key={player.playerId+"1"}>
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
      <Snackbar
        open={snackbarOpen}
        onClose={() => setSnackbarOpen(false)}
        key={"bottomright"}
      >
        <SnackbarContent message={snackbarMessage}/>
      </Snackbar>

    </div>
  );
}

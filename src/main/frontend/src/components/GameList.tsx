import {
  Box,
  Button,
  ButtonGroup,
  Modal,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import React from "react";
import ludoAxios from "../ludo-axios";
import AddGameForm from "./AddGameForm";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import { Game } from "../data-interfaces";
import { GameIdContext } from "./GameIdProvider";
import FriendlistToInviteToGame from "./FriendlistToInviteToGame";
import authService from "../services/auth.service";

const modalStyle: React.CSSProperties = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  backgroundColor: "white",
  border: "none",
};

const DATE_FORMAT: string = "DD.MM.yyyy HH:mm";

export default function GameList() {
  const API_URL: string = "games";

  const [games, setGames] = React.useState<
    Array<Game>
  >([]);
  const [formModalOpen, setFormModalOpen] = React.useState<boolean>(false);
  const [gameModalOpen, setGameModalOpen] = React.useState<boolean>(false);
  const [listUpdated, setListUpdated] = React.useState<boolean>(false);
  const [selectedGame, setSelectedGame] = React.useState<Game>()

  const navigate = useNavigate();

  const navigateToLobby=(game:Game)=>{
    navigate("/lobby/"+game.id);
  }

  const navigateToGame = (clickedGame: Game) => {
    navigate("/game/"+clickedGame.id)
  }

  const handleOpenAddEditModal = (add: boolean) => {
    setFormModalOpen(true);
  };
  const handleCloseAddEditModal = () => {
    setFormModalOpen(false);
    setListUpdated(true);
  };

  const handleGameModalOpen = (game: Game) => {
    setSelectedGame(game)
    setGameModalOpen(true)
  }

  React.useEffect(() => {
    ludoAxios
      .get(API_URL + `/player/${authService.getCurrentPlayer().id}`)
      .then((response) => response.data)
      .then((data) => setGames(data))
      .then(() => setListUpdated(false));
  }, [listUpdated]);

  const Row = (game: Game) => {
    return (
      <TableRow key={game.id}>
        <TableCell>{game.name}</TableCell>
        <TableCell>
          {game.startDate !== null 
            ?moment(game.startDate).format(DATE_FORMAT)
            : '-'}
        </TableCell>
        <TableCell>
          <ButtonGroup>
            <Button onClick={() => handleGameModalOpen(game)}>Invite friends</Button>
            {game.startDate !== null
              ? <Button onClick={() => navigateToGame(game)}>Go to game</Button>
              : <Button onClick={() => navigateToLobby(game)}>Go to lobby</Button>
            }
          </ButtonGroup>
        </TableCell>
      </TableRow>
    );
  };

  const FormModal = () => {
    return (
      <Modal open={formModalOpen} onClose={handleCloseAddEditModal}>
        <Box style={modalStyle}>
          <AddGameForm
            onSave={handleCloseAddEditModal}
          ></AddGameForm>
        </Box>
      </Modal>
    );
  };

  const InviteModal = () => {
    return (
      <Modal open={gameModalOpen} onClose={() => setGameModalOpen(false)}>
        <Box style={modalStyle}>
          <FriendlistToInviteToGame gameId={gameModalOpen && selectedGame.id}></FriendlistToInviteToGame>
        </Box>
      </Modal>
    )
  }

  return (
    <Box>
      <Button variant="contained" style={{margin: '20px'}} onClick={() => handleOpenAddEditModal(true)}>
        New game 
      </Button>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Started at</TableCell>
              <TableCell>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>{games.map((session) => Row(session))}</TableBody>
        </Table>
      </TableContainer>
      <FormModal></FormModal>
      <InviteModal></InviteModal>
    </Box>
  );
}

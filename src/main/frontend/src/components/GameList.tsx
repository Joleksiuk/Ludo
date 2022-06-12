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
import { Link, Navigate, useNavigate } from "react-router-dom";
import moment from "moment";
import { Game } from "../data-interfaces";
import { GameIdContext } from "./GameIdProvider";
import { Navigate } from "react-router-dom";

const modalStyle: React.CSSProperties = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  backgroundColor: "white",
  border: "none",
};

const DATE_FORMAT: string = "DD.MM.yyyy hh:mm";

export default function VotingSessionList() {
  const API_URL: string = "games";

  const [games, setGames] = React.useState<
    Array<Game>
  >([]);
  const [modalOpen, setModalOpen] = React.useState<boolean>(false);
  const [isFormAdd, setIsAdd] = React.useState<boolean>(true);
  const [selected, setSelected] = React.useState<Game | undefined>();
  const [listUpdated, setListUpdated] = React.useState<boolean>(false);
  const [redirectToGame, setRedirectToGame] = React.useState<boolean>(false)
  const gameId = React.useContext(GameIdContext)

  const navigate = useNavigate();

  const navigateToLobby=(game:Game)=>{
    navigate("/lobby/"+game.id);
  }

  const handleGameSelect = (clickedGame: Game) => {
    gameId.current = clickedGame.id;
    setRedirectToGame(true);
  }

  const handleOpenAddEditModal = (add: boolean) => {
    setModalOpen(true);
    setIsAdd(add);
  };
  const handleCloseAddEditModal = () => {
    setModalOpen(false);
    setSelected(undefined);
    setListUpdated(true);
  };

  React.useEffect(() => {
    ludoAxios
      .get(API_URL)
      .then((response) => response.data)
      .then((data) => setGames(data))
      .then(() => setListUpdated(false));
  }, [listUpdated]);

  const handleEdit = (game: Game) => {
    setSelected(game);
    handleOpenAddEditModal(false);
  };
  const handleDelete = (game: Game) => {
    ludoAxios
      .delete(API_URL + "/" + game.id)
      .then(() => setListUpdated(true));
  };

  const Row = (game: Game) => {
    return (
      <TableRow key={game.id}>
        <TableCell>{game.name}</TableCell>
        <TableCell>
          //TODO add date
          {moment().format(DATE_FORMAT)}
        </TableCell>
        <TableCell>
          <ButtonGroup>
            <Button onClick={() => handleEdit(game)}>Edit</Button>
            <Button onClick={() => handleDelete(game)}>Delete</Button>
            <Button onClick={() => handleGameSelect(game)}>Go to game</Button>
            <Button onClick={() => navigateToLobby(game)}>Go to lobby</Button>
          </ButtonGroup>
        </TableCell>
      </TableRow>
    );
  };

  const FormModal = () => {
    return (
      <Modal open={modalOpen} onClose={handleCloseAddEditModal}>
        <Box style={modalStyle}>
          <AddGameForm
            onSave={handleCloseAddEditModal}
          ></AddGameForm>
        </Box>
      </Modal>
    );
  };

  return (
    <Box>
      <Button variant="contained" style={{margin: '20px'}} onClick={() => handleOpenAddEditModal(true)}>
        Add new
      </Button>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Creation date</TableCell>
              <TableCell>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>{games.map((session) => Row(session))}</TableBody>
        </Table>
      </TableContainer>
      <FormModal></FormModal>
      { redirectToGame && <Navigate to='/game'/> }
    </Box>
  );
}

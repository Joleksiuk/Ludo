import React, { useEffect, useState } from "react";
import BoardField from "./BoardField";
import { BoardMove, Field, PawnInfo, Player } from "../../data-interfaces";
import axios from "../../ludo-axios";
import Pawn from "./Pawn";
import { Box, Modal } from "@mui/material";
import { useStompClient, useSubscription } from "react-stomp-hooks";
import authService from "../../services/auth.service";
import authHeader from "../../services/auth.header";
import WinnerPrompt from "../WinnerPrompt";
import { RepeatOnSharp } from "@mui/icons-material";

interface BoardProps {
  gameId: number;
}

const modalStyle: React.CSSProperties = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600, 
  backgroundColor: "white",
  border: "none",
};

export default function Board(props: BoardProps) {
  const [fieldsMatrix, setFieldsMatrix] = useState<Array<Array<Field>>>(
    new Array<Array<Field>>()
  );
  const [pawns, setPawns] = useState<Array<PawnInfo>>();
  const [possibleMoves, setPossibleMoves] = useState<Array<BoardMove>>([]);
  const [winModalOpen, setWinModalOpen] = useState<boolean>(false);
  const [winner, setWinner] = useState<Player | undefined>();

  const stompClient = useStompClient();

  useSubscription(`/topic/game-move.${props.gameId}`, (message) =>
    handleMove(JSON.parse(message.body))
  );

  useSubscription(`/topic/game-winner.${props.gameId}`, message =>
    handleWinner(JSON.parse(message.body)) 
  );

  const handlePawnClick = (pawn: PawnInfo) => {
    stompClient.publish({
      destination: `/app/game/${props.gameId}/move`,
      headers: authHeader(),
      body: JSON.stringify(pawn),
    });
  };

  const handleWinner = (winner: Player) => {
    if (winner.nickname !== undefined) {
      setWinModalOpen(true);
      setWinner(winner);
    }
  }

  const handleMove = (moves: Array<BoardMove>) => {
    console.log(moves)
    moves.forEach(move => {
    setPawns(currentPawns => {
        const index = currentPawns.findIndex(
          (pawn) => pawn.fieldId === move.fromFieldId
        );
        let pawn = currentPawns[index];
        currentPawns[index] = new PawnInfo(
          pawn.color,
          move.toFieldId,
          pawn.number
        );
        return [...currentPawns];
      });
    });
    setPossibleMoves([]);
  };

  const renderPawnForField = (field: Field) => {
    const pawn = pawns?.find((pawn) => pawn.fieldId === field.id);
    return pawn === undefined ? (
      <></>
    ) : (
      <Pawn
        onClick={() =>
          isFieldHighlighted(field)
            ? handlePawnClick(pawn)
            : Function.prototype()
        }
        key={field.id}
        color={pawn.color}
        highlighted={isFieldHighlighted(field)}
      ></Pawn>
    );
  };

  const renderRowContent = (fields: Field[]) => {
    return fields.map((field) => (
      <BoardField key={field.id} field={field}>
        {renderPawnForField(field)}
      </BoardField>
    ));
  };

  const isFieldHighlighted = (field: Field) =>
    possibleMoves.filter(
      (possibleMove) => possibleMove.fromFieldId === field.id
    ).length > 0;

  const handlePossibleMoves = (message: Array<BoardMove>) => {
    setPossibleMoves(message);
  };

  useEffect(() => {
    axios
      .get<Array<Array<Field>>>("games/" + props.gameId + "/board/fields")
      .then((response) => response.data)
      .then((data) => setFieldsMatrix(data));

    axios
      .get<Array<PawnInfo>>("games/" + props.gameId + "/pawns")
      .then((response) => response.data)
      .then((data) => setPawns(data));

    axios
      .get<Array<BoardMove>>("games/" + props.gameId + "/possible-moves")
      .then((response) => response.data)
      .then(setPossibleMoves);

    axios
      .get<Player>("games/" + props.gameId + "/winner")
      .then(response => response.data)
      .then(winner => handleWinner(winner))
  }, [props.gameId]);

  useSubscription(
    `/topic/game-possible-move.${props.gameId}.${
      authService.getCurrentPlayer().nickname
    }`,
    (message) => handlePossibleMoves(JSON.parse(message.body))
  );

  return (
    <Box>
      <table>
        <tbody>
          {fieldsMatrix != null ? (
            fieldsMatrix.map((row) => (
              <tr key={fieldsMatrix.indexOf(row)}>{renderRowContent(row)}</tr>
            ))
          ) : (
            <tr>
              <td>loading</td>
            </tr>
          )}
        </tbody>
      </table>
      <Modal
        open={winModalOpen}
        onClose={Function.prototype()}>
          <Box style={modalStyle}>
            <WinnerPrompt onButtonClick={() => setWinModalOpen(false)} player={winner}></WinnerPrompt>
          </Box>
      </Modal>
    </Box>
  );
}

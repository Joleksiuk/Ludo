import React, { useEffect, useState } from 'react'
import BoardField from './BoardField';
import { Field, PawnInfo } from '../../data-interfaces'
import axios from '../../axios';
import Pawn from './Pawn';
import { Box } from '@mui/material';

interface BoardProps {
  gameId: number
}


export default function Board(props: BoardProps) {

  const [fieldsMatrix, setFieldsMatrix] = useState<Array<Array<Field>>>(new Array<Array<Field>>())
  const [pawns, setPawns] = useState<Array<PawnInfo>>()

  const renderPawnForField = (field: Field) => {
    const pawn = pawns?.find(pawn => pawn.fieldId === field.id);
    return (
      pawn === undefined ? <></> : <Pawn key={field.id} color={pawn.color}></Pawn>
    )
  }

  const renderRowContent = (fields: Field[]) => {
    return (
      fields.map(field => <BoardField key={field.id} field={field}>{renderPawnForField(field)}</BoardField>)
    )
  }

  useEffect(() => {
    axios.get<Array<Array<Field>>>('game/' + props.gameId + '/board/fields')
      .then(response => response.data)
      .then(data => setFieldsMatrix(data));

    axios.get<Array<PawnInfo>>('game/' + props.gameId + '/pawns')
      .then(response => response.data)
      .then(data => setPawns(data));
  }, [props.gameId])

  return (
    <Box>
      <table>
        <tbody>
          {fieldsMatrix != null ? fieldsMatrix.map(row => <tr key = {fieldsMatrix.indexOf(row)}>{renderRowContent(row)}</tr>) : <tr><td>loading</td></tr>}
        </tbody>
      </table>
    </Box>
  )
}

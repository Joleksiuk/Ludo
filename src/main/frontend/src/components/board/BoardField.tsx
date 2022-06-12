import React from 'react'
import './BoardField.css'
import { Field } from '../../data-interfaces'

interface BoardFieldProps {
  field: Field
  children: JSX.Element
}


export default function BoardField(props : BoardFieldProps) {

  const getBoardClass = (field: Field): string => {
     return 'BoardField' + (field.empty? '' : ' bordered ' + field.color) 
  }

  return (
    <td id={'BoardField-' + props.field.id} className={getBoardClass(props.field)}>
      {props.children}
    </td>

  )
}

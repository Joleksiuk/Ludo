import React from 'react'
import './Pawn.css'

interface PawnProps {
  color: string,
  highlighted?: boolean,
  onClick: () => void
}

export default function Pawn(props: PawnProps) {
  return (
    <div className={'Pawn ' + (props.highlighted ? 'highlighted' : props.color)} onClick={props.onClick}></div>
  )
}

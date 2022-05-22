import React from 'react'
import './Pawn.css'

interface PawnProps {
  color: string,
}

export default function Pawn(props: PawnProps) {
  return (
    <div className={'Pawn ' + props.color}></div>
  )
}

import React, { MutableRefObject } from 'react'

export const GameIdContext = React.createContext<MutableRefObject<number> | undefined>(undefined)

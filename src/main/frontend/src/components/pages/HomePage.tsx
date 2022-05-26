import { Stack } from '@mui/material'
import React from 'react'
import HomePageCard from '../HomePageCard';

export default function HomePage() {

  return (
    <Stack spacing = {3}>
        <HomePageCard title='Start game' buttonText='New game'></HomePageCard>
        <HomePageCard title='Friends and invites' buttonText='See friends'></HomePageCard>
        <HomePageCard title='Games' buttonText='Browse games'></HomePageCard>
    </Stack>
  )
}

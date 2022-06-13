import { Stack } from '@mui/material'
import React from 'react'
import HomePageCard from '../HomePageCard';

export default function HomePage() {

  return (
    <Stack spacing = {3}>
        <HomePageCard href='/profile' title='Edit profile' buttonText='Go to profile'></HomePageCard>
        <HomePageCard href='/friends' title='Friends and invites' buttonText='See friends'></HomePageCard>
        <HomePageCard href='/my-games' title='Games' buttonText='Browse games'></HomePageCard>
    </Stack>
  )
}

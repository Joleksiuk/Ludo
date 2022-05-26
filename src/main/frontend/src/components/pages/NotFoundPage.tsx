import { Button, Grid, Typography } from '@mui/material'
import React from 'react'

export default function NotFound() {
  return (
    <Grid container justifyContent='center' alignItems='center' minHeight={'100vh'} direction='column' rowSpacing={2}>
      <Grid item xs={5}>
        <Typography variant='h3'>Oops, we didn't find what you were looking for :(</Typography>
      </Grid>
      <Grid item xs={5}>
        <Button variant='contained' href='/'>Return home</Button>
      </Grid>
    </Grid>
  )
}

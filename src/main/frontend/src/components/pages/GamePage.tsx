import { Box, Grid, Paper, Stack, Typography } from '@mui/material'
import React from 'react'
import Board from '../board/Board'

export default function GamePage() {
  return (
    <Box sx={{padding: '20px'}}>
      <Grid container spacing={0}>
        <Grid item xs={9}>
          <Board gameId={1}></Board>
        </Grid>
        <Grid item xs={2}>
            <Paper sx={{height: '100%'}}>
              <Stack alignItems='center' spacing={2}>
                <Box>
                  <Typography>Dice component</Typography>
                </Box>
                <Box>
                  <Typography>Chat or other</Typography>
                </Box>
              </Stack>
            </Paper>
        </Grid>
      </Grid>
    </Box>
  )
}

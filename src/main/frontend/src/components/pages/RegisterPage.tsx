import {
  Box,
  Button,
  Grid,
  Link,
  Paper,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import React from 'react'

export default function RegisterPage() {
  return (
    <Grid container justifyContent="center" alignItems="center" height={'80vh'}>
      <Paper>
        <Box sx={{margin: '35px', minWidth:'300px'}}>
          <Stack spacing={2}>
            <Typography variant='h6' component='h2'>Register to our page</Typography>
            <TextField label="Login"></TextField>
            <TextField label='Password' type='password'></TextField>
            <TextField label='Repeat password' type='password'></TextField>
            <Button variant="contained">Register</Button>
          </Stack>
        </Box>
      </Paper>
    </Grid>
  )
}

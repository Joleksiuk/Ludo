import {
  Box,
  Button,
  Grid,
  Paper,
  Stack,
  TextField,
  Alert,
  Typography,
} from "@mui/material";
import React from 'react'
import { Navigate } from "react-router-dom";
import authService from '../../services/auth.service'

export default function RegisterPage() {

  const [nickname, setNickname] = React.useState('')
  const [email, setEmail] = React.useState('')
  const [password, setPassword] = React.useState('')
  const [passwordRepeat, setPasswordRepeat] = React.useState('')
  const [error, setError] = React.useState('')
  const [registerSuccessful, setRegisterSuccessful] = React.useState<boolean>(false)

  const handleSubmit = () => {
    if (password !== passwordRepeat) {
      setError('Passwords do not match');
    } else {
      authService.register(nickname,email, password)
          .then(() => setRegisterSuccessful(true))
          .catch(() => setError('Could not register'))
    }
  }

  return (
    <Grid container justifyContent="center" alignItems="center" height={'80vh'}>
      <Paper>
        <Box sx={{margin: '35px', minWidth:'300px'}}>
          <Stack spacing={2}>
            <Typography variant='h6' component='h2'>Register to our page</Typography>
            { error !== '' ? <Alert severity='error'>{error}</Alert> : <></>}
            <TextField label="Login" value={nickname} onChange={event => setNickname(event.target.value)}></TextField>
            <TextField label="Email" value={email} onChange={event => setEmail(event.target.value)}></TextField>
            <TextField label='Password' type='password' value={password} onChange={event => setPassword(event.target.value)}></TextField>
            <TextField label='Repeat password' type='password' value={passwordRepeat} onChange={event => setPasswordRepeat(event.target.value)}></TextField>
            <Button variant="contained" onClick={handleSubmit}>Register</Button>
          </Stack>
        </Box>
      </Paper>
      { registerSuccessful ? <Navigate to = '/login'/> : <></>}
    </Grid>
  )
}

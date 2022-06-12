import {
  Box,
  Button,
  Grid,
  Link,
  Paper,
  Alert,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import React from "react";
import authService from '../../services/auth.service';
import { Navigate } from 'react-router-dom';

export default function LoginPage() {

  const [nickname, setNickname] = React.useState<string>('')
  const [password, setPassword] = React.useState<string>('')
  const [loginSuccessful, setLoginSuccessful] = React.useState<boolean>(false)
  const [error, setError] = React.useState<string>('')

  const handleSubmit = () => {
    authService.login(nickname, password)
      .then(() => setLoginSuccessful(true))
      .catch(error => setError(error));
  }

  return (
    <Grid container justifyContent="center" alignItems="center" height={'80vh'}>
      <Paper>
        <Box sx={{margin: '35px', minWidth: '300px'}}>
          <Stack spacing={2}>
            <Typography variant='h6' component='h2'>Login to our page</Typography>
            { error !== '' ? <Alert severity="error">Invalid credencials</Alert> : <></>}
            <TextField label="Login" value={nickname} onChange={event => setNickname(event.target.value)}></TextField>
            <TextField label='Password' type='password' value={password} onChange={event => setPassword(event.target.value)}></TextField>
            <Button variant="outlined" onClick={handleSubmit}>Login</Button>
            <Box sx={{display: 'flex', justifyContent: 'center'}}>
              <Typography align="center" variant='button' color='grey'>or</Typography>
            </Box>
            <Button variant='outlined' href="/register">Register</Button>
          </Stack>
        </Box>
      </Paper>
      { loginSuccessful ? <Navigate to='/'/> : <></>}
    </Grid>
  );
}
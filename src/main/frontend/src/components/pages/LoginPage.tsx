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
import React from "react";

export default function LoginPage() {

  return (
    <Grid container justifyContent="center" alignItems="center" height={'80vh'}>
      <Paper>
        <Box sx={{margin: '35px', minWidth: '300px'}}>
          <Stack spacing={2}>
            <Typography variant='h6' component='h2'>Login to our page</Typography>
            <TextField label="Login"></TextField>
            <TextField label='Password' type='password'></TextField>
            <Button variant="outlined">Login</Button>
            <Box sx={{display: 'flex', justifyContent: 'center'}}>
              <Typography align="center" variant='button' color='grey'>or</Typography>
            </Box>
            <Button variant="outlined" className='discord-button'>Login with Discord</Button>
            <Button variant='outlined' href="/register">Register</Button>
          </Stack>
        </Box>
      </Paper>
    </Grid>
  );
}

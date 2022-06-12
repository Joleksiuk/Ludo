import {
    Button,
    Typography,
    Grid,
  } from "@mui/material";
import React from 'react'

export default function RedirectToLogin() {
    return(
     <Grid container justifyContent='center' alignItems='center' minHeight={'50vh'} direction='column' rowSpacing={2}>
           <Grid item xs={5}>
             <Typography variant='h3'>Log in to access that page</Typography>
           </Grid>
           <Grid item xs={5}>
             <Button variant='contained' href='/login'>Login</Button>
           </Grid>
     </Grid>
    );
}
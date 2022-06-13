import React, {Component, useEffect, useState} from 'react'
import authService from "../../services/auth.service";
import {Game, Player} from "../../data-interfaces";
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Fade from '@mui/material/Fade';
import {
    Avatar,
    Box,
    Button,
    Grid,
    Paper,
    TextField,
    Typography,
    Stack,
    Alert,
} from "@mui/material";
import axios from "../../ludo-axios";
import ImageList from "@mui/material/ImageList";
import {styled} from "@mui/material/styles";
import ButtonBase from "@mui/material/ButtonBase";
import {Navigate} from "react-router-dom";
import RedirectToLogin from "../RedirectToLogin";

export default function ProfilePage() {
    const [player, setPlayer] = React.useState<Player>();
    const [nickname, setNickname] = React.useState<string>('')
    const [picture, setPicture] = React.useState<string>('')
    const [email, setEmail] = React.useState<string>('')
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl)
    const [change, setChange] = React.useState<boolean>(false)

    const handleClick = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const getPlayer = () =>{
    axios
        .get<Player>("players/"+authService.getCurrentPlayer().id)
        .then((response) => {
        setNickname(response.data.nickname)
        setPicture(response.data.picture)
        setPlayer(response.data)
        })
        .catch((error) => console.log(error));
    }
    const handleGravatar = () => {
        axios
            .put<Player>("players/gravatar", {id: player.id, nickname: nickname, email:player.email })
        getPlayer()
    }

    const handleClose = (image: string) => {
        setAnchorEl(null)
        setPicture(image)
    };

    useEffect(() => {
        if(!authService.isPlayerLoggedIn()) return;
        getPlayer()
    }, []);

    const handleSubmit = () => {
        axios
            .put<Player>("players", {id: player.id, nickname: nickname, picture: picture, email:player.email })
            .then(() =>{
                setChange(true)
            } )
    }

    return (
    <>{authService.isPlayerLoggedIn() ? (
        <Grid container justifyContent="center" alignItems="left" height={'50vh'}>
                    <Paper>
                        { change ? <Alert severity="info">Changes saved</Alert> : <></>}
                        <Box sx={{margin: '35px', minWidth:'300px'}}>
                            <Stack spacing={3}>
                                <Typography justifyContent="center" variant='h3' component='h3'>My profile</Typography>
                                <Avatar
                                    sx={{ width: 300, height: 300}}
                                    src={picture}
                                />
                                <Button
                                    variant="contained"
                                    id="fade-button"
                                    aria-controls={open ? 'fade-menu' : undefined}
                                    aria-haspopup="true"
                                    aria-expanded={open ? 'true' : undefined}
                                    onClick={handleClick}
                                >
                                    Change avatar
                                </Button>
                                <Button variant="contained" onClick={handleGravatar}>Avatar from the Gravatar</Button>
                                <Menu
                                    id="fade-menu"
                                    MenuListProps={{
                                        'aria-labelledby': 'fade-button',
                                    }}
                                    anchorEl={anchorEl}
                                    open={open}
                                    onClose={() =>handleClose(picture)}
                                    TransitionComponent={Fade}
                                >
                                    <Button variant="contained" onClick={() => handleClose(picture)}>Close</Button>
                                    <ImageList sx={{ width: 700, height: 400 }} cols={4} rowHeight={100}>
                                        {images.map((image) => (
                                            <ImageButton key={image} style={{ width: image,}} >
                                                <ImageSrc style={{ backgroundImage: `url(${image})` }} />
                                                <Button sx={{width:100, height:100}} onClick={() => handleClose(image)}></Button>
                                            </ImageButton>
                                        ))}
                                    </ImageList>
                                    <Button variant="contained" onClick={() => handleClose(null)}>Delete</Button>
                                </Menu>
                                <Typography variant='h5' component='h5'>Nickname</Typography>
                                <TextField value={nickname} onChange = {(event) => setNickname(event.target.value)}  ></TextField>
                                <Button variant="contained" onClick={handleSubmit} >Save changes</Button>
                            </Stack>
                        </Box>
                    </Paper>
                </Grid>
    ): <Navigate to ='/redirect'/>} </>
    );
}

const images = [
    "https://cdn-icons-png.flaticon.com/512/4128/4128405.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128309.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128400.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128380.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128190.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128335.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128337.png",
    "https://cdn-icons-png.flaticon.com/512/4128/4128369.png",
    "https://cdn-icons-png.flaticon.com/512/3940/3940403.png",
    "https://cdn-icons-png.flaticon.com/512/2829/2829751.png",
    "https://cdn-icons-png.flaticon.com/512/4322/4322992.png",
    "https://cdn-icons-png.flaticon.com/512/2829/2829679.png",
    "https://cdn-icons-png.flaticon.com/512/3940/3940405.png",
    "https://cdn-icons-png.flaticon.com/512/4322/4322991.png",
    "https://cdn-icons-png.flaticon.com/512/2829/2829664.png",
    "https://cdn-icons-png.flaticon.com/512/780/780260.png",
    "https://cdn-icons-png.flaticon.com/512/4605/4605649.png",
    "https://cdn-icons-png.flaticon.com/512/4605/4605647.png",
    "https://cdn-icons-png.flaticon.com/512/4605/4605676.png",
    "https://cdn-icons-png.flaticon.com/512/4605/4605669.png",
    "https://cdn-icons-png.flaticon.com/512/4605/4605670.png",
    "https://cdn-icons-png.flaticon.com/512/4605/4605650.png",
    "https://cdn-icons-png.flaticon.com/512/316/316340.png",
    "https://cdn-icons-png.flaticon.com/512/316/316329.png",
]

const ImageButton = styled(ButtonBase)(({ theme }) => ({
    position: 'relative',
    height: 200,
    [theme.breakpoints.down('sm')]: {
        width: '100% !important',
        height: 200,
    },
    '&:hover, &.Mui-focusVisible': {
        zIndex: 1,
        '& .MuiImageBackdrop-root': {
            opacity: 0.15,
        },
        '& .MuiImageMarked-root': {
            opacity: 0,
        },
        '& .MuiTypography-root': {
            border: '4px solid currentColor',
        },
    },
}));

const ImageSrc = styled('span')({
    position: 'absolute',
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    backgroundSize: 'cover',
    backgroundPosition: 'center 40%',
});
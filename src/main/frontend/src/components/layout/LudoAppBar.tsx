import {
  AppBar,
  Button,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import MenuIcon from "@mui/icons-material/Menu";
import PersonIcon from "@mui/icons-material/Person";
import CasinoIcon from "@mui/icons-material/Casino";
import HomeIcon from "@mui/icons-material/Home";
import PeopleIcon from '@mui/icons-material/People';
import React, { useEffect } from "react";
import authService from "../../services/auth.service";
import {Navigate} from "react-router-dom";

interface AppBarProps {
  title: string;
}

export default function LudoAppBar(props: AppBarProps) {
  const [open, setOpen] = React.useState(false);
  const [shouldLogout, setShouldLogout] = React.useState<boolean>(false);

  useEffect(() => {
    if (shouldLogout) {
      authService.logout();
      setShouldLogout(false);
    }
  }, [shouldLogout]);

  const toggleDrawer =
    (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        event.type === "keydown" &&
        ((event as React.KeyboardEvent).key === "Tab" ||
          (event as React.KeyboardEvent).key === "Shift")
      ) {
        return;
      }

      setOpen(() => open);
    };
  return (
    <Box sx={{ flexGrow: 1 }}>
      {
        shouldLogout ? <Navigate to="/" ></Navigate> : <></>
      }
      <AppBar>
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
            onClick={() => setOpen(true)}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            {props.title}
          </Typography>
          {authService.getCurrentPlayer() === null ? (
            <Button color="inherit" href="/login">
              Login
            </Button>
          ) : (
            <Button color="inherit" onClick={() => setShouldLogout(true)}>
              Logout
            </Button>
          )}
        </Toolbar>
      </AppBar>
      <Drawer anchor="left" open={open} onClose={toggleDrawer(false)}>
        <Box onClick={() => setOpen(false)}>
          <List>

            <ListItem>
              <ListItemButton href="/">
                <ListItemIcon>
                  <HomeIcon />
                </ListItemIcon>
                <ListItemText>Home</ListItemText>
              </ListItemButton>
            </ListItem>
            <ListItem>
              <ListItemButton href="/profile">
                <ListItemIcon>
                  <PersonIcon></PersonIcon>
                </ListItemIcon>
                <ListItemText>My profile</ListItemText>
              </ListItemButton>
            </ListItem>
            <ListItem>
              <ListItemButton href="/my-games">
                <ListItemIcon>
                  <CasinoIcon />
                </ListItemIcon>
                <ListItemText>My games</ListItemText>
              </ListItemButton>
            </ListItem>
            <ListItem>
              <ListItemButton href="/friends">
                <ListItemIcon>
                  <PeopleIcon></PeopleIcon>
                </ListItemIcon>
                <ListItemText>Friends</ListItemText>
              </ListItemButton>
            </ListItem>
          </List>
        </Box>
      </Drawer>
    </Box>
  );
}

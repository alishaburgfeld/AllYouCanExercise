import * as React from 'react';
import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { useTheme } from '@mui/material/styles';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import MenuIcon from '@mui/icons-material/Menu';
import { IconButton, Menu, MenuItem, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import "../../css/Navbar.css"
import axios from 'axios'
import Cookies from 'js-cookie';

export default function Navbar({ activeUsername, setActiveUsername, setActiveWorkout }) {

  // console.log("user inside navbar is", user);
  // console.log("Is user null?", user === null);
  // console.log("Type of user:", typeof user);
  const navigate = useNavigate();
  const theme = useTheme();
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLoginClick = () => {
    navigate("/login");
  };

  const logout = async () => {
    try {
      const csrfToken = Cookies.get('XSRF-TOKEN')
      // console.log('csrf token is', csrfToken)
      const response = await axios.post(
          "http://localhost:8080/auth/logout", 
          {},
          {
            headers: {
              'X-XSRF-TOKEN': csrfToken,
            },
            withCredentials: true, 
          }
        );
        // console.log("logout response is", response.data);
  } catch (error) {
      console.error(error);
  }
    handleClose()
    setActiveUsername(null)
    setActiveWorkout(null)
    navigate("/")
  }

  return (
    <Box sx={{ flexGrow: 1 }} className="navbar">
      <AppBar position="static" sx={{ backgroundColor: theme.palette.primary.mix }} role="navigation">
        <Toolbar>
          <Link to="/">
            <FitnessCenterIcon
              size="large"
              edge="start"
              color="inherit"
              sx={{ mr: 2, color: theme.palette.secondary.main }}
            >
              <MenuIcon />
            </FitnessCenterIcon>
          </Link>
          <Typography component="div" className="navbar_title" sx={{ flexGrow: 1, color: theme.palette.secondary.main, fontSize: "1.2rem", fontWeight: "600" }}>
            All You Can Exercise
          </Typography>
          {activeUsername!=null ? (
            <>
              <IconButton
                size="large"
                edge="end"
                color="inherit"
                aria-label="menu"
                aria-controls={open ? 'basic-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
                sx={{ mr: 2, color: theme.palette.secondary.main }}
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                  'aria-labelledby': 'basic-button',
                }}
              >
                <MenuItem onClick={handleClose}>Records</MenuItem>
                <MenuItem onClick={handleClose}>Workouts</MenuItem>
                <MenuItem onClick={handleClose}>Calendar</MenuItem>
                <MenuItem onClick={logout}>Logout</MenuItem>
              </Menu>
            </>
          ) : (
            <Button variant="text">
              <Typography onClick={() => handleLoginClick()} sx={{ flexGrow: 1, color: theme.palette.secondary.main, fontSize: "1rem", fontWeight: "400" }}>
            Login
          </Typography>
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}

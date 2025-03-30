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

export default function Navbar({ user }) {

  console.log("user is", user);
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
          {user!=null ? (
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
                <MenuItem onClick={handleClose}>Logout</MenuItem>
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

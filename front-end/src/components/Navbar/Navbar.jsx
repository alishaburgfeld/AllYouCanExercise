import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { useTheme } from '@mui/material/styles';
// import IconButton from '@mui/material/IconButton';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import MenuIcon from '@mui/icons-material/Menu';
import { IconButton } from '@mui/material';
import { Link } from 'react-router-dom';
import "../../css/Navbar.css"

export default function Navbar() {
  const theme = useTheme();

  return (
    <Box sx={{ flexGrow: 1 }} className="navbar">
      <AppBar position="static" sx={{ backgroundColor: theme.palette.primary.mix}} role='navigation'>
        <Toolbar>
          <Link to="/">
            <FitnessCenterIcon
              size="large"
              edge="start"
              color="inherit"
              sx={{ mr: 2, color: theme.palette.secondary.main}}
            >
              <MenuIcon />
            </FitnessCenterIcon>
          </Link>
          <Typography component="div" sx={{ flexGrow: 1, color: theme.palette.secondary.main, fontSize: "1.5rem", fontWeight: "600"}}>
            All You Can Exercise
          </Typography>
          <IconButton 
            size="large"
            edge="end"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2, color: theme.palette.secondary.main }}>
            <MenuIcon />
          </IconButton>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

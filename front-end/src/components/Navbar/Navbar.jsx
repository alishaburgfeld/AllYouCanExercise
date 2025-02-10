import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
// import IconButton from '@mui/material/IconButton';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import MenuIcon from '@mui/icons-material/Menu';
import { IconButton } from '@mui/material';

export default function Navbar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <FitnessCenterIcon
            size="large"
            edge="start"
            color="inherit"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </FitnessCenterIcon>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            All You Can Exercise
          </Typography>
          <IconButton 
          size="large"
          edge="end"
          color="inherit"
          aria-label="menu"
          sx={{ mr: 2 }}>
            <MenuIcon />
            </IconButton>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

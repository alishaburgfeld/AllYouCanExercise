import React, { useState } from 'react';
import { TextField, Button, Typography, Box, Link } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { useNavigate } from 'react-router-dom';
import "../css/SignUpPage.css";

export default function SignUpPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmedPassword, setConfirmedPassword] = useState('');
  const [matchingPasswords,setMatchingPasswords] = useState(true)
  const theme = useTheme();
  const navigate = useNavigate();

  const handleLoginRedirect=() =>{
    navigate("/login");
  }
  

  const handleSignUp = (event) => {
    event.preventDefault();
    if (password !== confirmedPassword) {
        setMatchingPasswords(false);
        return;
    }
    else {
        setMatchingPasswords(true);
        // Implement sign up logic here
        console.log('Signing up with', username, password);
    }
    }

  return (
    <Box
        className="signUpPage"
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '100vh',
        padding: 3,
      }}
    >
      <Typography variant="h4" sx={{ marginBottom: 3, color: theme.palette.secondary.main }}>
        Sign Up
      </Typography>
      <form onSubmit={handleSignUp} style={{ width: '100%', maxWidth: 400 }}>
        <TextField
          label="Username"
          variant="outlined"
          fullWidth
          required
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          sx={{ marginBottom: 2 }}
        />
        <TextField
          label="Password"
          type="password"
          variant="outlined"
          fullWidth
          required
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          sx={{ marginBottom: 2 }}
        />
        <TextField
          label="Confirm Password"
          type="password"
          variant="outlined"
          fullWidth
          required
          value={confirmedPassword}
          onChange={(e) => setConfirmedPassword(e.target.value)}
          sx={{ marginBottom: 2 }}
        />
        {!matchingPasswords 
            ? (
            <>
            <Typography variant="body2" sx={{ mb: 2, color: "red" }}>
        Passwords do not match!
      </Typography>
            </>
            )
            : ""
        }
        <Button
          type="submit"
          variant="contained"
          color="primary"
          fullWidth
          sx={{ marginBottom: 2 }}
        >
          Sign Up
        </Button>
        <Box sx={{ textAlign: 'center' }}>
          <Typography variant="body2">
            Already have an account?{' '}
            <Button onClick={handleLoginRedirect} variant="text" sx={{color: theme.palette.secondary.main}}>
              Login
            </Button>
          </Typography>
        </Box>
      </form>
    </Box>
  );
}

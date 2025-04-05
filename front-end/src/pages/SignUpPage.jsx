import React, { useState } from 'react';
import { TextField, Button, Typography, Box, Link } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'
import Cookies from 'js-cookie';
import "../css/SignUpPage.css";
import Alert from '@mui/material/Alert';

export default function SignUpPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmedPassword, setConfirmedPassword] = useState('');
  const [matchingPasswords,setMatchingPasswords] = useState(true)
  const [signedUp, setSignedUp] = useState(false)
  const theme = useTheme();
  const navigate = useNavigate();

  const loginRedirect=() =>{
    navigate("/login");
    // need to add a success alert that they were signed in
  }
  

  const handleSignUp = async (event) => {
    event.preventDefault();
    if (password !== confirmedPassword) {
        setMatchingPasswords(false);
        return;
    }
    else {
        setMatchingPasswords(true);
        const csrfToken = Cookies.get('XSRF-TOKEN');
        console.log('csrf token', csrfToken)
        try {
            const response = await axios.post(
                "http://localhost:8080/auth/register", 
                { username, password }, // This is the body of the POST request
                {
                  headers: {
                    'X-XSRF-TOKEN': csrfToken,
                  },
                  withCredentials: true, // Sends cookies with the request
                }
              );

              console.log("sign up response is", response)              
              console.log('Signing up with', username, password);
              setSignedUp(true)
              setTimeout(() => loginRedirect(), 1500);
        } catch (error) {
            console.error(error);
        }
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
          inputProps={{ "data-testid": "signup-username-input" }}
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
          inputProps={{ "data-testid": "signup-password-input" }}
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
          inputProps={{ "data-testid": "signup-password-confirmation-input" }}
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
            <Button onClick={loginRedirect} variant="text" sx={{color: theme.palette.secondary.main}}>
              Login
            </Button>
          </Typography>
        </Box>
        {signedUp ?
          <Alert severity="success">You have successfully signed up, you can now login!</Alert>
          : ""}
      </form>
    </Box>
  );
}

import React, { useState } from 'react';
import { TextField, Button, Typography, Box, Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@mui/material/styles';
import { postAxiosCall } from "../utils/HelperFunctions";
import "../css/LoginPage.css";
import Alert from '@mui/material/Alert';

export default function LoginPage({setActiveUsername}) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [loginError, setLoginError] = useState(null);
  const theme = useTheme();
  const navigate = useNavigate();

  const handleLoginSuccess = () => {
    setLoggedIn(true)
    // setTimeout(navigate("/"), 3000)
    // the way above was making the navigation happens instantly — before the timeout starts
    setTimeout(() => navigate("/"), 1500);
  };

  

  const handleLogin = async (event) => {
    event.preventDefault();
  
    const response = await postAxiosCall("http://localhost:8080/auth/login", { username, password });
  
    if (response.success) {
      console.log('handleLogin response is', response.data);
      setActiveUsername(response.data);
      handleLoginSuccess();
    } else {
      if (response.error === "Unauthorized") {
        // Show specific error message to user
        console.error("Login failed: Invalid credentials");
        setLoginError("Invalid username or password.");
      } else {
        console.error("Login failed:", response.error);
        setLoginError("An unexpected error occurred. Please try again.");
      }
    }
  };

  const handleSignUpRedirect = () => {
    navigate('/signup');
  };

  return (
    <Box
        className="loginPage"
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
        Login
      </Typography>
      <form onSubmit={handleLogin} style={{ width: '100%', maxWidth: 400 }}>
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
        <Button
          type="submit"
          variant="contained"
          color="primary"
          fullWidth
          sx={{ marginBottom: 2 }}
        >
          Login
        </Button>
        <Box sx={{ textAlign: 'center' }}>
          <Typography variant="body2">
            Don't have an account?{' '}
            {/* The { ' ' } here adds a space between "Don't have an account?" and the Sign Up link. Without it, the Sign Up link would be right next to the sentence without any space in between. */}
            <Link component="button" onClick={handleSignUpRedirect} sx={{color: theme.palette.secondary.main, '& .MuiInput-underline:before': { borderBottomColor: theme.palette.secondary.main }}}>
              Sign Up
            </Link>
          </Typography>
        </Box>
        {loggedIn ?
        <Alert severity="success">You have successfully logged in!</Alert>
        : ""}
        {loginError!==null ?
        <Alert severity="error">Invalid Credentials!</Alert>
        : ""}
      </form>
    </Box>
  );
}

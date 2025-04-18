import React from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
// import DatePicker from '@mui/material/DatePicker';


export default function TestPage () {

    return (
        <form>
          <TextField
          sx={{mt:9}}
            label="Name"
            variant="outlined"
            fullWidth
            margin="normal"
          />
          <TextField
            label="Email"
            variant="outlined"
            fullWidth
            margin="normal"
          />
          
          <Button
            variant="contained"
            color="primary"
            type="submit"
          >
            Submit
          </Button>
        </form>
      );
}


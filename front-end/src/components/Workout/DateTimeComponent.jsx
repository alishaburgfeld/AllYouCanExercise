import React, { useState } from 'react';
import { TextField, Box } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import "../../css/DateTimeComponent.css"


const DateTimeComponent = ({ onChange, selectedDateTime, setSelectedDateTime }) => {
  
  const handleChange = (newValue) => {
    setSelectedDateTime(newValue);
    if (onChange) {
      onChange(newValue);
    }
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDateFns} sx={{width: "100%"}}>
      <Box sx={{ mt: 2}}>
        <DateTimePicker
          label="Workout Completed At"
          value={selectedDateTime}
          onChange={handleChange}
          sx={{width: "100%"}}
          renderInput={(params) => <TextField {...params} />}
        />
      </Box>
    </LocalizationProvider>
  );
};

export default DateTimeComponent;

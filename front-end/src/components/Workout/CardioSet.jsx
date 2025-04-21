import { Button, Box, Select, MenuItem, FormControl, InputLabel, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters, toMeters } from "../../utils/HelperFunctions";
import { useState, useEffect } from "react";



export default function CardioSet ({distance, duration, setDistance, setDuration, distanceUnit, setDistanceUnit, hours, minutes, seconds, setHours, setMinutes, setSeconds}) {
  // onclose need to reset the distanceInput to distance
  // const [currentDistance, setCurrentDistance] = useState(null);
  // const [currentDuration, setCurrentDuration] = useState(null);
  const [distanceInput, setDistanceInput] = useState(distance || ""); // what the user typed
  const [unit, setUnit] = useState(distanceUnit || "mi"); // selected unit

  console.log('distanceinput is', distanceInput)
  const handleDurationChange = () => {
    setDistanceUnit(unit)
    // Convert hours, minutes, and seconds into total seconds
    const totalDuration = (hours * 3600) + (minutes * 60) + seconds;
    setDuration(totalDuration);
  };

  const handleDistanceInputChange = (distanceValue, distanceUnit) => {
    setDistanceInput(distanceValue)
    setUnit(distanceUnit)

    const totalDistance = toMeters(distanceValue, distanceUnit)
    console.log('2) Cardio set set total distance is', totalDistance)
    setDistance(totalDistance);
  };


    return(
      <Box sx={{ width: '100%', maxWidth: 400, display: 'flex', flexDirection: "column", alignItems: "flex-start", pl:4 }}>
        <Box sx={{ display: 'flex', justifyContent: "space-between" }}>
        <TextField
        required
          id="standard-number"
          label="Distance"
          type="number"
          variant="standard"
          value = {distanceInput}
          onChange={(e) => handleDistanceInputChange(e.target.value, unit)}
          sx={{width: "45%"}}
          slotProps={{
            inputLabel: {
              shrink: true,
            },
          }}
        />
        <FormControl required sx={{ width: "40%" }}>
        <InputLabel id="distance-unit-label" sx={{mt:1}}>Unit</InputLabel>
        <Select
          labelId="distance-unit-label"
          value={unit || "mi"}
          onChange={(e) => handleDistanceInputChange(distanceInput, e.target.value)}
          label="Distance Unit"
          variant="standard"
        >
          <MenuItem value="m">Meters</MenuItem>
          <MenuItem value="yd">Yards</MenuItem>
          <MenuItem value="mi">Miles</MenuItem>
        </Select>
      </FormControl>
        </Box>
       <Box sx={{mt:2}}>
        <TextField
          label="Hours"
          type="number"
          variant="standard"
          value={hours}
          onChange={(e) => {
            setHours(e.target.value);
            handleDurationChange();
          }}
          sx={{ width: "30%", marginRight: "5px" }}
        />
        <TextField
          label="Minutes"
          type="number"
          variant="standard"
          value={minutes}
          onChange={(e) => {
            setMinutes(e.target.value);
            handleDurationChange();
          }}
          sx={{ width: "30%", marginRight: "5px" }}
        />
        <TextField
          label="Seconds"
          type="number"
          variant="standard"
          value={seconds}
          onChange={(e) => {
            setSeconds(e.target.value);
            handleDurationChange();
          }}
          sx={{ width: "30%" }}
        />
      </Box>
      </Box>
  )
}
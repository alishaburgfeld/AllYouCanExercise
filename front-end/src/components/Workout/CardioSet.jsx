import { Button, Box, Select, MenuItem, FormControl, InputLabel, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters, toMeters } from "../../utils/HelperFunctions";
import { useState, useEffect } from "react";



export default function CardioSet ({distance, duration, setDistance, setDuration, distanceUnit, setDistanceUnit, hours, minutes, seconds, setHours, setMinutes, setSeconds, setInputDistance, setInputDistanceUnit, setInputDuration}) {
  // onclose need to reset the distanceInput to distance
  // const [currentDistance, setCurrentDistance] = useState(null);
  // const [currentDuration, setCurrentDuration] = useState(null);
  // const [distanceInput, setDistanceInput] = useState(distance || ""); // what the user typed
  const [temporaryUnit, setTemporaryUnit] = useState(distanceUnit || ""); // selected unit
  const [temporaryDistance, setTemporaryDistance] = useState(distance || "")

  // console.log('distanceinput is', distanceInput)
  const handleDurationChange = (hours, minutes, seconds) => {
    // setDistanceUnit(unit)
    // Convert hours, minutes, and seconds into total seconds
    // const totalDuration = (hours * 3600) + (minutes * 60) + seconds;
    setInputDuration({"hours": hours, "minutes": minutes, "seconds": seconds});

  };

  const handleDistanceInputChange = (distanceValue, distanceUnit) => {
    setInputDistance(distanceValue)
    setInputDistanceUnit(distanceUnit)

    // const totalDistance = toMeters(distanceValue, distanceUnit)
    // console.log('2) Cardio set set total distance is', totalDistance)
    // setDistance(totalDistance);
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
          value = {temporaryDistance}
          onChange={
            (e) => {
              setTemporaryDistance(e.target.value)
              handleDistanceInputChange(e.target.value, temporaryUnit)
              }}
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
          value={temporaryUnit}
          onChange={(e) => {
            setTemporaryUnit(e.target.value)
            handleDistanceInputChange(temporaryDistance, e.target.value)}}
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
            // setHours(e.target.value);
            handleDurationChange(e.target.value, minutes, seconds);
          }}
          sx={{ width: "30%", marginRight: "5px" }}
        />
        <TextField
          label="Minutes"
          type="number"
          variant="standard"
          value={minutes}
          onChange={(e) => {
            // setMinutes(e.target.value);
            handleDurationChange(hours, e.target.value, seconds);
          }}
          sx={{ width: "30%", marginRight: "5px" }}
        />
        <TextField
          label="Seconds"
          type="number"
          variant="standard"
          value={seconds}
          onChange={(e) => {
            // setSeconds(e.target.value);
            handleDurationChange(hours, minutes, e.target.value);
          }}
          sx={{ width: "30%" }}
        />
      </Box>
      </Box>
  )
}
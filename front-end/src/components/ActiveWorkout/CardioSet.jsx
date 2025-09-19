import { Button, Box, Select, MenuItem, FormControl, InputLabel, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters, toMeters } from "../../utils/HelperFunctions";
import { useState, useEffect } from "react";



export default function CardioSet ({exercise, setInputDistance, setInputDistanceMeasurement, setInputDuration}) {
  const [temporaryUnit, setTemporaryUnit] = useState(exercise?.sets?.[0]?.distanceMeasurement|| ""); // selected unit
  const [temporaryDistance, setTemporaryDistance] = useState(exercise?.sets?.[0]?.distance || "")
  const [temporaryHours, setTemporaryHours] = useState(exercise?.sets?.[0]?.duration?.hours || 0)
  const [temporaryMinutes, setTemporaryMinutes] = useState(exercise?.sets?.[0]?.duration?.minutes || 0)
  const [temporarySeconds, setTemporarySeconds] = useState(exercise?.sets?.[0]?.duration?.seconds || 0)
  // console.log('distanceinput is', distanceInput)
  
  const handleDurationChange = (hours, minutes, seconds) => {
    const parsedHours = Number(hours);
    const parsedMinutes = Number(minutes);
    const parsedSeconds = Number(seconds);
    setInputDuration({"hours": parsedHours, "minutes": parsedMinutes, "seconds": parsedSeconds});
  };

  const handleDistanceInputChange = (distanceValue, distanceMeasurement) => {
    setInputDistance(distanceValue)
    setInputDistanceMeasurement(distanceMeasurement)
  };

    return(
      <Box sx={{ width: '100%', maxWidth: 400, display: 'flex', flexDirection: "column", alignItems: "flex-start", pl:4 }}>
        <Box sx={{ display: 'flex', justifyContent: "space-between" }}>
        <TextField
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
        <FormControl sx={{ width: "40%" }}>
        <InputLabel id="distance-unit-label" sx={{mt:1}}>Unit</InputLabel>
        <Select

          value={temporaryUnit}
          onChange={(e) => {
            setTemporaryUnit(e.target.value)
            handleDistanceInputChange(temporaryDistance, e.target.value)}}
          label="Distance Unit"
          variant="standard"
        >
          <MenuItem value="METERS">Meters</MenuItem>
          <MenuItem value="YARDS">Yards</MenuItem>
          <MenuItem value="MILES">Miles</MenuItem>
        </Select>
      </FormControl>
        </Box>
       <Box sx={{mt:2}}>
        <TextField
          label="Hours"
          type="number"
          variant="standard"
          value={temporaryHours}
          onChange={(e) => {
            setTemporaryHours(e.target.value);
            handleDurationChange(e.target.value, temporaryMinutes, temporarySeconds);
          }}
          sx={{ width: "30%", marginRight: "5px" }}
        />
        <TextField
          label="Minutes"
          type="number"
          variant="standard"
          value={temporaryMinutes}
          onChange={(e) => {
            setTemporaryMinutes(e.target.value);
            handleDurationChange(temporaryHours, e.target.value, temporarySeconds);
          }}
          sx={{ width: "30%", marginRight: "5px" }}
        />
        <TextField
          label="Seconds"
          type="number"
          variant="standard"
          value={temporarySeconds}
          onChange={(e) => {
            setTemporarySeconds(e.target.value);
            handleDurationChange(temporaryHours, temporaryMinutes, e.target.value);
          }}
          sx={{ width: "30%" }}
        />
      </Box>
      </Box>
  )
}
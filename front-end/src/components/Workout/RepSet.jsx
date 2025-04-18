import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { useState, useEffect } from "react";


export default function RepSet ({setCount}) {

    
    const [currentReps, setCurrentReps] = useState(null);
    const [currentWeight, setCurrentWeight] = useState(null);
    
    const [allReps, setAllReps] = useState(null);
    const [allWeights, setAllWeights] = useState(null);

    return(
        <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "row", justifyContent: "space-between", alignItems: "center" }}>
        <Typography>Set {setCount}</Typography>    
        <TextField
        required
          id="standard-number"
          label="Reps"
          type="number"
          variant="standard"
          placeholder = {currentReps !== null? currentReps : ""}
          onChange={(e) => setCurrentReps(e.target.value)}
          sx={{width: "30%"}}
          slotProps={{
            inputLabel: {
              shrink: true,
            },
          }}
        />
        <TextField
        required
          id="standard-number"
          label="Weight"
          type="number"
          variant="standard"
          placeholder = {currentWeight !== null? currentWeight : ""}
          onChange={(e) => setCurrentWeight(e.target.value)}
          sx={{width: "30%"}}
          slotProps={{
            inputLabel: {
              shrink: true,
            },
          }}
        />
      </Box>
  )
}
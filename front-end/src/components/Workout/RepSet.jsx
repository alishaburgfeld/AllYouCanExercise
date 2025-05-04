import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { useState, useEffect } from "react";


export default function RepSet ({setCount, allReps, allWeights, setAllReps, setAllWeights}) {

    const index = setCount -1;

    return(
        <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "row", justifyContent: "space-between", alignItems: "center" }}>
        <Typography>Set {setCount}: </Typography>    
        <TextField
          required
          label="Reps"
          type="number"
          variant="standard"
          onChange={(e) => {
            const updatedReps = [...allReps];
            updatedReps[index] = e.target.value;
            setAllReps(updatedReps);
          }}
          value={allReps[index] || ""}
          sx={{ width: "30%" }}
          slotProps={{
            inputLabel: {
              shrink: true,
            },
          }}
        />

        <TextField
          required
          label="Weight"
          type="number"
          variant="standard"
          onChange={(e) => {
            const updatedWeights = [...allWeights];
            updatedWeights[index] = e.target.value;
            // setting the final value in that array instead of the previous value it came in with (defaulted to)
            setAllWeights(updatedWeights);
          }}
          value={allWeights[index] || ""}
          sx={{ width: "30%" }}
          slotProps={{
            inputLabel: {
              shrink: true,
            },
          }}
        />

      </Box>
  )
}
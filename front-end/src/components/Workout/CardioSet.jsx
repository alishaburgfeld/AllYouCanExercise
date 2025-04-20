import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { useState, useEffect } from "react";


export default function CardioSet ({distance, duration, setDistance, setDuration}) {
  const [currentDistance, setCurrentDistance] = useState(null);
  const [currentDuration, setCurrentDuration] = useState(null);

    return(
      <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "row", justifyContent: "space-between", alignItems: "center" }}>
        
        <TextField
        required
          id="standard-number"
          label="Distance"
          type="number"
          variant="standard"
          value = {distance}
          onChange={(e) => setDistance(e.target.value)}
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
          label="Duration"
          type="number"
          variant="standard"
          value = {duration}
          onChange={(e) =>  setDuration(e.target.value)}
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
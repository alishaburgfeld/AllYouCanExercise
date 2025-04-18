import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import { Add } from '@mui/icons-material';
import { useState, useEffect } from "react";


export default function CardioSet () {
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
          onChange={(e) => setCurrentDistance(e.target.value)}
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
          onChange={(e) =>  setCurrentDuration(e.target.value)}
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
import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import { useState, useEffect } from "react";
import { preventMouseScroll } from "../../utils/HelperFunctions";


export default function RepSet ({set, setIndex, updateSets}) {
    const [segments, setSegments] = useState(set["segments"]);
    const [currentSegmentReps, setCurrentSegmentReps] = useState(null);
    const [currentSegmentWeight, setCurrentSegmentWeight] = useState(null);

    const updateSegments = (index, reps, weight) =>{
      const updatedSegment = segments[index]
      updatedSegment.reps = reps;
      updatedSegment.weight = weight;
      const updatedSegments = [...segments];
      updatedSegments[index] = updatedSegment;
      console.log('udpated segments are', updatedSegments)
      setSegments(updatedSegments);
  };

  const addSegment = () => {
    const lastSegment = segments[segments.length - 1] || {};
    const newSegment = { ...lastSegment }; // shallow copy
    setSegments([...segments, newSegment]);
    // duplicates the previous value into the new segment value
};


  const removeSegment = (index) => {
    const newSegments = [...segments]
    newSegments.splice(index,1)
    console.log('removed segments are now', newSegments)
    setSegments(newSegments);
  }

  useEffect(()=> {
    updateSets(setIndex, segments);
  }, [segments])

    return(
      <>
      {segments.map((segment, index)=> {
        return(
          <>
          <Typography sx={{pl: 2}}>Segment {index + 1}: </Typography>    
          <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "row", justifyContent: "space-between", alignItems: "center" }}>
            <TextField
              required
              label="Reps"
              type="number"
              variant="standard"
              onChange={(e) => {
                setCurrentSegmentReps(e.target.value);
                updateSegments(index, e.target.value, currentSegmentWeight)
              }}
              value={segment.reps || ""}
              sx={{ width: "30%" }}
              slotProps={{
                inputLabel: {
                  shrink: true,
                },
                input: {
                  onWheel: preventMouseScroll,
                },
              }}
            />

            <TextField
              required
              label="Weight"
              type="number"
              variant="standard"
              onChange={(e) => {
                setCurrentSegmentWeight(e.target.value);
                updateSegments(index, currentSegmentReps, e.target.value);
              }}
              value={segment.weight || ""}
              sx={{ width: "30%" }}
              slotProps={{
                inputLabel: {
                  shrink: true,
                },
                input: {
                  onWheel: preventMouseScroll,
                },
              }}
            />
            <>
            <AddIcon onClick={addSegment} sx={{ cursor: 'pointer' }} />
            
            </>
          </Box>
          </>
        )
      })}
      </>
  )
}
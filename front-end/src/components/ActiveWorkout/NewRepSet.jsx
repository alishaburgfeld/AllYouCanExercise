import {
  Button,
  TextField,
  IconButton,
  Box,
  Typography,
  Divider,
} from "@mui/material"
import AddIcon from "@mui/icons-material/Add"
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline"
import { preventMouseScroll } from "../../utils/HelperFunctions";
import { useState, useEffect } from "react";



export default function NewRepSet ({set, setIndex, updateSets}) {
    const [segments, setSegments] = useState(set["segments"]);
    const [currentSegmentReps, setCurrentSegmentReps] = useState(null);
    const [currentSegmentWeight, setCurrentSegmentWeight] = useState(null);
    
    const updateSegments = (index, reps, weight) =>{
    setSegments(prevSegments=> prevSegments.map((seg,i)=>
        i === index ? { ...seg, reps: reps, weight: weight} : seg))
    };

  const addSegment = () => {
    const lastSegment = segments[segments.length - 1] || {};
    const newSegment = {
        ...lastSegment,
        reps: lastSegment.reps ?? 0,
        weight: lastSegment.weight ?? 0,
        };
    // (?? is the nullish coalescing operator — it only uses 0 if reps or weight are null or undefined, not if they’re 0.)
    
    setSegments([...segments, newSegment]);
    // duplicates the previous value into the new segment value
};


  const removeSegment = (segmentIndex) => {
    const newSegments = [...segments]
    newSegments.splice(segmentIndex,1)
    console.log('removed segments are now', newSegments)
    setSegments(newSegments);
  }

  useEffect(()=> {
    updateSets(setIndex, segments);
  }, [segments])

    return(
      <>
      {segments.map((segment, segmentIndex)=> {
        return (

        <Box key={segmentIndex}>
            {segmentIndex > 0 && (
                <Divider sx={{ my: 1 }}>
                <Typography variant="caption" color="text.secondary">
                    Split
                </Typography>
                </Divider>
            )}
            <Box
                sx={{
                display: "flex",
                gap: { xs: 1, sm: 2 },
                alignItems: "center",
                }}
            >
                <TextField
                required
                label="Reps"
                type="text"
                inputMode="numeric"
                pattern="[0-9]*"
                variant="standard"
                onChange={(e) => {
                    setCurrentSegmentReps(Number(e.target.value));
                    updateSegments(segmentIndex, Number(e.target.value), currentSegmentWeight)
                }}
                value={segment.reps || ""}
                size="small"
                sx={{
                flex: 1,
                minWidth: { xs: "auto", sm: "100px" },
                }}
                />

                <TextField
                    required
                    label="Weight"
                    type="text"
                    inputMode="numeric"
                    pattern="[0-9]*"
                    variant="standard"
                    onChange={(e) => {
                        setCurrentSegmentWeight(Number(e.target.value));
                        updateSegments(segmentIndex, currentSegmentReps, Number(e.target.value));
                    }}
                    value={segment.weight || ""}
                    size="small"
                    sx={{
                    flex: 1,
                    minWidth: { xs: "auto", sm: "100px" },
                    }}
                />
            </Box>
            {segments.length > 1 && (
                <IconButton
                    onClick={() => removeSegment(segmentIndex)}
                    color="warning"
                    size="small"
                    sx={{
                    border: "1px solid",
                    borderColor: "warning.main",
                    "&:hover": {
                        backgroundColor: "warning.light",
                        opacity: 0.1,
                    },
                    }}
                >
                    <RemoveCircleOutlineIcon />
                </IconButton>
            )}
        </Box>
        )
        })}
       <Button
            startIcon={<AddIcon />}
            onClick={() => addSegment()}
            variant="outlined"
            size="small"
            sx={{
            mt: { xs: 0.5, sm: 1 },
            fontSize: { xs: "0.813rem", sm: "0.875rem" },
            }}
        >
            Add Segment
        </Button> 
        </>
      
  )
}
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

import { useState, useEffect } from "react";



export default function NewRepSet ({set, setIndex, updateSets}) {
    const [segments, setSegments] = useState(set["segments"]);
    const [currentSegmentReps, setCurrentSegmentReps] = useState(null);
    const [currentSegmentWeight, setCurrentSegmentWeight] = useState(null);
    
    console.log("showing new rep set, segments are are", segments)
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
                type="number"
                variant="standard"
                onChange={(e) => {
                    setCurrentSegmentReps(e.target.value);
                    updateSegments(segmentIndex, e.target.value, currentSegmentWeight)
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
                    type="number"
                    variant="standard"
                    onChange={(e) => {
                        setCurrentSegmentWeight(e.target.value);
                        updateSegments(segmentIndex, currentSegmentReps, e.target.value);
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
        // return(
        //   <>
        //   <Typography sx={{pl: 2}}>Segment {segmentIndex + 1}: </Typography>    
        //   <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "row", justifyContent: "space-between", alignItems: "center" }}>
        //     <TextField
        //       required
        //       label="Reps"
        //       type="number"
        //       variant="standard"
        //       onChange={(e) => {
        //         setCurrentSegmentReps(e.target.value);
        //         updateSegments(segmentIndex, e.target.value, currentSegmentWeight)
        //       }}
        //       value={segment.reps || ""}
        //       sx={{ width: "30%" }}
        //       slotProps={{
        //         inputLabel: {
        //           shrink: true,
        //         },
        //       }}
        //     />

        //     <TextField
        //       required
        //       label="Weight"
        //       type="number"
        //       variant="standard"
        //       onChange={(e) => {
        //         setCurrentSegmentWeight(e.target.value);
        //         updateSegments(segmentIndex, currentSegmentReps, e.target.value);
        //       }}
        //       value={segment.weight || ""}
        //       sx={{ width: "30%" }}
        //       slotProps={{
        //         inputLabel: {
        //           shrink: true,
        //         },
        //       }}
        //     />
        //     <>
        //     <AddIcon onClick={addSegment} sx={{ cursor: 'pointer' }} />
        //     <RemoveIcon onClick={removeSegment} sx={{ cursor: 'pointer' }} />
        //     </>
        //   </Box>
        //   </>
        // )
      
      
  )
}
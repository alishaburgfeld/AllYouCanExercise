import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState, useEffect } from "react";
import { useTheme } from '@mui/material/styles';

export default function WorkoutAccordion({workoutNotes, workoutExerciseDetails, workoutExerciseGroups}) {


    const groupByExerciseGroup = (workoutExerciseDetails) =>{
        return workoutExerciseDetails.reduce((acc, exercise) => {
            const { exerciseGroup } = exercise;
            if (!acc[exerciseGroup]) {
            acc[exerciseGroup] = [];
            }
            acc[exerciseGroup].push(exercise);
            return acc;
        }, {});
    }

    function formatSet(set, index) {
        if (set.reps && set.weight) {
            return `Set ${index + 1}: ${set.reps} reps at ${set.weight} lbs`;
        }
        if (set.durationSeconds && set.distanceMeters) {
            const minutes = Math.floor(set.durationSeconds / 60);
            const seconds = set.durationSeconds % 60;
            const distanceMiles = (set.distanceMeters / 1609.34).toFixed(2);
            return `Set ${index + 1}: ${minutes}m ${seconds}s for ${distanceMiles} miles`;
        }
        if (set.durationSeconds) {
            const minutes = Math.floor(set.durationSeconds / 60);
            const seconds = set.durationSeconds % 60;
            return `Set ${index + 1}: ${minutes}m ${seconds}s`;
        }
        return `Set ${index + 1}: No data`;
    }

    // https://chatgpt.com/share/68b25999-b5f0-800f-953a-9e40ee289410




    return (
        <Accordion sx={{width: "100%", 
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'flex-start',
        justifyContent: 'flex-start', 
        backgroundColor: "transparent"
        }}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1-content"
          id="panel1-header"
          sx={{
             display: 'flex',
        // flexDirection: 'column',
        // alignItems: 'center',
          }}
        >
          <Typography component="span" sx={{alignSelf:"center"}}>View More</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography variant="h6" sx={{mb: "1.5rem"}}>Notes </Typography>
          <Typography sx={{mb: "1.5rem"}}>{workoutNotes} </Typography>
          <Typography variant="h6" sx={{mb: "1.5rem"}}>Exercises </Typography>
          {workoutExerciseGroups.map((exerciseGroup, index)=> {
                    return(
                    <Box key = {index} sx={{ pb: 5}}>
                        <Typography> {exerciseGroup}</Typography>
                        <SetsRepsDuration exercise={exerciseGroup}/>
                    </Box>
                    )
                })}
        </AccordionDetails>
      </Accordion>
    )
}
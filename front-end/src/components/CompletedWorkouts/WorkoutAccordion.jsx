import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState, useEffect } from "react";
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';

export default function WorkoutAccordion({workoutNotes, workoutExerciseDetails, workoutExerciseGroups}) {
    const [groupedWorkoutDetails, setGroupedWorkoutDetails] = useState(null);
    console.log('workout exercise groups on accordion', workoutExerciseGroups)
    const groupByExerciseGroup = () =>{
        const grouped = workoutExerciseDetails.reduce((acc, exercise) => {
            const { exerciseGroup } = exercise;
            if (!acc[exerciseGroup]) {
            acc[exerciseGroup] = [];
            }
            acc[exerciseGroup].push(exercise);
            return acc;
        }, {});
        setGroupedWorkoutDetails(grouped);
        // {BICEPS: [
//              { exerciseName: "Reverse Bicep Curls", sets: [...] },
//              { exerciseName: "Bicep Curl to Opposite Shoulder", sets: [...] }
//                  ],
    //      CARDIO: [
    //      { exerciseName: "Run", sets: [...] }
    // }
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

    useEffect(()=> {
    groupByExerciseGroup();
    
  }, [])



    return (
        <>
        
        {groupedWorkoutDetails !==null?

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
              {workoutExerciseGroups.map((exerciseGroup, index) => (
                <Box key={index} sx={{ pb: 5 }}>
                    <Typography variant="subtitle1">{exerciseGroup}</Typography>
    
                    {groupedWorkoutDetails[exerciseGroup.toUpperCase()].map((exerciseInformation, i) => (
                    <Box key={i} sx={{ pl: 2, mb: 1 }}>
                        <Typography>{exerciseInformation.exerciseName}</Typography>
    
                        {exerciseInformation.sets.map((set, j) => (
                        <Typography key={j} variant="body2" sx={{ ml: 2 }}>
                            {formatSet(set, j)}
                        </Typography>
                        ))}
                    </Box>
                    ))}
                </Box>
                ))}
            </AccordionDetails>
          </Accordion>
            : ""
        }
        </>
    )
}
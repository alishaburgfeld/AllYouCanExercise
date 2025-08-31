import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState, useEffect } from "react";
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import CompletedExercise from '../Exercise/CompletedExercise';

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

    useEffect(()=> {
    groupByExerciseGroup();
    
  }, [])

    return (
        <>
        
        {groupedWorkoutDetails !==null?

            <Accordion sx={{
            width: "100%", 
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'flex-start',
            justifyContent: 'flex-start', 
            backgroundColor: "transparent",
            borderTop: "1px solid",
            }}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              
              aria-controls="panel1-content"
              id="panel1-header"
              sx={{
                 display: 'flex',
                 mb: 0,
                 justifyContent: "center",
            // flexDirection: 'column',
            alignItems: 'center',
              }}
            >
              <Typography variant = "h6" sx={{alignSelf:"center", mb: 0, textAlign: "center"}}>View More</Typography>
              
            </AccordionSummary>
            <Divider />
            <AccordionDetails sx={{mt: 0, display: 'flex',
            flexDirection: 'column',
            alignItems: 'flex-start',
            justifyContent: "flex-start"}}>
              <Typography variant="h6" sx={{mb: "1rem", fontWeight: 550}}>Notes </Typography>
              <Typography sx={{mb: "1.5rem"}}>{workoutNotes} </Typography>
              <Typography variant="h6" sx={{mb: "1rem", fontWeight: 550}}>Exercises </Typography>
              <Box sx={{mb:0 }}>

              {workoutExerciseGroups.map((exerciseGroup, index) => (
                <Box key={index} sx={{ mb: 2, display: "flex", flexDirection: "column", alignItems: "flex-start" }}>
                    <Typography fontWeight={450} sx={{fontSize: "1.3em"}}>
                    {exerciseGroup}
                    </Typography>

                    {groupedWorkoutDetails[exerciseGroup.toUpperCase()].map((exercise, i) => (
                      <CompletedExercise exercise={exercise} index={index}/>
                    ))}
                </Box>
                ))}
              </Box>

            </AccordionDetails>
          </Accordion>
            : ""
        }
        </>
    )
}
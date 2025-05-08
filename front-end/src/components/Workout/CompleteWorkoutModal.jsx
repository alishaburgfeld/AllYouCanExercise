import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField, DialogContentText } from "@mui/material";
import { useTheme } from '@mui/material/styles';
import SetsRepsDuration from "./SetsRepsDuration"
import { postAxiosCall, toMeters, convertToSeconds } from "../../utils/HelperFunctions";
import { useState, useEffect } from 'react';
import DateTimeComponent from "./DateTimeComponent";

export default function CompleteWorkoutModal({ openCompleteWorkoutModal, setOpenCompleteWorkoutModal, activeWorkout, activeUsername}) {
    const theme = useTheme();
    const [workoutDetails, setWorkoutDetails] = useState(null)
    const [saveWorkoutError, setSaveWorkoutError] = useState(null);
    const [title, setTitle] = useState("")
    const [notes, setNotes] = useState("")
    const [selectedDateTime, setSelectedDateTime] = useState(new Date());


    
    const defineWorkoutDetails = () => {
        const timeFormattedForJava = selectedDateTime.toISOString().slice(0, 19);

        console.log('in complete workout modal, active workout is', activeWorkout)
        let workoutExerciseDetails = [];
        let finalWorkoutDetails = {
            "workoutDetails": {
                "username": activeUsername,
                "title": title,
                "completedAt": timeFormattedForJava,
                "workoutNotes": notes
                
            }
        }
        activeWorkout.forEach((exercise)=> {
            let sets = prepareSetsForBackend(exercise.sets);
            workoutExerciseDetails.push({"exerciseId": exercise.exerciseId, "sets": sets})
        })
        finalWorkoutDetails["workoutExerciseDetails"] = workoutExerciseDetails
        console.log('finalWorkoutDetails are', finalWorkoutDetails)
       setWorkoutDetails(finalWorkoutDetails)
        // console.log('in complete workout modal, workoutDetails are', workoutDetails)
    }

    const prepareSetsForBackend = (sets) => {
        let finalSets=[]
        sets.forEach((set)=> {
            let convertedSet = {...set}
            if (set.duration) {
                convertedSet.durationSeconds = convertToSeconds(set.duration)
                delete convertedSet.duration;
            }
            if (set.distance) {
                let meters = toMeters(set.distance, set.distanceUnit)
                let formattedMeters = Math.round(meters * 100) / 100
                convertedSet.distanceMeters = formattedMeters
                delete convertedSet.distanceUnit;
                delete convertedSet.distance;
            }
            finalSets.push(convertedSet)
        })
        return finalSets
    }

    const handleSave = async () => {
        console.log('in handle save, workout details are', workoutDetails, "title is", title)
        const response = await postAxiosCall("http://localhost:8080/api/workouts/full/save", workoutDetails);
            if (response.success) {
            console.log('handleworkoutsave response', response)
            } else {
                console.error("save workout failed:", response.error);
                setSaveWorkoutError("An unexpected error occurred. Please try again.");
              }
    }

    useEffect(()=> {
        defineWorkoutDetails();
      }, [activeWorkout, title, selectedDateTime, notes])

    return(
        <Dialog
        open={openCompleteWorkoutModal}
        onClose={()=>{setOpenCompleteWorkoutModal(false)}}
        aria-labelledby="completeWorkout-dialog-title"
        aria-describedby="completeWorkout-dialog-description"
        sx={{ width: '100%', maxWidth: 900, m:1 }}
        // possibly need to lower the margins
        >
            <DialogTitle id="completeWorkout-dialog-title" sx={{fontSize: 17}}>
                {/* "Finalize Workout Details" */}
                Complete Workout
            </DialogTitle>
            <form onSubmit={(e) => {
                e.preventDefault(); // Stop page reload
                handleSave();
            }}>
            <DialogContent sx={{width:"100%"}}>
                <DialogContentText>Have you finished editing all your exercises and would like to submit your workout?</DialogContentText>
                <TextField
                          label="Workout Title"
                          variant="outlined"
                          fullWidth
                          required
                          value={title}
                          onChange={(e) => setTitle(e.target.value)}
                          sx={{marginTop: 3 }}
                        />
                <TextField
                          label="Notes"
                          variant="outlined"
                          multiline={true}
                            rows={3}
                          fullWidth
                          value={notes}
                          onChange={(e) => setNotes(e.target.value)}
                          sx={{marginTop: 1 }}
                        />  
                
                <DateTimeComponent onChange={(date) => console.log('Selected:', date)} selectedDateTime={selectedDateTime} setSelectedDateTime = {setSelectedDateTime}/>      
            </DialogContent>
            {/* <Box sx={{width: "100%"}}>

            {activeWorkout.map((exercise, index)=> {
                return(
                <Box key = {index} sx={{ pb: 5, borderRadius: 1, border: 2, borderColor: theme.palette.secondary.main, position: "relative", alignItems: "flex-start"}}>
                    <Typography> {exercise.name}</Typography>
                    <SetsRepsDuration exercise={exercise}/>
                </Box>
                )
            })}
            </Box> */}
            <DialogActions>
                <Box >
                <Button onClick={() => setOpenCompleteWorkoutModal(false)}>
                        Cancel
                    </Button>
                    <Button type= "submit" autoFocus>
                        Save
                    </Button>
                </Box>
            </DialogActions>
            </form>
        </Dialog>
    )
}

// {
    // "workoutDetails": {
    // "username": "xusername",
    // "title": "xtitle",
    // "completedAt": "2025-04-13T14:00:00",
    // "notes": "xnotes"
    // },
    // "workoutExerciseDetails": [
    // {
    // "exerciseId": 1,
    // "sets": [
    // { "reps": 10, "weight": 50.0, "duration": 0, "distance": 0 },
    // { "reps": 8, "weight": 55.0, "duration": 0, "distance": 0 }
    // ]
    // },
    // {
    // "id": 2,
    // "sets": [
    // { "reps": 15, "weight": 0.0, "duration": 900, "distance": 2000 },
    // { "reps": 10, "weight": 0.0, "duration": 600, "distance": 1500 }
    // ]
    // }
    // ]
    // }
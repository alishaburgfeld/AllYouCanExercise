import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField, DialogContentText } from "@mui/material";
import { useTheme } from '@mui/material/styles';
import SetsRepsDuration from "./SetsRepsDuration"
import { postAxiosCall, toMeters, convertToSeconds, convertToJavaTime } from "../../utils/HelperFunctions";
import { useState, useEffect } from 'react';
import DateTimeComponent from "./DateTimeComponent";


export default function CompleteWorkoutModal({ openCompleteWorkoutModal, setOpenCompleteWorkoutModal, activeWorkout, setActiveWorkout, activeUsername, isWorkoutSaved, setIsWorkoutSaved, saveWorkoutError, setSaveWorkoutError}) {
    const theme = useTheme();
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const [workoutDetails, setWorkoutDetails] = useState(null)
    const [title, setTitle] = useState("")
    const [notes, setNotes] = useState("")
    const [selectedDateTime, setSelectedDateTime] = useState(new Date());
    

    
    const defineWorkoutDetails = () => {
        const timeFormattedForJava = convertToJavaTime(selectedDateTime);

        // console.log('in complete workout modal, active workout is', activeWorkout)
        let workoutExerciseDetails = [];
        let finalWorkoutDetails = {
            "workoutDetails": {
                "username": activeUsername,
                "title": title,
                "completedAt": timeFormattedForJava,
                "workoutNotes": notes
            }
        }
        activeWorkout?.forEach((exercise)=> {
            let sets = prepareSetsForBackend(exercise.sets);
            workoutExerciseDetails.push({"exerciseId": exercise.exerciseId, "sets": sets})
        })
        finalWorkoutDetails["workoutExerciseDetails"] = workoutExerciseDetails
        // console.log('finalWorkoutDetails are', finalWorkoutDetails)
       setWorkoutDetails(finalWorkoutDetails)
        // console.log('in complete workout modal, workoutDetails are', workoutDetails)
    }

    const handleSaveSuccess = () => {
        setIsWorkoutSaved(true)
        setTimeout(() => setOpenCompleteWorkoutModal(false), 500);
        setTimeout(() => setActiveWorkout(null), 500);
        // probably need to set the activeworkout to null and then redirect to homepage
      };

      const prepareSetsForBackend = (sets) => {
        console.log("666666 I'm in prepareSets for backend")
        const finalSets = [];

        sets.forEach((set) => {
            const finalSegments = set.segments.map((segment) => {
            const clonedSegment = { ...segment }; // shallow clone

            if (clonedSegment.duration) {
                clonedSegment.durationSeconds = convertToSeconds(clonedSegment.duration);
                delete clonedSegment.duration;
            }

            if (clonedSegment.distance) {
                const meters = toMeters(clonedSegment.distance, clonedSegment.distanceMeasurement);
                clonedSegment.distanceMeters = Math.round(meters * 100) / 100;
                delete clonedSegment.distance;
            }

            return clonedSegment;
            });

            finalSets.push({ segments: finalSegments });
        });

        return finalSets;
    };


    const handleSave = async () => {
        console.log('&^*** in handle save, workout details are', workoutDetails)
        const response = await postAxiosCall(`${VITE_API_BASE_URL}/workouts/full/save`, workoutDetails);
        if (response.success) {
            console.log('handleworkoutsave response', response)
            handleSaveSuccess();
            } else {
                console.error("save workout failed:", response.error);
                setSaveWorkoutError("An unexpected error occurred. Please try again.");
              }
    }

    useEffect(() => {
        if (openCompleteWorkoutModal) {
            defineWorkoutDetails();
        }
    }, [activeWorkout, title, selectedDateTime, notes, openCompleteWorkoutModal]);


    return(
        <>
        {activeWorkout!== null?
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
        
        : ""
    }
        
        </>
    )
}
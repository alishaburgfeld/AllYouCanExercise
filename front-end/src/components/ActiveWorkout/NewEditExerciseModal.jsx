import * as React from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Box,
  Typography,
  Paper,
} from "@mui/material"
import AddIcon from "@mui/icons-material/Add"
import DeleteIcon from "@mui/icons-material/Delete"
import { useState, useEffect } from "react";
import CardioSet from './CardioSet';
import RepSet from './RepSet';
import NewRepSet from './NewRepSet';
import { convertFromSeconds } from '../../utils/HelperFunctions';

export default function NewEditExerciseModal({ openEditExerciseModal, setOpenEditExerciseModal, exercise, updateActiveWorkoutWithNewStats }) {

    const [sets, setSets] = useState(exercise.sets);
    const [inputDistance, setInputDistance] = useState(null);
    const [inputDistanceMeasurement, setInputDistanceMeasurement] = useState(null);
    const [inputDuration, setInputDuration] = useState(null);

    const exerciseType = exercise.exerciseType;
    // console.log('distance on edit modal is', distance, 'duration on edit modal is', duration)

    const handleClose = () => {
        setOpenEditExerciseModal(false);
    };

    // TO-DO: SaveCardio and SaveRep are basically the same except for the sets - refacdtor this.

    const saveCardioEdits = () => {
        let updatedExerciseDetail = {...exercise}

        const newSets =
        [{"segments":
            [
                {
                "distance": inputDistance !== null ? inputDistance : exercise.sets[0]["segments"][0].distance, 
                "duration": inputDuration || exercise.sets[0]["segments"][0].duration, 
                "distanceMeasurement": inputDistanceMeasurement || exercise.sets[0]["segments"][0].distanceMeasurement
                }
            ]
        }]
        console.log('3) in savecardio edits newsets are', newSets)
        updatedExerciseDetail["sets"] = newSets;
        updateActiveWorkoutWithNewStats(updatedExerciseDetail)
        setOpenEditExerciseModal(false);
        // setInputDuration({"hours": parsedHours, "minutes": parsedMinutes, "seconds": parsedSeconds});
    }

    const saveRepEdits = () => {
        let updatedExerciseDetail = {...exercise}
            updatedExerciseDetail["sets"] = sets;
        console.log('4) EEM SaveReps updatedexdetails are', updatedExerciseDetail)
        updateActiveWorkoutWithNewStats(updatedExerciseDetail)
        setOpenEditExerciseModal(false);
    };


    const updateSets = (setIndex, segments) => {
        // Make a new copy of each segment so changes don’t affect other sets (avoid shared references)
        setSets(prevSets =>
            prevSets.map((s, i) =>
            i === setIndex ? { ...s, segments: segments.map(seg => ({ ...seg })) } : s
            )
        );
    };


    const addSet = () => {
        const lastSet = sets[sets.length - 1] || {};
        // this copies over the last set, but then replaces the inner segments array with a new array (same values, but now a different memory reference so the inner values can be changed independtly on the two sets)
        const newSet = {
            ...lastSet,
            segments: lastSet.segments ? lastSet.segments.map(seg => ({ ...seg })) : []
        };
        setSets([...sets, newSet]);
    };


    const removeSet = (setIndex) => {
    const newSets = [...sets]
    newSets.splice(setIndex,1)
    setSets(newSets);
  }

    return (
        <>
            <Dialog
                open={openEditExerciseModal}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                maxWidth="md"
                fullWidth
                fullScreen={false}
                sx={{
                    "& .MuiDialog-paper": {
                    m: { xs: 1, sm: 2 },
                    maxHeight: { xs: "calc(100% - 16px)", sm: "calc(100% - 64px)" },
                    },
                }}
            >
                <DialogTitle>
                    <Typography id="alert-dialog-title" variant="h5" component="div" sx={{ fontSize: { xs: "1.25rem", sm: "1.5rem" } }} />
                    {`Edit ${exercise.name} Stats`}
                </DialogTitle>
                <DialogContent>
                    <Box sx={{ display: "flex", flexDirection: "column", gap: { xs: 2, sm: 3 }, pt: { xs: 1, sm: 2 } }}>
                            {sets.map((set, setIndex) => (
                                <Paper key={setIndex} elevation={2} sx={{ p: { xs: 1.5, sm: 2 } }}>
                                    <Box
                                        sx={{
                                        display: "flex",
                                        justifyContent: "space-between",
                                        alignItems: "center",
                                        mb: { xs: 1.5, sm: 2 },
                                        flexWrap: "wrap",
                                        gap: 1,
                                        }}
                                    >
                                        <Typography variant="h6" component="div" sx={{ fontSize: { xs: "1rem", sm: "1.25rem" } }}>
                                            Set {setIndex + 1}
                                        </Typography>
                                        <Button
                                            onClick={() => removeSet(setIndex)}
                                            disabled={sets.length === 1}
                                            color="error"
                                            size="small"
                                            startIcon={<DeleteIcon />}
                                            variant="outlined"
                                            sx={{
                                                minWidth: { xs: "auto", sm: "auto" },
                                                px: { xs: 1, sm: 2 },
                                            }}
                                        >
                                            <Box component="span" sx={{ display: { xs: "none", sm: "inline" } }}>
                                            Delete Set
                                            </Box>
                                        </Button>
                                    </Box>

                                    <Box sx={{ display: "flex", flexDirection: "column", gap: { xs: 1.5, sm: 2 } }}>
                                        {exercise.exerciseType !== "CARDIO" ? (
                                            <>
                                            {/* <Typography> Test</Typography> */}
                                        <NewRepSet set={set} setIndex={setIndex} updateSets={updateSets}  />
                                            </>
                                        ): (
                                        <CardioSet exercise = {exercise} setInputDistance={setInputDistance} setInputDuration={setInputDuration} setInputDistanceMeasurement={setInputDistanceMeasurement}/>
                                        )
                                        }
                                    </Box>
                        
                                </Paper>
                            ))}
                            {exercise.exerciseType !== "CARDIO" ? (
                            <Button
                                startIcon={<AddIcon />}
                                onClick={addSet}
                                variant="contained"
                                size="large"
                                sx={{
                                py: { xs: 1, sm: 1.5 },
                                fontSize: { xs: "0.938rem", sm: "1rem" },
                                }}
                            >
                                Add Set
                            </Button>
                            ) : ""}
                    </Box>
                </DialogContent>
                <DialogActions disableSpacing
                    sx={{
                    p: { xs: 1.5, sm: 2 },
                    gap: { xs: 0.5, sm: 1 },
                    flexDirection: { xs: "column-reverse", sm: "row" },
                    
                    }}
                >
                    <Button onClick={handleClose} variant="outlined" fullWidth={true} sx={{ width: { xs: "100%", sm: "100%" } }}>
                    Cancel
                    </Button>
                    <Button onClick={() => (exerciseType === "CARDIO" ? saveCardioEdits() : saveRepEdits())} autoFocus variant="outlined" fullWidth={true} sx={{ ml: 0, width: { xs: "100%", sm: "100%" } }}>
                    Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}                                    

            


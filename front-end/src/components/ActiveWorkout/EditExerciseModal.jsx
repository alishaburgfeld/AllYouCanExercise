import * as React from 'react';
import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { useState, useEffect } from "react";
import CardioSet from './CardioSet';
import RepSet from './RepSet';
import { convertFromSeconds } from '../../utils/HelperFunctions';

export default function EditExerciseModal({ openEditExerciseModal, setOpenEditExerciseModal, exercise, updateActiveWorkoutWithNewStats }) {

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


    const updateSets = (setIndex, segments) =>{
        const newSets = [...sets];
        const updatedSet = sets[setIndex];
        updatedSet.segments = segments;
        newSets[setIndex] = updatedSet;
        console.log('newSets in EEM are', newSets);
        setSets(newSets);
    }

    const addSet = () => {
        const lastSet = sets[sets.length - 1] || {};
        const newSet = { ...lastSet }; // shallow copy
        setSets([...sets, newSet]);
        // duplicates the previous value into the new set value
    };

    return (
        <>
            <Dialog
                open={openEditExerciseModal}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                sx={{ width: '90%', maxWidth: 400, m:1 }}
                // possibly need to lower the margins
            >
                <DialogTitle id="alert-dialog-title" sx={{fontSize: 17}}>
                    {`Edit ${exercise.name} Stats`}
                </DialogTitle>
                    {exercise.exerciseType !== "CARDIO" ?
                        <>
                            {sets.map((set, index) => (
                                <>
                                <Typography sx={{pl: 2}}>Set {index +1}: </Typography> 
                                <RepSet set={set} key={index} setIndex={index} updateSets={updateSets} />
                                </>
                            ))}
                            <AddIcon onClick={addSet} sx={{ cursor: 'pointer' }} />
                        </>
                        : <CardioSet exercise = {exercise} setInputDistance={setInputDistance} setInputDuration={setInputDuration} setInputDistanceMeasurement={setInputDistanceMeasurement}/>
                    }
                <DialogActions>
                    <Box >
                        <Button onClick={() => (exerciseType === "CARDIO" ? saveCardioEdits() : saveRepEdits())} autoFocus>
                            Save
                        </Button>
                    </Box>
                </DialogActions>
            </Dialog>
        </>
    );
}

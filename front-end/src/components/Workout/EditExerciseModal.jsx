import * as React from 'react';
import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { useState } from "react";
import CardioSet from './CardioSet';
import RepSet from './RepSet';

export default function EditExerciseModal({ openEditExerciseModal, setOpenEditExerciseModal, exercise }) {

    const [sets, setSets] = useState([1]);

    const handleClose = () => {
        setOpenEditExerciseModal(false);
    };

    const exerciseType = exercise.exerciseType;

    const addSet = () => {
        setSets([...sets, sets.length + 1]); 
    };

    return (
        <>
            <Dialog
                open={openEditExerciseModal}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                sx={{ width: '90%', maxWidth: 400 }}
            >
                <DialogTitle id="alert-dialog-title" sx={{fontSize: 17}}>
                    {`Edit ${exercise.name} Stats`}
                </DialogTitle>
                <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "column", justifyContent: "center", alignItems: "center" }}>
                    {exercise.exerciseType !== "CARDIO" ?
                        <>
                            {sets.map((setCount) => (
                                <RepSet key={setCount} setCount={setCount} />
                            ))}
                            <AddIcon onClick={addSet} sx={{ cursor: 'pointer' }} />
                        </>
                        : <CardioSet />
                    }
                </Box>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

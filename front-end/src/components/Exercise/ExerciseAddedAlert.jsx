import * as React from 'react';
import { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import Alert from '@mui/material/Alert';

export default function ExerciseAddedAlert ({openExerciseAddedAlert, setOpenExerciseAddedAlert}) {
  
    const handleClose = () => {
      setOpenExerciseAddedAlert(false);
    };
  
    return (
        <>
        <Dialog
          open={openExerciseAddedAlert}
          onClose={handleClose}
        >
        <Alert severity="success">Exercise added to workout!</Alert>
        </Dialog>
      </>
    );
  }
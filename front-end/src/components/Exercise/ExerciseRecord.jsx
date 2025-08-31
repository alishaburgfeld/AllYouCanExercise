import { useState, useEffect } from "react"
import { Typography, Box } from "@mui/material";
import {formatExerciseDurationIntoMinutesAndSeconds} from "../../utils/HelperFunctions"

export default function ExerciseRecord({exerciseRecord}) {

    const [maxSets, setMaxSets] = useState(null);
    const [maxReps, setMaxReps] = useState(null);
    const [maxWeight, setMaxWeight] = useState(null);
    const [maxVolume, setMaxVolume] = useState(null);
    const [maxDistance, setMaxDistance] = useState(null);
    const [maxDuration, setMaxDuration] = useState(null);
    const [maxPacePerMile, setMaxPacePerMile] = useState(null);

    console.log('on exerciseRecord, exrec is', exerciseRecord)

    const setMaxValues =() =>{
        setMaxSets(exerciseRecord.maxSets);
        setMaxReps(exerciseRecord.maxReps);
        setMaxWeight(exerciseRecord.maxWeight);
        setMaxVolume(exerciseRecord.maxVolume);
        setMaxDistance(exerciseRecord.maxDistanceMeters);
        const duration = formatExerciseDurationIntoMinutesAndSeconds(exerciseRecord.maxDurationSeconds)
        console.log('er duration is', duration)
        setMaxDuration(duration);
        setMaxPacePerMile(exerciseRecord.maxPacePerMile);
    }

    useEffect(() => {
          setMaxValues();
        }, [exerciseRecord]);

    return (
    <Box sx={{ display: 'flex', flexDirection: "column", alignItems: "flex-start"}}> 
        {console.log('maxDistance is', maxDistance, 'type', typeof(maxDistance))}
        {maxDistance==0?
        <>
        <Box sx={{ display: 'flex', flexWrap: 'wrap'}}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Sets: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxSets}</Typography>
        </Box>
        <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Reps: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxReps}</Typography>
        </Box>
        <Box sx={{ display: 'flex', flexWrap: 'wrap'}}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Weight: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxWeight}</Typography>
        </Box>
        <Box sx={{ display: 'flex', flexWrap: 'wrap'}}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Volume: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxVolume}</Typography>
        </Box>
        </>
        
    :
    <>
        <Box sx={{ display: 'flex', flexWrap: 'wrap', alignItems: 'flex-start' }}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Distance: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxDistance}</Typography>
        </Box>
        <Box sx={{ display: 'flex', flexWrap: 'wrap', alignItems: 'flex-start' }}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Duration: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxDuration}</Typography>
        </Box>
        {maxPacePerMile !==0?
            <Box sx={{ display: 'flex', flexWrap: 'wrap', alignItems: 'flex-start' }}>
            <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Max Pace Per Mile: </Typography>
            <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{maxPacePerMile}</Typography>
        </Box>
        : ""
        }
    </>
    }

    </Box>

    )
}
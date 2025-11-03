import { Typography, Box} from "@mui/material";
import {displayReps} from "../../utils/HelperFunctions";

export default function CompletedExercise({exercise, index}) {

    function formatRepSets(exercise) {
        let combinedSets = displayReps(exercise);
        
        return (
            combinedSets.map((setString, index) => (
            <Typography
                key={index}
                sx={{ ml: 2, fontSize: "1.1em", mb: ".3em" }}
            >
                {setString}
            </Typography>
            ))
        );
    }


    function formatCardioSet(set, index) {
        const durationSeconds = set.segments[0].durationSeconds || null;
        const distanceMeters = set.segments[0].distanceMeters || null;
        
            if ((durationSeconds !==null)  && (distanceMeters !== null)) {
                const hours = Math.floor(durationSeconds / 3600);
                const minutes = Math.floor((durationSeconds % 3600) / 60);
                const seconds = durationSeconds % 60;
                const distanceMiles = (distanceMeters / 1609.34).toFixed(2);
                // return `Set ${index + 1}: ${hours > 0 ? hours + 'h ' : ""} ${minutes}m ${seconds}s for ${distanceMiles} miles`;
                return (
                    <>
                    <Typography sx={{ ml: 2, fontSize: "1.1em", mb: ".3em" }}>
                        {`Distance: ${distanceMiles} miles`}
                    </Typography>
                    <Typography sx={{ ml: 2, fontSize: "1.1em", mb: ".3em" }}>
                        {`Duration: ${hours > 0 ? hours + 'h ' : ""} ${minutes}m ${seconds}s`}
                    </Typography>
                    </>
                )
            }
            if (durationSeconds !== null) {
                const hours = Math.floor(durationSeconds / 3600);
                const minutes = Math.floor(durationSeconds / 60);
                const seconds = durationSeconds % 60;
                return (
                    <Typography sx={{ ml: 2, fontSize: "1.1em", mb: ".3em" }}>
                        {`Duration: ${hours > 0 ? hours + 'h ' : ""} ${minutes}m ${seconds}s`}
                    </Typography>
                )
            }
            return `Set ${index + 1}: No data`;
        }
    return (
    <Box key={index} sx={{ pl: 2, mb: 1 }}>
        <Typography sx={{ fontSize: "1.1em", mb: ".3em" }} fontWeight={500}>
            {exercise.exerciseName}
        </Typography>
        { 
        exercise.exerciseGroup !== 'CARDIO' ? (
            formatRepSets(exercise)
            ) : (
            exercise.sets.map((set, j) => (
                formatCardioSet(set, j)
            ))
        )}
    </Box>
    );

}
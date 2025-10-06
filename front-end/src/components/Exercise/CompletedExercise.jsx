import { Typography, Box} from "@mui/material";


export default function CompletedExercise({exercise, index}) {

    function formatSet(set, index) {
        console.log("I'm in format Sets on Completed Exercise")
            if (set.reps || set.weight) {
                return `Set ${index + 1}: ${set.reps} reps at ${set.weight} lbs`;
            }
            if (set.durationSeconds && set.distanceMeters) {
                const hours = Math.floor(set.durationSeconds / 3600);
                const minutes = Math.floor((set.durationSeconds % 3600) / 60);
                const seconds = set.durationSeconds % 60;
                const distanceMiles = (set.distanceMeters / 1609.34).toFixed(2);
                return `Set ${index + 1}: ${hours > 0 ? hours + 'h ' : ""} ${minutes}m ${seconds}s for ${distanceMiles} miles`;
            }
            if (set.durationSeconds) {
                const hours = Math.floor(set.durationSeconds / 3600);
                const minutes = Math.floor(set.durationSeconds / 60);
                const seconds = set.durationSeconds % 60;
                return `Set ${index + 1}: ${hours > 0 ? hours + 'h ' : ""} ${minutes}m ${seconds}s`;
            }
            return `Set ${index + 1}: No data`;
        }

    return (
        <Box key={index} sx={{ pl: 2, mb: 1 }}>
            <Typography sx={{fontSize: "1.1em", mb:".3em"}} fontWeight={500}>
            {exercise.exerciseName}
            </Typography>

            {exercise.sets.map((set, j) => (
            <Typography
                key={j}
                sx={{ ml: 2, fontSize: "1.1em", mb: ".3em" }}
            >
                {formatSet(set, j)}
            </Typography>
            ))}
        </Box>
    )
}
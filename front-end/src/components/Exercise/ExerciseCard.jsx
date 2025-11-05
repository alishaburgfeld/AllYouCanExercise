import Box from '@mui/material/Box';
import { Card, CardMedia, Typography, CardContent, IconButton } from "@mui/material"
import {useNavigate } from "react-router-dom";
import { useTheme } from '@mui/material/styles';
import { getImageSource } from "../../utils/HelperFunctions"

function ExerciseCard ({exercise}) {
    const theme = useTheme();
    const navigate = useNavigate();

    const handleClick= (exerciseId) => {
        navigate(`/exercise/${exerciseId}`);
    }

    return (
        <Card 
        key={exercise.id} className="exerciseGroup_items" 
        onClick={() => handleClick(exercise.id)} 
        sx=
        {{
            borderRadius: "8px",
            transition: "box-shadow 0.3s ease",
            border:2, 
            borderColor: theme.palette.secondary.main,
            "&:hover": {
                boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
                "& img": {
                    transform: "scale(1.05)",
                },
                "& .add-button": {
                    opacity: 1,
                },
            },

        }}
        >
            <CardContent>
                <CardMedia src={getImageSource(exercise.name)} 
                    className="exerciseGroup_photo" alt={exercise.name}
                    component="img"
                    sx={{
                        width: "100%",
                        height: { xs: 180, sm: 220, md: 260, lg: 300 },
                        objectFit: "cover",
                        transition: "transform 0.3s ease",
                        "&:hover": { transform: "scale(1.05)" },
                    }}
                    
                />
                <Typography align="center" className="exerciseGroup_name"> {exercise.name}</Typography>

            </CardContent>
        </Card>
    )
}

export default ExerciseCard
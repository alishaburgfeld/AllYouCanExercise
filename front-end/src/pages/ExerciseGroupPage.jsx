import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import { getAxiosCall } from "../utils/HelperFunctions"
import Box from '@mui/material/Box';
import "../css/ExerciseGroupPage.css";
import { Typography} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource } from "../utils/HelperFunctions";

// TO-DO: Eventually I can create a component for "records" etc. each of these components can be querying different tables in my database so that I don't have to worry about everything being on one record
function ExerciseGroupPage() {

    const theme = useTheme();
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const { exerciseGroup } = useParams();
    const navigate = useNavigate();
    const [exercisesByGroup, setExercisesByGroup] = useState([]);
    
    
    const getExercisesByGroup = async () => {
        const response = await getAxiosCall(`${VITE_API_BASE_URL}/exercises/group/${exerciseGroup}`);
        if (response) {
            setExercisesByGroup(response);
        } else {
            console.error("Error fetching exercises:");
        }
    };

    const handleClick= (exerciseId) => {
        navigate(`/exercise/${exerciseId}`);
    }

    
    


  useEffect(()=> {
    getExercisesByGroup();
  }, [exerciseGroup])
  
    return (
        
        <Box className="exerciseGroup">
            <Typography className="exerciseGroup_title" sx={{fontSize:"1.8rem", pt:"4rem", color: theme.palette.secondary.main}}>
                {exerciseGroup}
            </Typography>
            <Box className="exerciseGroup_ItemContainer">

                {exercisesByGroup.map((exercise)=> (
                    <Box key={exercise.id} className="exerciseGroup_items" onClick={() => handleClick(exercise.id)} sx={{borderRadius: 1, border:2, borderColor: theme.palette.secondary.main}}>
                        <img src={getImageSource(exercise.name)} className="exerciseGroup_photo" alt={exercise.name}/>
                        <Typography align="center" className="exerciseGroup_name"> {exercise.name}</Typography>
                    </Box>
                ))}
            </Box>
            
        </Box>
    )
}

export default ExerciseGroupPage 


// todos: add the ability to add an exercise
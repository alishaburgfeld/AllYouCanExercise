import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios'
import Cookies from 'js-cookie';
import Box from '@mui/material/Box';
import "../css/ExerciseGroupPage.css";
import { Typography} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource } from "../utils/HelperFunctions";

// TO-DO: Eventually I can create a component for "records" etc. each of these components can be querying different tables in my database so that I don't have to worry about everything being on one record
function ExerciseGroupPage() {

    const theme = useTheme();
    const { exerciseGroup } = useParams();
    const navigate = useNavigate();
    const [exercisesByGroup, setExercisesByGroup] = useState([]);
    
    
    const getExercisesByGroup = async () => {
        const csrfToken = Cookies.get('XSRF-TOKEN'); // Read the CSRF token from the cookie
        try {
            const response = await axios.get(`http://localhost:8080/api/exercises/group/${exerciseGroup}`, {
                headers: {
                'X-XSRF-TOKEN': csrfToken,
                },
                withCredentials: true, 
            });
            setExercisesByGroup(response.data);
        } catch (error) {
            console.error("Error fetching exercises:", error);
        }
    };

    const handleClick= (exerciseId) => {
        navigate(`/${exerciseId}`);
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
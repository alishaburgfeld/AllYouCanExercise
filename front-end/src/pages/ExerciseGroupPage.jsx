import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios'
import Cookies from 'js-cookie';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import "../css/ExerciseGroupPage.css";
import { Typography } from "@mui/material";
import { exerciseOptions, fetchRapidData } from '../utils/RapidApiInfo'

// TO-DO: Eventually I can create a component for "records" etc. each of these components can be querying different tables in my database so that I don't have to worry about everything being on one record
function ExerciseGroupPage() {

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
                withCredentials: true, // Include cookies in the request
            });
            console.log("response is", response, "response.data is", response.data);
            setExercisesByGroup(response.data);
        } catch (error) {
            console.error("Error fetching exercises:", error);
        }
    };

    const handleClick= (exerciseId) => {
        navigate(`/${exerciseId}`);
    }

    const getImageSource = (name) => {
        try {
            return require(`../assets/images/${name}.png`); 
        } catch (error) {
            return require("../assets/images/noexerciseimage.png");
        }
    };
    


  useEffect(()=> {
    console.log('exercise group is', exerciseGroup)
    getExercisesByGroup();
  }, [exerciseGroup])

//   useEffect(() => {
//     if (rapidUrl) {
//         getRapidExercises();
//     }
// }, [rapidUrl]);


  
    return (
        
        <Box className="exerciseGroup">
            <Typography variant="h3" className="exerciseGroup_title">
                {exerciseGroup}
            </Typography>
            {exercisesByGroup.map((exercise)=> (
                <>
                <Link key={exercise.id} className="exerciseGroup_name" onClick={() => handleClick(exercise.id)} underline="none">{exercise.name}
                <img src={getImageSource(exercise.name)} className="exerciseGroup_photo" alt={exercise.name} />
                </Link>
                </>
            ))}
            
        </Box>
    )
}

export default ExerciseGroupPage 
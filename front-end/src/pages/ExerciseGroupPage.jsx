import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios'
import Cookies from 'js-cookie';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import "../css/ExerciseGroupPage.css";
import { Typography } from "@mui/material";



function ExerciseGroupPage() {

    const { exerciseGroup } = useParams();
    const navigate = useNavigate();
    // console.log("Extracted exerciseGroup:", exerciseGroup);
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

  useEffect(()=> {
    getExercisesByGroup();
  }, [exerciseGroup])

    return (
        
        <Box className="exerciseGroup">
            <Typography variant="h3">
                {exerciseGroup}
            </Typography>
            {exercisesByGroup.map((exercise)=> (
                <>
                {console.log("exercise is", exercise.name)}
                <Link key={exercise.id} onClick={() => handleClick(exercise.id)}>{exercise.name}
                </Link>
                </>
            ))}
            
        </Box>
    )
}

export default ExerciseGroupPage 
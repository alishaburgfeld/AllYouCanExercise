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

    const apiKey = process.env.REACT_APP_RAPID_API_KEY;
    const [rapidUrl, setRapidUrl] = useState('');
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

    const getRapidExercises = async () => {
        try {
            const exercises = await fetchRapidData(rapidUrl,exerciseOptions)
            console.log(exercises);
        } catch (error) {
            console.error(error);
        }
    }

    const handleClick= (exerciseId) => {
        navigate(`/${exerciseId}`);
    }

    const determineRapidUrl = () => {
        const targetUrl= 'https://exercisedb.p.rapidapi.com/exercises/target/'
        const bodyPartUrl='https://exercisedb.p.rapidapi.com/exercises/bodyPart/'
        var finalUrl = ""

        if (["SHOULDERS","CHEST","CARDIO"].includes(exerciseGroup)) {
            // still need to figure out obliques and lower back.
            finalUrl = (bodyPartUrl + exerciseGroup.toLowerCase())
        }
        else {
            finalUrl = (targetUrl + exerciseGroup.toLowerCase())
        }
        console.log("rapidUrl is currently", rapidUrl, "final url is", finalUrl);
        setRapidUrl(finalUrl);
        
    }


  useEffect(()=> {
    console.log('exercise group is', exerciseGroup)
    determineRapidUrl();
    getExercisesByGroup();
  }, [exerciseGroup])

  useEffect(() => {
    if (rapidUrl) {
        getRapidExercises();
    }
}, [rapidUrl]);



  
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
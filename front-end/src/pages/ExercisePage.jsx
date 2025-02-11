
import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios'
import Cookies from 'js-cookie';


function ExercisePage() {

    const { exerciseId } = useParams();
    const [exercise, setExercise] = useState({});
    
    
    const getExercise = async () => {
        const csrfToken = Cookies.get('XSRF-TOKEN'); // Read the CSRF token from the cookie
        try {
            const response = await axios.get(`http://localhost:8080/api/exercises/${exerciseId}`, {
                headers: {
                'X-XSRF-TOKEN': csrfToken,
                },
                withCredentials: true, // Include cookies in the request
            });
            console.log("response is", response, "response.data is", response.data);
            setExercise(response.data);
        } catch (error) {
            console.error("Error fetching exercises:", error);
        }
    };

  useEffect(()=> {
    getExercise();
  }, [exerciseId])

    return (`exercise is, ${exercise.name}, ${exercise.description}`);
}

export default ExercisePage;
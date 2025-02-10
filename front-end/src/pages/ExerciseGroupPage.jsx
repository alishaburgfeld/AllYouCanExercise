import { useState, useEffect } from "react"
import { useParams } from "react-router-dom";
import axios from 'axios'
import Cookies from 'js-cookie';
import "../css/ExerciseGroupPage.css";



function ExerciseGroupPage() {

    const { exerciseGroup } = useParams();
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

  useEffect(()=> {
    getExercisesByGroup();
  }, [exerciseGroup])

    return (
        
        <div className="exerciseGroup">
            <div>"Exercises are" {exercisesByGroup.map((exercise, index) => (
                <div key={index}>
                    <h4>{`Exercise-${exercise.id} details:`}</h4>
                    <span>{`name: ${exercise.name}`}</span>
                    <span>{`exercise group: ${exercise.exerciseGroup}`}</span>
                    <span>{`type: ${exercise.exerciseType}`}</span>
                    <span>{`description: ${exercise.description}`}</span>
                    <br />
                </div>
            ))}</div>
            
        </div>
    )
}

export default ExerciseGroupPage 
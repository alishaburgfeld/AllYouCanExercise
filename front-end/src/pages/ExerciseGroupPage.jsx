import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import { getAxiosCall } from "../utils/HelperFunctions"
import Box from '@mui/material/Box';
import { Typography} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource } from "../utils/HelperFunctions";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ExerciseCard from "../components/Exercise/ExerciseCard";
import Header from "../components/Shared/Header.jsx";


function ExerciseGroupPage({setExerciseToBeAdded, activeUsername, setActiveWorkout, activeWorkout,}) {

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

    const handleBackClick= () => {
        navigate(`/`);
    }
    
  useEffect(()=> {
    getExercisesByGroup();
  }, [exerciseGroup])
  
return (
        
        <Box className="exerciseGroup" sx={{ minHeight: "100vh",
         pt:12

         }}>
            <Header 
                title={exerciseGroup} 
                icon={ <ArrowBackIosIcon sx={{
                    color: theme.palette.secondary.main,
                    fill: theme.palette.secondary.main,
                    fontSize: "2rem",
                    }}/>
                    } 
                onIconClick={() => handleBackClick()}
                typographyClassName={"exerciseGroup_title"}
            />
                    {/* <SearchBar 
                        sx={{
                        position: "absolute",
                        right:0,
                        }}
                        value={searchQuery} onChange={setSearchQuery} 
                    /> */}
                
            <Box className="exerciseGroup_ItemContainer"
            sx={{ px: { xs: 2, sm: 3, lg: 4 }, py: 4, pt: 6,
                display: "grid",
                gap: 2,
                gridTemplateColumns: {
                xs: "repeat(2, 1fr)", // 2 columns by default
                md: "repeat(3, 1fr)", // 3 columns on medium+
                lg: "repeat(4, 1fr)", // 4 columns on large+
                },
            }}
            >

                {exercisesByGroup.map((exercise)=> (
                    <ExerciseCard exercise={exercise} setExerciseToBeAdded={setExerciseToBeAdded} activeUsername={activeUsername} activeWorkout={activeWorkout} setActiveWorkout={setActiveWorkout} />
                ))}
            </Box>
            
        </Box>
    )
}

export default ExerciseGroupPage 


// todos: add the ability to add an exercise
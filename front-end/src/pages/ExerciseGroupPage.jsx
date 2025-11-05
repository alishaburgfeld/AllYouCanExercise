import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import { getAxiosCall } from "../utils/HelperFunctions"
import Box from '@mui/material/Box';
import { Typography} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource } from "../utils/HelperFunctions";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ExerciseCard from "../components/Exercise/ExerciseCard";


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
            <Box
                sx={{
                position: "fixed",
                top: 55,
                left: 0,
                right: 0,
                bgcolor: "background.default",
                // bgcolor: "red",
                borderBottom: "1px solid",
                borderColor: "divider",
                zIndex: 1000,
                py: 1,
                pt: 1.5,
                px: { xs: 2, sm: 3, lg: 4 },
                }}
            >   
                <Box sx={{
                    position: "relative",
                    maxWidth: "1280px",
                    mx: "auto",
                    display: "flex",
                    justifyContent: "center", // centers the title horizontally
                    alignItems: "center",     // vertically aligns both
                    // py: 2,
                    }}
                >
                    <ArrowBackIosIcon sx={{
                    color: theme.palette.secondary.main,
                    fill: theme.palette.secondary.main, // force fill color
                    // stroke: theme.palette.secondary.main,
                    fontSize: "2rem",
                    position: "absolute",
                    left: 0,
                    }}
                    onClick={() => handleBackClick()}
                    />
                    <Typography className="exerciseGroup_title" sx={{fontSize:"1.8rem", color: theme.palette.secondary.main, textWrap: "balance",}}>
                        {exerciseGroup}
                    </Typography>
                    {/* <SearchBar 
                        sx={{
                        position: "absolute",
                        right:0,
                        }}
                        value={searchQuery} onChange={setSearchQuery} 
                    /> */}
                </Box>
                
            </Box>
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
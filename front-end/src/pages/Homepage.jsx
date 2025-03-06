import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import front from "../assets/images/woman-front-two.png";
import back from "../assets/images/woman-back-two.png";
import Box from '@mui/material/Box';
import CameraswitchIcon from '@mui/icons-material/Cameraswitch';
import { useTheme } from '@mui/material/styles';
import "../css/Homepage.css";
import { IconButton } from "@mui/material";




function Homepage() {

    const theme = useTheme();
    const [frontAngle, setfrontAngle] = useState(true);
    const [angle, setAngle] = useState("front");
    const frontLabels = ['SHOULDERS','CHEST','FOREARMS','OBLIQUES','QUADS','ADDUCTORS','ABS','BICEPS','CARDIO']
    const backLabels = ['CARDIO','TRAPS','TRICEPS','ABDUCTORS','HAMSTRINGS','CALVES','LATS','LOWER_BACK','GLUTES']
    const [imageSource, setImageSource] = useState(front);
    const [labels, setLabels] = useState(frontLabels);
    
    const navigate = useNavigate();

    const handleLabelClick = (exerciseGroup) => {
        navigate(`/exercises/${exerciseGroup}`);
    };

    const changeLabels = () =>{
        if (frontAngle) {
            setLabels(frontLabels);
            setImageSource(front);
            setAngle("front");
        }
        else {
            setLabels(backLabels);
            setImageSource(back);
            setAngle("back");
        }
    }


    useEffect(()=> {
        changeLabels();
      }, [frontAngle])

    function handleAngleSwitch()  {
        setfrontAngle(!frontAngle);
    }
    
    return (
        
        <Box className="homepage">
            {/* {console.log("theme secondary is", theme.palette.secondary.main)} */}
                <Box className="homepage_image">
                    <img src={imageSource} alt={`woman-${angle}-body`} className={`homepage_image_${angle}`}/>
                    {labels.map((label) => (
                    <span key={label} role="link" className={`homepage_label_${label}`} aria-label={`homepage_label_${label}`} onClick={() => handleLabelClick(label)}>{label}</span>
                ))}
                </Box>
            <IconButton className = "homepage_camera" aria-label="camera-switch" sx={{color: theme.palette.secondary.main, position: "absolute", right: "42%", top: "13%"}} onClick={() => handleAngleSwitch()}>
                <CameraswitchIcon fontSize ="large"/>
            </IconButton>
        </Box>
    )
}

export default Homepage
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import front from "../assets/images/woman-front-two.png";
import back from "../assets/images/woman-back-two.png";

import "../css/Homepage.css";


function Homepage() {

    const [angle, setAngle] = useState("front");
    const frontLabels = ['SHOULDERS','CHEST','FOREARMS','OBLIQUES','QUADS','ADDUCTORS','ABS','BICEPS','CARDIO']
    const backLabels = ['CARDIO','TRAPS','TRICEPS','ABDUCTORS','HAMSTRINGS','CALVES','LATS','LOWER_BACK','GLUTES']
    const [angleLabels, setAngleLabels] = useState(frontLabels)
    const navigate = useNavigate();

    const handleLabelClick = (exerciseGroup) => {
        navigate(`/exercises/${exerciseGroup}`);
    };

    // need to do a function that on some button click it will change angle to back, and will also change the labels {setAngleLabels(backLabels)}.
    
    return (
        
        <div className="homepage">
            {console.log("angle is", angle)}
            
            {angle==="front" ?
                <div className="homepage_image">
                    <img src={front} alt="woman-front-body" className="homepage_image_front"/>
                    {frontLabels.map((label) => (
                    <>
                    {console.log("in front labels map", label)}
                    <span key={label} className={`homepage_label_${label}`} onClick={() => handleLabelClick(label)}>{label}</span>
                    </>
                ))}
                </div>
                :
                <div className="homepage_image">
                    <img src={back} alt="woman-back-body" className="homepage_image_back"/>
                    {backLabels.map((label) => (
                    <>
                    {console.log("in back labels map", label)}
                    <span key={label} className={`homepage_label_${label}`} onClick={() => handleLabelClick(label)}>{label}</span>
                    </>
                ))}
                </div>
            }
        </div>
    )
}

{/* map through the labels instead, each label will navigate to exercisegroup page. Additionally, try putting the labels inside the same div as the image, so hopefully they'll stay with the responsive image */}
export default Homepage
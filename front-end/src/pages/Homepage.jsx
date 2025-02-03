import { useEffect, useState } from "react"
import front from "../assets/images/woman-front.jpg";
import back from "../assets/images/woman-back.jpg";

import "../css/Homepage.css";


function Homepage({exercises}) {

    const [angle, setAngle] = useState("front");
    const frontLabels = ['SHOULDERS','CHEST','FOREARMS','OBLIQUES','QUADS','ADDUCTORS','ABS','BICEPS','CARDIO']
    const backLabels = ['CARDIO','TRAPS','TRICEPS','ABDUCTORS','HAMSTRINGS','CALVES','LATS','LOWER_BACK','GLUTES']
    const [angleLabels, setAngleLabels] = useState(frontLabels)

    const checkGroupLabels = function (exercise) {
        if(angleLabels.includes(exercise.exerciseGroup)) {
            console.log("inside the if")
            return(
                <div className={`homepage_label ${exercise.exerciseGroup}`}>
            <span className={`homepage_span`}>{exercise.exerciseGroup}</span>
            {/* need to make this a link to exerciseGroupPage */}
            <br />
        </div>
            )       
        }
        
    }

    // need to do a function that on some button click it will change angle to back, and will also change the labels {setAngleLabels(backLabels)}.
    
    return (
        
        <div className="homepage">
            {/* {console.log("angle is", angle)} */}
            <div className="homepage_image">
            {angle==="front" ?
                <img src={front} alt="woman-front-body" className="homepage_image_front"/>
            :
                <img src={back} alt="woman-back-body" className="homepage_image_back"/>}
            </div>
            {exercises.map((exercise) => (
                checkGroupLabels(exercise)
            ))}
        </div>
    )
}

export default Homepage
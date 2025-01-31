import { useState } from "react"
import front from "../assets/images/woman-front.jpg";
import back from "../assets/images/woman-back.jpg";

import "../css/Homepage.css";


function Homepage({exercises}) {

    const [angle, setAngle] = useState("front");

    return (
        
        <div className="homepage">
            {console.log("angle is", angle)}
            <div className="homepage_image">
            {angle==="front" ?
                <img src={front} alt="woman-front-body" className="homepage_image_front"/>
            :
                <img src={back} alt="woman-back-body" className="homepage_image_back"/>}
            </div>
            {exercises.map((exercise, index) => (
                <div className={`homepage_label ${exercise.exerciseGroup}`}>
                    <span className={`homepage_span`}>{exercise.exerciseGroup}</span>
                    {/* need to make this a link to exerciseGroupPage */}
                    <br />
                </div>
            ))}</div>
    )
}

export default Homepage
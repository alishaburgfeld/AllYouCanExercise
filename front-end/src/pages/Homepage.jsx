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
            <div>"Exercises are" {exercises.map((exercise, index) => (
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

export default Homepage
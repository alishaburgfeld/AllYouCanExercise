import { useState } from "react"
import front from "../assets/images/woman-front.jpg";
import back from "../assets/images/woman-back.jpg";

import "../css/Homepage.css";


function Homepage() {

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
            
        </div>
    )
}

export default Homepage
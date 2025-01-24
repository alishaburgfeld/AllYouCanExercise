import { useState } from "react"
import front from "../assets/images/woman-front.jpg";
import back from "../assets/images/woman-back.jpg";

import "../css/Homepage.css";


function Homepage() {

    const [angle, setAngle] = useState("front");

    return (
        
        <div className="homepage">
            {console.log("angle is", angle)}
            <div className="image div">
            {angle==="front" ?
                <img src={front} alt="woman-front-body" className="main-image-front"/>
            :
                <img src={back} alt="woman-back-body" className="main-image-back"/>}
            </div>
            
        </div>
    )
}

export default Homepage
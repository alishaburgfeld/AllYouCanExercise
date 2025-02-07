import { useState } from "react"

import "../css/ExerciseGroupPage.css";


function ExerciseGroupPage({exercises}) {


    return (
        
        <div className="exerciseGroup">
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

export default ExerciseGroupPage
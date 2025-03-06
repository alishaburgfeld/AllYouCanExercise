export default function WorkoutPage({workout}) {
    return (
        <>
        <h2>WorkoutPage</h2>
        {workout?
            workout.map((exercise)=> {
                <p>{exercise.name}</p>
            })
            :
        <p>Add an exercise to see your workout!</p>
        }
        </>
    )
}   
    // todos:
// need a way to save the workout - either completed or plan for the future
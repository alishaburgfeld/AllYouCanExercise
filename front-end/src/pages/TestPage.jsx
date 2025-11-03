import { useState } from "react"
import { Button, Container, Typography, Box } from "@mui/material"
// import TestExerciseEditModal from "../components/ActiveWorkout/TestExerciseEditModal"


export default function TestPage () {
  return (<><Typography>Hello</Typography></>)
}

//   const [open, setOpen] = useState(false)
//     const [exercise, setExercise] = useState({
//       name: "Bench Press",
//       sets: [
//         {
//           segments: [{ reps: 10, weight: 20 }],
//         },
//         {
//           segments: [
//             { reps: 8, weight: 20 },
//             { reps: 2, weight: 25 },
//           ],
//         },
//       ],
//     })
  
//     const handleSave = (updatedExercise) => {
//       setExercise(updatedExercise)
//       setOpen(false)
//       console.log("Saved exercise:", updatedExercise)
//     }
  
//     const handleCancel = () => {
//       setOpen(false)
//     }

//   return (
//     <Container maxWidth="md" sx={{ py: 8 }}>
//       <Box sx={{ textAlign: "center", mb: 4 }}>
//         <Typography variant="h3" component="h1" gutterBottom>
//           Exercise Editor Demo
//         </Typography>
//         <Typography variant="body1" color="text.secondary" paragraph>
//           Click the button below to edit the exercise data
//         </Typography>
//       </Box>

//       <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mb: 4 }}>
//         <Typography variant="h5">{exercise.name}</Typography>
//         {exercise.sets.map((set, setIndex) => (
//           <Box key={setIndex}>
//             <Typography variant="body1">
//               <strong>Set {setIndex + 1}:</strong>{" "}
//               {set.segments.map((seg, segIndex) => (
//                 <span key={segIndex}>
//                   {segIndex > 0 && " and "}
//                   {seg.reps} reps, {seg.weight} lbs
//                 </span>
//               ))}
//             </Typography>
//           </Box>
//         ))}
//       </Box>

//       <Button variant="contained" size="large" onClick={() => setOpen(true)}>
//         Edit Exercise
//       </Button>

//       <TestExerciseEditModal open={open} exercise={exercise} onSave={handleSave} onCancel={handleCancel} />
//     </Container>
//   )
//       // <ExerciseAddedAlert />
// }


// "use client"

// import { useState } from "react"
// import {
//   Dialog,
//   DialogTitle,
//   DialogContent,
//   DialogActions,
//   Button,
//   TextField,
//   IconButton,
//   Box,
//   Typography,
//   Divider,
//   Paper,
// } from "@mui/material"
// import AddIcon from "@mui/icons-material/Add"
// import DeleteIcon from "@mui/icons-material/Delete"
// import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline"

// interface Segment {
//   reps: number
//   weight: number
// }

// interface Set {
//   segments: Segment[]
// }

// interface Exercise {
//   name: string
//   sets: Set[]
// }

// interface ExerciseEditModalProps {
//   open: boolean
//   exercise: Exercise
//   onSave: (exercise: Exercise) => void
//   onCancel: () => void
// }

// export default function ExerciseEditModal({ open, exercise, onSave, onCancel }: ExerciseEditModalProps) {
//   const [editedExercise, setEditedExercise] = useState<Exercise>(exercise)

//   const handleSegmentChange = (setIndex: number, segmentIndex: number, field: "reps" | "weight", value: string) => {
//     const newExercise = { ...editedExercise }
//     newExercise.sets[setIndex].segments[segmentIndex][field] = Number(value) || 0
//     setEditedExercise(newExercise)
//   }

//   const addSegment = (setIndex: number) => {
//     const newExercise = { ...editedExercise }
//     const currentSet = newExercise.sets[setIndex]
//     const lastSegment = currentSet.segments[currentSet.segments.length - 1]

//     // Copy values from the previous segment
//     currentSet.segments.push({
//       reps: lastSegment.reps,
//       weight: lastSegment.weight,
//     })

//     setEditedExercise(newExercise)
//   }

//   const removeSegment = (setIndex: number, segmentIndex: number) => {
//     const newExercise = { ...editedExercise }
//     // Don't allow removing the last segment in a set
//     if (newExercise.sets[setIndex].segments.length > 1) {
//       newExercise.sets[setIndex].segments.splice(segmentIndex, 1)
//       setEditedExercise(newExercise)
//     }
//   }

//   const addSet = () => {
//     const newExercise = { ...editedExercise }
//     const lastSet = newExercise.sets[newExercise.sets.length - 1]

//     // Copy the structure from the previous set
//     const newSet: Set = {
//       segments: lastSet.segments.map((segment) => ({
//         reps: segment.reps,
//         weight: segment.weight,
//       })),
//     }

//     newExercise.sets.push(newSet)
//     setEditedExercise(newExercise)
//   }

//   const removeSet = (setIndex: number) => {
//     const newExercise = { ...editedExercise }
//     // Don't allow removing the last set
//     if (newExercise.sets.length > 1) {
//       newExercise.sets.splice(setIndex, 1)
//       setEditedExercise(newExercise)
//     }
//   }

//   const handleSave = () => {
//     onSave(editedExercise)
//   }

//   return (
//     <Dialog
//       open={open}
//       onClose={onCancel}
//       maxWidth="md"
//       fullWidth
//       fullScreen={false}
//       sx={{
//         "& .MuiDialog-paper": {
//           m: { xs: 1, sm: 2 },
//           maxHeight: { xs: "calc(100% - 16px)", sm: "calc(100% - 64px)" },
//         },
//       }}
//     >
//       <DialogTitle>
//         <Typography variant="h5" component="div" sx={{ fontSize: { xs: "1.25rem", sm: "1.5rem" } }}>
//           Edit {editedExercise.name}
//         </Typography>
//       </DialogTitle>

//       <DialogContent>
//         <Box sx={{ display: "flex", flexDirection: "column", gap: { xs: 2, sm: 3 }, pt: { xs: 1, sm: 2 } }}>
//           {editedExercise.sets.map((set, setIndex) => (
//             <Paper key={setIndex} elevation={2} sx={{ p: { xs: 1.5, sm: 2 } }}>
//               <Box
//                 sx={{
//                   display: "flex",
//                   justifyContent: "space-between",
//                   alignItems: "center",
//                   mb: { xs: 1.5, sm: 2 },
//                   flexWrap: "wrap",
//                   gap: 1,
//                 }}
//               >
//                 <Typography variant="h6" component="div" sx={{ fontSize: { xs: "1rem", sm: "1.25rem" } }}>
//                   Set {setIndex + 1}
//                 </Typography>
//                 <Button
//                   onClick={() => removeSet(setIndex)}
//                   disabled={editedExercise.sets.length === 1}
//                   color="error"
//                   size="small"
//                   startIcon={<DeleteIcon />}
//                   variant="outlined"
//                   sx={{
//                     minWidth: { xs: "auto", sm: "auto" },
//                     px: { xs: 1, sm: 2 },
//                   }}
//                 >
//                   <Box component="span" sx={{ display: { xs: "none", sm: "inline" } }}>
//                     Delete Set
//                   </Box>
//                 </Button>
//               </Box>

//               <Box sx={{ display: "flex", flexDirection: "column", gap: { xs: 1.5, sm: 2 } }}>
//                 {set.segments.map((segment, segmentIndex) => (
//                   <Box key={segmentIndex}>
//                     {segmentIndex > 0 && (
//                       <Divider sx={{ my: 1 }}>
//                         <Typography variant="caption" color="text.secondary">
//                           Split
//                         </Typography>
//                       </Divider>
//                     )}

//                     <Box
//                       sx={{
//                         display: "flex",
//                         gap: { xs: 1, sm: 2 },
//                         alignItems: "center",
//                       }}
//                     >
//                       {/* Column 1: Reps and Weight stacked */}
//                       <Box
//                         sx={{
//                           display: "flex",
//                           flexDirection: { xs: "column", sm: "row" },
//                           gap: { xs: 1, sm: 2 },
//                           flex: 1,
//                         }}
//                       >
//                         <TextField
//                           label="Reps"
//                           type="number"
//                           value={segment.reps}
//                           onChange={(e) => handleSegmentChange(setIndex, segmentIndex, "reps", e.target.value)}
//                           size="small"
//                           sx={{
//                             flex: 1,
//                             minWidth: { xs: "auto", sm: "100px" },
//                           }}
//                         />

//                         <TextField
//                           label="Weight (lbs)"
//                           type="number"
//                           value={segment.weight}
//                           onChange={(e) => handleSegmentChange(setIndex, segmentIndex, "weight", e.target.value)}
//                           size="small"
//                           sx={{
//                             flex: 1,
//                             minWidth: { xs: "auto", sm: "100px" },
//                           }}
//                         />
//                       </Box>

//                       {/* Column 2: Remove segment button - only show if more than one segment */}
//                       {set.segments.length > 1 && (
//                         <IconButton
//                           onClick={() => removeSegment(setIndex, segmentIndex)}
//                           color="warning"
//                           size="small"
//                           sx={{
//                             border: "1px solid",
//                             borderColor: "warning.main",
//                             "&:hover": {
//                               backgroundColor: "warning.light",
//                               opacity: 0.1,
//                             },
//                           }}
//                         >
//                           <RemoveCircleOutlineIcon />
//                         </IconButton>
//                       )}
//                     </Box>
//                   </Box>
//                 ))}

//                 <Button
//                   startIcon={<AddIcon />}
//                   onClick={() => addSegment(setIndex)}
//                   variant="outlined"
//                   size="small"
//                   sx={{
//                     mt: { xs: 0.5, sm: 1 },
//                     fontSize: { xs: "0.813rem", sm: "0.875rem" },
//                   }}
//                 >
//                   Add Segment
//                 </Button>
//               </Box>
//             </Paper>
//           ))}

//           <Button
//             startIcon={<AddIcon />}
//             onClick={addSet}
//             variant="contained"
//             size="large"
//             sx={{
//               py: { xs: 1, sm: 1.5 },
//               fontSize: { xs: "0.938rem", sm: "1rem" },
//             }}
//           >
//             Add Set
//           </Button>
//         </Box>
//       </DialogContent>

//       <DialogActions
//         sx={{
//           p: { xs: 1.5, sm: 2 },
//           gap: { xs: 0.5, sm: 1 },
//           flexDirection: { xs: "column-reverse", sm: "row" },
//         }}
//       >
//         <Button onClick={onCancel} variant="outlined" fullWidth={true} sx={{ width: { xs: "100%", sm: "auto" } }}>
//           Cancel
//         </Button>
//         <Button onClick={handleSave} variant="contained" fullWidth={true} sx={{ width: { xs: "100%", sm: "auto" } }}>
//           Save
//         </Button>
//       </DialogActions>
//     </Dialog>
//   )
// }

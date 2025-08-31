package com.allyoucanexercise.back_end.exerciseRecord;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecordService;

@RestController
@RequestMapping("/api/exercise-records")
public class ExerciseRecordController {

    private final ExerciseRecordService exerciseRecordService;

    public ExerciseRecordController(final ExerciseRecordService exerciseRecordService) {
        this.exerciseRecordService = exerciseRecordService;
    }

    @GetMapping("/{username}/{exercise_id}")
    public ResponseEntity<ExerciseRecordResponseDTO> getExerciseRecord(@PathVariable String username,
            @PathVariable Long exercise_id) {
        ExerciseRecordResponseDTO exerciseRecordResponseDTO = exerciseRecordService.getExerciseRecord(username,
                exercise_id);
        if (exerciseRecordResponseDTO != null) {
            return ResponseEntity.ok(exerciseRecordResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

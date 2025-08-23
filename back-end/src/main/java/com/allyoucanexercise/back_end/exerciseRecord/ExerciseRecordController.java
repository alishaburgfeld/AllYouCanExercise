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

    @GetMapping("/{username}/{exercise_name}")
    public ResponseEntity<ExerciseRecord> getExerciseRecord(@PathVariable String username,
            @PathVariable String exercise_name) {
        ExerciseRecord exerciseRecord = exerciseRecordService.getExerciseRecord(username, exercise_name);
        if (exerciseRecord != null) {
            return ResponseEntity.ok(exerciseRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

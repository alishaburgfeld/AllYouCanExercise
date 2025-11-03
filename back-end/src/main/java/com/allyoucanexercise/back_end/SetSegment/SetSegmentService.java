package com.allyoucanexercise.back_end.SetSegment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SetSegmentService {
    private final SetSegmentRepository setSegmentRepository;

    public SetSegmentService(SetSegmentRepository setSegmentRepository) {
        this.setSegmentRepository = setSegmentRepository;
    }

    public SetSegment getSetSegmentById(Long id) {
        return setSegmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Set Segment not found"));
    }

    public List<SetSegment> getAllSetSegmentsByExerciseSet(ExerciseSet exerciseSet) {
        return setSegmentRepository.findAllByExerciseSet(exerciseSet);
    }

    public SetSegment saveSetSegment(SetSegment setSegment) {
        return setSegmentRepository.save(setSegment);
    }

    public SetSegment saveSetSegment(ExerciseSet exerciseSet, Integer segmentOrder, Integer reps, Float weight,
            Integer durationSeconds, Float distanceMeters, DistanceMeasurement distanceMeasurement, Float pacePerMile) {
        SetSegment setSegment = new SetSegment();
        setSegment.setExerciseSet(exerciseSet);
        setSegment.setSegmentOrder(segmentOrder);
        setSegment.setReps(reps);
        setSegment.setWeight(weight);
        setSegment.setDurationSeconds(durationSeconds);
        setSegment.setDistanceMeters(distanceMeters);
        setSegment.setDistanceMeasurement(distanceMeasurement);
        setSegment.setPacePerMile(pacePerMile);
        return setSegmentRepository.save(setSegment);
    }

    public SetSegment updateSetSegment(SetSegment setSegment, Long id) {
        if (!setSegmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }

        setSegment.setId(id); // Make sure the entity has the correct ID. This ensures that the JPA context
        // understands this is an update, not a new insert.
        return setSegmentRepository.save(setSegment);
    }

    public void deleteSetSegment(Long id) {
        if (!setSegmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Segment with id " + id + " not found");
        }
        setSegmentRepository.deleteById(id);
    }

}

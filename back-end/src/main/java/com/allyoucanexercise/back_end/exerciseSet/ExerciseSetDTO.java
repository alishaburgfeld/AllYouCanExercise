package com.allyoucanexercise.back_end.exerciseSet;

import java.util.List;

import com.allyoucanexercise.back_end.SetSegment.SetSegmentDTO;

public class ExerciseSetDTO {
    private List<SetSegmentDTO> segments;

    public List<SetSegmentDTO> getSegments() {
        return segments;
    }

    public void setSegments(List<SetSegmentDTO> segments) {
        this.segments = segments;
    }

}

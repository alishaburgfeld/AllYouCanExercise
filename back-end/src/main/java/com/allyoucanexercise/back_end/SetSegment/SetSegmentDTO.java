package com.allyoucanexercise.back_end.setSegment;

public class SetSegmentDTO {
    private Integer reps;
    private Float weight;
    private Integer durationSeconds; // durationSeconds in seconds
    private Float distanceMeters; // distanceMeters in meters
    private DistanceMeasurement distanceMeasurement;
    private Float pacePerMile;

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Float getPacePerMile() {
        return pacePerMile;
    }

    public void setPacePerMile(Float pacePerMile) {
        this.pacePerMile = pacePerMile;
    }

    public Float getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(Float distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public DistanceMeasurement getDistanceMeasurement() {
        return distanceMeasurement;
    }

    public void setDistanceMeasurement(DistanceMeasurement distanceMeasurement) {
        this.distanceMeasurement = distanceMeasurement;
    }

    @Override
    public String toString() {
        return "SetSegmentDTO{" +
                "reps=" + reps +
                ", weight=" + weight +
                ", durationSeconds=" + durationSeconds +
                ", distanceMeters=" + distanceMeters +
                ", distanceMeasurement=" + distanceMeasurement +
                ", pacePerMile=" + pacePerMile +
                '}';
    }

}

package edu.dipae.sportify.models;

public class AthleteResults
{
    private int athleteId;
    private double athleteScore;

    public AthleteResults() {
    }

    public AthleteResults(int athleteId, double athleteScore) {
        this.athleteId = athleteId;
        this.athleteScore = athleteScore;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public double getAthleteScore() {
        return athleteScore;
    }

    public void setAthleteScore(double athleteScore) {
        this.athleteScore = athleteScore;
    }

    @Override
    public String toString() {
        return "AthleteResults{" +
                "athleteId=" + athleteId +
                ", athleteScore=" + athleteScore +
                '}';
    }
}

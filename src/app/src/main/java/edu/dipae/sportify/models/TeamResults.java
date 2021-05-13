package edu.dipae.sportify.models;

public class TeamResults
{
    private int teamAId;
    private int teamAScore;
    private int teamBId;
    private int teamBScore;

    public TeamResults() {
    }

    public TeamResults(int teamAId, int teamAScore, int teamBId, int teamBScore) {
        this.teamAId = teamAId;
        this.teamAScore = teamAScore;
        this.teamBId = teamBId;
        this.teamBScore = teamBScore;
    }

    public int getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(int teamAId) {
        this.teamAId = teamAId;
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(int teamAScore) {
        this.teamAScore = teamAScore;
    }

    public int getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(int teamBId) {
        this.teamBId = teamBId;
    }

    public int getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(int teamBScore) {
        this.teamBScore = teamBScore;
    }

    @Override
    public String toString() {
        return "TeamResults{" +
                "teamAId=" + teamAId +
                ", teamAScore=" + teamAScore +
                ", teamBId=" + teamBId +
                ", teamBScore=" + teamBScore +
                '}';
    }
}

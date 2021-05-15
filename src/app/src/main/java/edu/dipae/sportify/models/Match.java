package edu.dipae.sportify.models;
import java.util.ArrayList;
import java.util.Date;

public class Match
{
    private String id;
    private Date date;
    private String city;
    private String country;
    private int sportId;

    private TeamResults teamResults;
    private ArrayList<AthleteResults> athleteScores;

    public Match() {
    }

    public Match(String id, Date date, String city, String country, int sportId, TeamResults teamResults, ArrayList<AthleteResults> athleteScores) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.country = country;
        this.sportId = sportId;
        this.teamResults = teamResults;
        this.athleteScores = athleteScores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public TeamResults getTeamResults() {
        return teamResults;
    }

    public void setTeamResults(TeamResults teamResults) {
        this.teamResults = teamResults;
    }

    public ArrayList<AthleteResults> getAthleteScores() {
        return athleteScores;
    }

    public void setAthleteScores(ArrayList<AthleteResults> athleteScores) {
        this.athleteScores = athleteScores;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", date=" + date +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", sportId='" + sportId + '\'' +
                ", teamResults=" + teamResults +
                ", athleteScores=" + athleteScores +
                '}';
    }
}


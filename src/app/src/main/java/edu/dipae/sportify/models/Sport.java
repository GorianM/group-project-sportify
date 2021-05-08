package edu.dipae.sportify.models;

public class Sport
{

    private int id;
    private String name;
    private boolean teamSport;
    private int participants;
    private char gender;

    public Sport() {
    }

    public Sport(int id, String name, boolean teamSport, int participants, char gender) {
        this.id = id;
        this.name = name;
        this.teamSport = teamSport;
        this.participants = participants;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTeamSport() {
        return teamSport;
    }

    public void setTeamSport(boolean teamSport) {
        this.teamSport = teamSport;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SportInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teamSport=" + teamSport +
                ", participants=" + participants +
                ", gender=" + gender +
                '}';
    }
}

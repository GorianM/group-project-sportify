package edu.dipae.sportify.models;

public class Sport
{

    private int id;
    private String name;
    private boolean teamSport;
    private int participants;
    private char sex;

    public Sport() {
    }

    public Sport(int id, String name, boolean teamSport, int participants, char sex) {
        this.id = id;
        this.name = name;
        this.teamSport = teamSport;
        this.participants = participants;
        this.sex = sex;
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

    public char getSex() {
        return sex;
    }

    public void setSex(char gender) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "SportInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teamSport=" + teamSport +
                ", participants=" + participants +
                ", sex=" + sex +
                '}';
    }
}
